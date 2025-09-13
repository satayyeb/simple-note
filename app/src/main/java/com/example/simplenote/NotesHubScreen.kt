package com.example.simplenote

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.simplenote.Home0NotesEmptyScreen
import com.example.simplenote.homenotes.*
import com.google.relay.compose.*

/**
 * Screen هاب:
 * - اگر notes خالی باشد: Empty state (Home0NotesEmptyScreen)
 * - اگر notes غیرخالی: لیست با قالب HomeNotes
 */
@Composable
fun NotesHubScreen(
    notes: List<NoteUi>,
    onOpenNote: (noteId: String?) -> Unit,
    onHome: () -> Unit,
    onSettings: () -> Unit,

    // ——— پارامترهای جدید برای سرچ و پیجینیشن (اختیاری با پیش‌فرض) ———
    query: String = "",
    page: Int = 1,
    totalPages: Int = 1,
    onQueryChange: (String) -> Unit = {},
    onPrevPage: () -> Unit = {},
    onNextPage: () -> Unit = {}
) {
    if (notes.isEmpty() && query.isBlank()) {
        Home0NotesEmptyScreen(
            onAddNote = { onOpenNote(null) },
            onHome = onHome,
            onSettings = onSettings
        )
    } else {
        HomeNotesListScreen(
            notes = notes,
            onNoteClick = { onOpenNote(it) },
            onAddNote = { onOpenNote(null) },
            onHome = onHome,
            onSettings = onSettings,

            // وصلِ سرچ/پیجینیشن
            query = query,
            page = page,
            totalPages = totalPages,
            onQueryChange = onQueryChange,
            onPrevPage = onPrevPage,
            onNextPage = onNextPage
        )
    }
}

/* -------------------------------------------
   حالت غیرخالی: قالب HomeNotes با دادهٔ واقعی
   + سرچ و پیجینیشن ۴تایی (بدون تغییر تم)
   ------------------------------------------- */
