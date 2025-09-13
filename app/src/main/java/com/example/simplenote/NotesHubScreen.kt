package com.example.simplenote

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
) {
    if (notes.isEmpty()) {
        // حالت خالی: همون ایلاستریشن/فلش/متن معرفی + تب‌بار با کلیک‌ها
        Home0NotesEmptyScreen(
            onAddNote = { onOpenNote(null) },
            onHome = onHome,
            onSettings = onSettings
        )
    } else {
        // حالت غیرخالی: قالب HomeNotes با لیست داینامیک و تب‌بار کلیکی
        HomeNotesListScreen(
            notes = notes,
            onNoteClick = { onOpenNote(it) },
            onAddNote = { onOpenNote(null) },
            onHome = onHome,
            onSettings = onSettings
        )
    }
}

/* -------------------------------------------
   حالت غیرخالی: قالب HomeNotes با دادهٔ واقعی
   ------------------------------------------- */
@Composable
private fun HomeNotesListScreen(
    notes: List<NoteUi>,
    onNoteClick: (noteId: String?) -> Unit,  // <- nullable
    onAddNote: () -> Unit,
    onHome: () -> Unit,
    onSettings: () -> Unit,
) {
    TopLevel(modifier = Modifier.fillMaxSize()) {
        // SearchBar (فعلاً دکوری؛ اگر خواستی بعدها سرچ را وصل کن)
        SearchBar(
            modifier = Modifier.boxAlign(
                alignment = Alignment.TopStart,
                offset = DpOffset(x = 0.dp, y = 42.dp)
            ).rowWeight(1f)
        ) {
            Back {
                Icon(
                    modifier = Modifier
                        .boxAlign(Alignment.TopStart, DpOffset(-1.dp, -1.dp))
                        .rowWeight(1f).columnWeight(1f)
                )
            }
            SearchField(modifier = Modifier.rowWeight(1f)) {
                Placeholder(modifier = Modifier.rowWeight(1f))
            }
        }

        // بخش لیست نوت‌ها با عنوان "Notes"
        NotesSection(
            modifier = Modifier.boxAlign(
                alignment = Alignment.TopStart,
                offset = DpOffset(x = 0.dp, y = 108.dp)
            ).rowWeight(1f)
        ) {
            InterestingIdeaSection(modifier = Modifier.rowWeight(1f)) {
                SectionTitle(modifier = Modifier.rowWeight(1f)) {
                    Label() // متن "Notes" (همان اتوژن) :contentReference[oaicite:1]{index=1}
                }

                // اسکرول افقی با استایل مشابه InterestingIdeaList
                RelayContainer(
                    arrangement = RelayContainerArrangement.Row,
                    padding = PaddingValues(start = 16.dp, end = 16.dp),
                    itemSpacing = 16.0,
                    clipToParent = false,
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth()
                ) {
                    // از LazyRow استفاده می‌کنیم تا داینامیک باشد
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(notes, key = { it.id ?: it.title }) { note ->
                            DynamicNoteCard(
                                index = notes.indexOf(note),
                                title = note.title,
                                snippet = note.body,
                                onClick = { onNoteClick(note.id) } // حالا امضای onNoteClick هم nullable شده
                            )
                        }
                        // اسپیسِ انتهایی مثل Spacer1 در اتوژن
                        item { Spacer1(modifier = Modifier.columnWeight(1f)) }
                    }
                }
            }

            // فاصلهٔ پایینی مثل اتوژن
            com.example.simplenote.homenotes.Spacer(modifier = Modifier.rowWeight(1f))
        }

        // تب‌بار پایین + کلیک‌ها
        TabBar(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
            // پس‌زمینه تب‌بار
            com.example.simplenote.homenotes.Box(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.BottomStart,
                    offset = DpOffset(x = 0.dp, y = 0.009.dp)
                ).rowWeight(1f)
            )

            // Home
            Right(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.BottomStart,
                    offset = DpOffset(x = 0.dp, y = -15.991.dp)
                ).rowWeight(1f)
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
                modifier = Modifier.boxAlign(
                    alignment = Alignment.BottomStart,
                    offset = DpOffset(x = 0.dp, y = -15.991.dp)
                ).rowWeight(1f)
            ) {
                NavPartIconMenu3(
                    modifier = Modifier
                        .boxAlign(Alignment.BottomStart, DpOffset(0.dp, 0.dp))
                        .rowWeight(1f)
                        .clickable { onSettings() }
                ) {
                    IconOutlineCog {
                        Icon5(
                            modifier = Modifier.boxAlign(
                                alignment = Alignment.TopStart,
                                offset = DpOffset(x = -1.dp, y = -1.dp)
                            ).rowWeight(1f).columnWeight(1f)
                        )
                    }
                    Settings()
                }
            }

            // Plus (Add new)
            IconButton(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.BottomCenter,
                    offset = DpOffset(x = 0.dp, y = -43.991.dp)
                ).clickable { onAddNote() }
            ) {
                Button {
                    IconOutlinePlus {
                        Icon6(
                            modifier = Modifier.boxAlign(
                                alignment = Alignment.TopStart,
                                offset = DpOffset(x = -1.5.dp, y = -1.5.dp)
                            ).rowWeight(1f).columnWeight(1f)
                        )
                    }
                }
            }
        }
    }
}

/**
 * کارت داینامیک با استایل سه‌گانه‌ی اتوژن:
 * index%3 تعیین‌کننده رنگ پس‌زمینه (معادل TitleShortContent/1/2) است. :contentReference[oaicite:2]{index=2}
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
        1 -> Color(0xFFFCEAAA) // TitleShortContent1 (252,234,170)
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
            .clickable { onClick() }
    ) {
        // Title
        RelayText(
            content = title,
            fontSize = 16.sp,
            fontFamily = inter,
            color = Color(0xFF180E25),
            height = 1.4.em,
            maxLines = 2,
            modifier = Modifier.fillMaxWidth().requiredHeight(44.dp)
        )
        // Snippet
        RelayText(
            content = snippet,
            fontSize = 10.sp,
            fontFamily = inter,
            color = Color(0x99180E25),
            height = 1.2102272.em,
            maxLines = 6,
            modifier = Modifier.fillMaxWidth().requiredHeight(140.dp)
        )
    }
}
