package com.example.simplenote

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.relay.compose.*
import com.example.simplenote.buyingsomethingfilledwithadditionalinfo.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class NoteUi(
    val id: String? = null,
    val title: String = "",
    val body: String = "",
    val lastEditedText: String = ""
)

@Composable
fun NoteScreen(
    noteId: String?,
    loadNote: suspend (String) -> NoteUi?,
    onSave: (NoteUi, (NoteUi?) -> Unit) -> Unit, // ← بازگشتی شد
    onDelete: (String) -> Unit,
    onBack: () -> Unit
) {
    var currentId by rememberSaveable { mutableStateOf(noteId) }
    var title by rememberSaveable { mutableStateOf("") }
    var body by rememberSaveable { mutableStateOf("") }
    var statusText by rememberSaveable { mutableStateOf("") }
    var showDeleteDialog by remember { mutableStateOf(false) }

    // بارگذاری اولیه
    LaunchedEffect(noteId) {
        if (!noteId.isNullOrBlank()) {
            loadNote(noteId)?.let { n ->
                currentId = n.id
                title = n.title
                body = n.body
                statusText = if (n.lastEditedText.isNotBlank()) n.lastEditedText else "editing..."
            }
        } else {
            statusText = "new note"
        }
    }

    // ——— اتوسیوی دی‌بونس ———
    val scope = rememberCoroutineScope()
    var saveJob: Job? by remember { mutableStateOf(null) }
    fun scheduleAutoSave() {
        // اگر هر دو خالی‌اند، سیو نکن
        if (title.isBlank() || body.isBlank()) return

        val toSave = NoteUi(id = currentId, title = title.trim(), body = body.trim())
        saveJob?.cancel()

        // فوراً به کاربر بگو "در حال ذخیره..."
        statusText = "saving..."

        saveJob = scope.launch {
            delay(600) // debounce
            onSave(toSave) { saved ->
                val hhmm = java.time.LocalTime.now().withSecond(0).withNano(0).toString() // 21:34
                if (saved != null) {
                    currentId = saved.id ?: currentId
                    statusText = saved.lastEditedText.ifBlank { "last edit: $hhmm" }
                } else {
                    statusText = "save failed"
                }
            }
        }
    }

    androidx.compose.foundation.layout.Box(Modifier.fillMaxSize()) {
        BuyingSomethingFilledWithAdditionalInfo(Modifier.matchParentSize())

        Column(Modifier.fillMaxSize()) {
            Spacer(Modifier.height(42.dp))

            NavBar(Modifier.fillMaxWidth()) {
                Link(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                        .clickable { onBack() }
                ) {
                    IconSolidCheveronLeft { Icon(Modifier.fillMaxSize()) }
                    Text()
                }
            }

            Spacer(Modifier.height(78.dp))

            Content(Modifier.fillMaxWidth()) {
                Notes(Modifier.fillMaxWidth()) {
                    TitleInput(Modifier.fillMaxWidth()) {
                        TextField(
                            value = title,
                            onValueChange = { title = it; scheduleAutoSave() },
                            placeholder = { Text("Title") },
                            textStyle = LocalTextStyle.current.copy(
                                fontSize = MaterialTheme.typography.headlineSmall.fontSize
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    FreeTextAreaInput(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    ) {
                        TextField(
                            value = body,
                            onValueChange = { body = it; scheduleAutoSave() },
                            placeholder = { Text("Feel Free to Write Here...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 240.dp),
                            maxLines = Int.MAX_VALUE
                        )
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            // نوار پایین (Status + Delete)
            TaskBar(Modifier.fillMaxWidth()) {
                Box1(Modifier.fillMaxWidth())
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // وضعیت: "آخرین ویرایش ..."
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RelayText(content = statusText)
                    }
                    // دکمهٔ حذف (فقط وقتی id داریم)
                    if (!currentId.isNullOrBlank()) {
                        TaskBarButton2(
                            modifier = Modifier
                                .height(48.dp)               // ← ارتفاع صریح
                                .clickable { showDeleteDialog = true }
                        ) {
                            IconOutlineTrash { Icon3(Modifier.fillMaxSize()) }
                        }
                    }
                }
            }
        }

        // دیالوگ تأیید حذف
        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("delete node") },
                text = { Text("are you sure?") },
                confirmButton = {
                    TextButton(onClick = {
                        currentId?.let { onDelete(it) }
                        showDeleteDialog = false
                        onBack()
                    }) { Text("yes, delete") }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) { Text("cancel") }
                }
            )
        }
    }
}