@Composable
private fun HomeNotesListScreen(
    notes: List<NoteUi>,
    onNoteClick: (noteId: String?) -> Unit,
    onAddNote: () -> Unit,
    onHome: () -> Unit,
    onSettings: () -> Unit,

    // سرچ/پیجینیشن
    query: String,
    page: Int,
    totalPages: Int,
    onQueryChange: (String) -> Unit,
    onPrevPage: () -> Unit,
    onNextPage: () -> Unit,
) {
    TopLevel(modifier = Modifier.fillMaxSize()) {
        // SearchBar — ظاهر همون اتوژن، فقط داخلش input واقعی گذاشتیم
        SearchBar(
            modifier = Modifier
                .boxAlign(Alignment.TopStart, DpOffset(0.dp, 42.dp))
                .rowWeight(1f)
        ) {
            Back {
                Icon(
                    modifier = Modifier
                        .boxAlign(Alignment.TopStart, DpOffset(-1.dp, -1.dp))
                        .rowWeight(1f).columnWeight(1f)
                )
            }
            SearchField(modifier = Modifier.rowWeight(1f)) {
                // فیلد واقعی سرچ با حفظ شِمای اتوژن
                RelayContainer(
                    padding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BasicTextField(
                        value = query,
                        onValueChange = onQueryChange, // دی‌بونس در VM
                        textStyle = TextStyle(
                            color = Color(0xFF180E25),
                            fontSize = 14.sp
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        decorationBox = { inner ->
                            // Placeholder سبک در تم فعلی
                            if (query.isBlank()) {
                                RelayText(
                                    content = "Search...",
                                    color = Color(0x99180E25),
                                    fontSize = 14.sp,
                                    modifier = Modifier
                                )
                            }
                            inner()
                        }
                    )
                }
            }
        }

        // بخش لیست نوت‌ها با عنوان "Notes"
        NotesSection(
            modifier = Modifier
                .boxAlign(Alignment.TopStart, DpOffset(0.dp, 108.dp))
                .rowWeight(1f)
        ) {
            InterestingIdeaSection(modifier = Modifier.rowWeight(1f)) {
                SectionTitle(modifier = Modifier.rowWeight(1f)) {
                    Label() // متن "Notes"
                }

                if (notes.isEmpty()) {
                    RelayContainer(
                        padding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        modifier = Modifier.rowWeight(1f).fillMaxWidth()
                    ) {
                        RelayText(
                            content = "No results found.",
                            fontSize = 12.sp,
                            color = Color(0x99180E25)
                        )
                    }
                } else {
                    // --- 2×2 از صفحهٔ فعلی (بدون بریدن دوباره) ---
                    val slots = List(4) { i -> notes.getOrNull(i) }   // حداکثر 4 آیتم همین صفحه
                    val row1 = listOf(slots[0], slots[1])
                    val row2 = listOf(slots[2], slots[3])

                    RelayContainer(
                        arrangement = RelayContainerArrangement.Column,
                        padding = PaddingValues(start = 16.dp, end = 16.dp),
                        itemSpacing = 16.0,
                        clipToParent = false,
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                    ) {
                        // ردیف اول
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            row1.forEach { n ->
                                if (n != null) {
                                    DynamicNoteCard(
                                        index = notes.indexOf(n),
                                        title = n.title,
                                        snippet = n.body,
                                        onClick = { onNoteClick(n.id) }
                                    )
                                } else {
                                    com.example.simplenote.homenotes.Spacer(Modifier.width(180.dp).height(300.dp))
                                }
                            }
                        }
                        // ردیف دوم
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            row2.forEach { n ->
                                if (n != null) {
                                    DynamicNoteCard(
                                        index = notes.indexOf(n),
                                        title = n.title,
                                        snippet = n.body,
                                        onClick = { onNoteClick(n.id) }
                                    )
                                } else {
                                    com.example.simplenote.homenotes.Spacer(Modifier.width(180.dp).height(300.dp))
                                }
                            }
                        }
                    }
                }

// --- کنترل‌های پیجینیشن (Prev | Page x of y | Next) ---
                RelayContainer(
                    mainAxisAlignment = MainAxisAlignment.SpaceBetween,
                    crossAxisAlignment = CrossAxisAlignment.Center,
                    padding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    modifier = Modifier
                        .rowWeight(1f)
                        .fillMaxWidth()
                ) {
                    TextButton(onClick = onPrevPage, enabled = page > 1) { Text("Prev") }

                    RelayText(
                        content = "Page $page of $totalPages",
                        fontSize = 12.sp,
                        color = Color(0x99180E25)
                    )

                    TextButton(onClick = onNextPage, enabled = page < totalPages) { Text("Next") }
                }
            }

            // فاصلهٔ پایینی مثل اتوژن
            com.example.simplenote.homenotes.Spacer(modifier = Modifier.rowWeight(1f))
        }

        // تب‌بار پایین + کلیک‌ها (بدون تغییر)
        TabBar(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
            // پس‌زمینه
            com.example.simplenote.homenotes.Box(
                modifier = Modifier
                    .boxAlign(Alignment.BottomStart, DpOffset(0.dp, 0.009.dp))
                    .rowWeight(1f)
            )

            // Home
            Right(
                modifier = Modifier
                    .boxAlign(Alignment.BottomStart, DpOffset(0.dp, -15.991.dp))
                    .rowWeight(1f)
            ) {
                NavPartIconMenu(
                    modifier = Modifier
                        .boxAlign(Alignment.BottomStart, DpOffset(0.dp, 0.dp))
                        .rowWeight(1f)
                        .clickable { onHome() }
                ) {
                    IconSolidHome { Icon2(modifier = Modifier.rowWeight(1f).columnWeight(1f)) }
                    Home()
                }
            }

            // Settings
            Left(
                modifier = Modifier
                    .boxAlign(Alignment.BottomStart, DpOffset(0.dp, -15.991.dp))
                    .rowWeight(1f)
            ) {
                NavPartIconMenu3(
                    modifier = Modifier
                        .boxAlign(Alignment.BottomStart, DpOffset(0.dp, 0.dp))
                        .rowWeight(1f)
                        .clickable { onSettings() }
                ) {
                    IconOutlineCog {
                        Icon5(
                            modifier = Modifier
                                .boxAlign(Alignment.TopStart, DpOffset(-1.dp, -1.dp))
                                .rowWeight(1f).columnWeight(1f)
                        )
                    }
                    Settings()
                }
            }

            // Plus (Add new)
            IconButton(
                modifier = Modifier
                    .boxAlign(Alignment.BottomCenter, DpOffset(0.dp, -43.991.dp))
                    .clickable { onAddNote() }
            ) {
                Button {
                    IconOutlinePlus {
                        Icon6(
                            modifier = Modifier
                                .boxAlign(Alignment.TopStart, DpOffset(-1.5.dp, -1.5.dp))
                                .rowWeight(1f).columnWeight(1f)
                        )
                    }
                }
            }
        }
    }
}

/**
 * کارت داینامیک با استایل سه‌گانه‌ی اتوژن:
 * index%3 تعیین‌کننده رنگ پس‌زمینه (معادل TitleShortContent/1/2) است.
 */
@Composable
private fun DynamicNoteCard(
    index: Int,
    title: String,
    snippet: String,
    onClick: () -> Unit
) {
    val bg = when (index % 3) {
        0 -> Color(0xFFF6F6D4) // TitleShortContent
        1 -> Color(0xFFFCEAAA) // TitleShortContent1
        else -> Color(0xFFF6DEE2) // TitleShortContent2
    }

    RelayContainer(
        backgroundColor = bg,
        mainAxisAlignment = MainAxisAlignment.Start,
        crossAxisAlignment = CrossAxisAlignment.Start,
        padding = PaddingValues(all = 12.dp),
        itemSpacing = 16.0,
        clipToParent = false,
        radius = 8.0,
        modifier = Modifier
            .width(180.dp)
            .height(300.dp)
            .clickable { onClick() }
    ) {
        // Title
        RelayText(
            content = if (title.isNotBlank()) title else "(Untitled)",
            fontSize = 16.sp,
            fontFamily = inter,
            color = Color(0xFF180E25),
            height = 1.4.em,
            maxLines = 2,
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(44.dp),
            overflow = TextOverflow.Ellipsis
        )
        // Snippet
        val preview =
            if (snippet.length > 140) snippet.take(140) + "…" else snippet
        RelayText(
            content = preview,
            fontSize = 10.sp,
            fontFamily = inter,
            color = Color(0x99180E25),
            height = 1.2102272.em,
            maxLines = 6,
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(140.dp),
            overflow = TextOverflow.Ellipsis
        )
    }
}
