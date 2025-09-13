package com.example.simplenote

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.relay.compose.* // برای کامپوننت‌های Relay (Template)
import com.example.simplenote.buyingsomethingfilledwithadditionalinfo.*

// ——— مدل نوت (سراسری و مشترک با بقیه‌ی صفحات)
data class NoteUi(
    val id: String? = null,
    val title: String = "",
    val body: String = "",
    val lastEditedText: String = ""
)

/**
 * NoteScreen (Add/Edit):
 * - وقتی noteId != null: ویرایش
 * - وقتی noteId == null: ایجاد نوت جدید
 */
@Composable
fun NoteScreen(
    noteId: String?,
    loadNote: suspend (String) -> NoteUi?,   // گرفتن دیتای نوت
    onSave: (NoteUi) -> Unit,                // ذخیره (ساخت/آپدیت)
    onDelete: (String) -> Unit,              // حذف
    onBack: () -> Unit                       // برگشت
) {
    var title by rememberSaveable { mutableStateOf("") }
    var body by rememberSaveable { mutableStateOf("") }
    var statusText by rememberSaveable { mutableStateOf("") }

    // بارگذاری نوت موجود در حالت ویرایش
    LaunchedEffect(noteId) {
        if (!noteId.isNullOrBlank()) {
            loadNote(noteId)?.let { n ->
                title = n.title
                body = n.body
                statusText = n.lastEditedText.ifBlank { "Editing existing note" }
            }
        } else {
            statusText = "New note"
        }
    }

    // ریشه: Box پس‌زمینه + Column محتوای روی آن (بدون align)
    androidx.compose.foundation.layout.Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // پس‌زمینه/قالب اتوژن (تمام‌صفحه)
        BuyingSomethingFilledWithAdditionalInfo(
            modifier = Modifier.matchParentSize()
        )

        // لایه‌ی محتوایی
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // فاصله تا نوار بالا (پیش‌تر 42dp بود)
            Spacer(modifier = Modifier.height(42.dp))

            // ——— نوار بالا (Back) ———
            NavBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp) // قابل تنظیم
            ) {
                // دکمهٔ Back (Relay Link)
                Link(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                        .clickable { onBack() }
                ) {
                    IconSolidCheveronLeft {
                        Icon(
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Text() // متن داخل قالب
                }
            }

            // فاصله تا محتوای اصلی (قبلاً y≈120dp، اینجا 78dp بعد از NavBar)
            Spacer(modifier = Modifier.height(78.dp))

            // ——— محتوای اصلی (Title + Body) ———
            Content(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Notes(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Title
                    TitleInput(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextField(
                            value = title,
                            onValueChange = { title = it },
                            placeholder = { Text("Title") },
                            textStyle = LocalTextStyle.current.copy(
                                fontSize = MaterialTheme.typography.headlineSmall.fontSize
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    // Body (multi-line)
                    FreeTextAreaInput(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    ) {
                        TextField(
                            value = body,
                            onValueChange = { body = it },
                            placeholder = { Text("Feel Free to Write Here...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 240.dp),
                            maxLines = Int.MAX_VALUE
                        )
                    }
                }
            }

            // فضای خالی تا پایین صفحه
            Spacer(modifier = Modifier.weight(1f))

            // ——— نوار پایین (Status + Save/Delete) ———
            TaskBar(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // پس‌زمینه بار (در طرح قبلی y=-1dp بود، در Compose استاندارد نیازی نیست)
                Box1(
                    modifier = Modifier
                        .fillMaxWidth()
                )

                // ردیف: Status + متن وضعیت | دکمه‌ها
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // بخش وضعیت
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Status(modifier = Modifier.padding(end = 8.dp))
                        RelayText(content = statusText)
                    }

                    // دکمه‌ها
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Save
                        TaskBarButton2(
                            modifier = Modifier
                                .height(IntrinsicSize.Min)
                                .clickable {
                                    val toSave = NoteUi(
                                        id = noteId,
                                        title = title.trim(),
                                        body = body.trim(),
                                        lastEditedText = "Saved just now"
                                    )
                                    onSave(toSave)
                                    statusText = "Saved just now"
                                }
                        ) {
                            RelayText(content = "Save")
                        }

                        // فاصله بین دکمه‌ها
                        Spacer(Modifier.width(8.dp))

                        // Delete فقط وقتی noteId داریم
                        if (!noteId.isNullOrBlank()) {
                            TaskBarButton2(
                                modifier = Modifier
                                    .height(IntrinsicSize.Min)
                                    .clickable { onDelete(noteId) }
                            ) {
                                IconOutlineTrash {
                                    Icon3(
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
