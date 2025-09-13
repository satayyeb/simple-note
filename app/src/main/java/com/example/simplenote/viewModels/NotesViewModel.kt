package com.example.simplenote

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplenote.data.NoteDto
import com.example.simplenote.data.NoteCreateRequest
import com.example.simplenote.data.NoteUpdateRequest
import com.example.simplenote.data.NotesApi
import com.example.simplenote.data.PaginatedNotesResponse
import com.example.simplenote.network.BackendApi
import com.example.simplenote.network.SessionManager
import kotlinx.coroutines.launch

/**
 * Repository-in-ViewModel:
 * تمام لاجیک دسترسی به شبکه اینجاست و کلاس جداگانهٔ Repository نداریم.
 */
class NotesViewModel : ViewModel() {

    // -- UI state
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

//    data class NoteUi(val id: String?, val title: String, val body: String, val lastEditedText: String = "")

    var notes by mutableStateOf<List<NoteUi>>(emptyList())
        private set
    var totalCount by mutableStateOf(0)
        private set

    // -- API (Retrofit)
    // مطمئن شو در BackendApi چیزی مثل:
    // val notes: NotesApi = retrofit.create(NotesApi::class.java)
    // اضافه شده باشد.
    private val api: NotesApi = BackendApi.notes

    // ---------- Read ----------
    fun loadNotes(page: Int? = 1, pageSize: Int? = 20) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val res = api.listNotes(page, pageSize,
                    token = "Bearer ${SessionManager.fetchAccessToken()}"
                )
                if (res.isSuccessful) {
                    val body: PaginatedNotesResponse<NoteDto>? = res.body()
                    totalCount = body?.count ?: 0
                    notes = (body?.results ?: emptyList()).map { it.toUi() }
                } else {
                    errorMessage = res.errorBody()?.string() ?: "Failed to load notes"
                }
            } catch (e: Exception) {
                errorMessage = "Network error: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    suspend fun loadOne(noteId: String): NoteUi? {
        val id = noteId.toIntOrNull() ?: return null
        // اول از لیست فعلی اگر موجود بود همونو بده
        notes.firstOrNull { it.id == noteId }?.let { return it }
        return try {
            val res = api.getNote(id,
                token = "Bearer ${SessionManager.fetchAccessToken()}"
            )
            if (res.isSuccessful) res.body()?.toUi() else null
        } catch (_: Exception) { null }
    }

    // ---------- Create / Update ----------
    fun save(note: NoteUi, onSaved: (NoteUi?) -> Unit) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val resp = if (note.id.isNullOrBlank()) {
                    api.createNote(NoteCreateRequest(note.title, note.body),
                        token = "Bearer ${SessionManager.fetchAccessToken()}"
                    )
                } else {
                    api.updateNotePut(note.id!!.toInt(), NoteUpdateRequest(note.title, note.body),
                        token = "Bearer ${SessionManager.fetchAccessToken()}"
                    )
                }
                if (resp.isSuccessful) {
                    val dto = resp.body()
                    val savedUi = dto?.let {
                        NoteUi(
                            id = it.id.toString(),
                            title = it.title,
                            body = it.description,
                            lastEditedText = "last edit: " + java.time.LocalTime.now()
                                .withSecond(0).withNano(0).toString()
                        )
                    }
                    // آپدیت خوش‌بینانه‌ی لیست برای نمایش فوری در خانه
                    savedUi?.let { s ->
                        notes = buildList {
                            // اگر قبلاً وجود داشت جایگزین کن، وگرنه اضافه کن
                            val replaced = notes.any { it.id == s.id }
                            if (replaced) addAll(notes.map { if (it.id == s.id) s else it })
                            else add(0, s).also { addAll(notes) }
                        }
                    }
                    onSaved(savedUi)
                } else {
                    errorMessage = resp.errorBody()?.string() ?: "Save failed"
                    onSaved(null)
                }
            } catch (e: Exception) {
                errorMessage = "Network error: ${e.message}"
                onSaved(null)
            } finally {
                isLoading = false
            }
        }
    }

    // ---------- Delete ----------
    fun delete(noteId: String, onDone: () -> Unit) {
        val id = noteId.toIntOrNull() ?: return
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val resp = api.deleteNote(id,
                    token = "Bearer ${SessionManager.fetchAccessToken()}"
                )
                if (resp.isSuccessful) {
                    loadNotes()
                    onDone()
                } else {
                    errorMessage = resp.errorBody()?.string() ?: "Delete failed"
                }
            } catch (e: Exception) {
                errorMessage = "Network error: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    // ---------- Optional: filter / bulk / patch ----------
    fun filter(
        title: String? = null,
        description: String? = null,
        updatedGteIso: String? = null,
        updatedLteIso: String? = null,
        page: Int? = 1,
        pageSize: Int? = 20
    ) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val res = api.filterNotes(
                    title = title,
                    description = description,
                    updatedGte = updatedGteIso,
                    updatedLte = updatedLteIso,
                    page = page,
                    pageSize = pageSize,
                    token = "Bearer ${SessionManager.fetchAccessToken()}"
                )
                if (res.isSuccessful) {
                    val body = res.body()
                    totalCount = body?.count ?: 0
                    notes = (body?.results ?: emptyList()).map { it.toUi() }
                } else errorMessage = res.errorBody()?.string() ?: "Filter failed"
            } catch (e: Exception) {
                errorMessage = "Network error: ${e.message}"
            } finally { isLoading = false }
        }
    }

    fun patch(id: String, fields: Map<String, Any>, onDone: () -> Unit) {
        val noteId = id.toIntOrNull() ?: return
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val res = api.updateNotePatch(noteId, fields,
                    token = "Bearer ${SessionManager.fetchAccessToken()}"
                )
                if (res.isSuccessful) {
                    loadNotes()
                    onDone()
                } else errorMessage = res.errorBody()?.string() ?: "Patch failed"
            } catch (e: Exception) {
                errorMessage = "Network error: ${e.message}"
            } finally { isLoading = false }
        }
    }

    fun bulkCreate(items: List<NoteCreateRequest>, onDone: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val res = api.bulkCreate(page = null, items = items,
                    token = "Bearer ${SessionManager.fetchAccessToken()}"
                )
                if (res.isSuccessful) {
                    loadNotes()
                    onDone()
                } else errorMessage = res.errorBody()?.string() ?: "Bulk failed"
            } catch (e: Exception) {
                errorMessage = "Network error: ${e.message}"
            } finally { isLoading = false }
        }
    }

    // ---------- mapping ----------
    private fun NoteDto.toUi(): NoteUi = NoteUi(
        id = id.toString(),
        title = title,
        body = description,
        lastEditedText = updated ?: ""
    )
}
