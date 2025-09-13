package com.example.simplenote

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplenote.data.*
import com.example.simplenote.network.BackendApi
import com.example.simplenote.network.SessionManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.ceil
import kotlin.math.max

class NotesViewModel : ViewModel() {

    // ---------- UI state ----------
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    var notes by mutableStateOf<List<NoteUi>>(emptyList())
        private set
    var totalCount by mutableStateOf(0)
        private set

    // ---------- Search & Pagination ----------
    var searchQuery by mutableStateOf("")
        private set
    var page by mutableStateOf(1)
        private set
    var pageSize by mutableStateOf(4)
        private set
    var totalPages by mutableStateOf(1)
        private set

    private var searchJob: Job? = null

    private var allNotesCache: MutableList<NoteDto> = mutableListOf()
    private var searchCacheValid = false

    private fun updateTotalPages() {
        val pages = ceil(totalCount.coerceAtLeast(0).toDouble() / pageSize.coerceAtLeast(1)).toInt()
        totalPages = max(1, pages)
    }

    private suspend fun fetchAllNotesIntoCache() {
        allNotesCache.clear()
        var p = 1
        while (true) {
            val res = api.listNotes(page = p, pageSize = 100,
                token = "Bearer ${SessionManager.fetchAccessToken()}"
            ) // هر صفحه 100 تا؛ لازم شد ادامه می‌دهیم
            if (!res.isSuccessful) break
            val body = res.body() ?: break
            allNotesCache.addAll(body.results)
            if (body.next.isNullOrBlank()) break
            p += 1
        }
        searchCacheValid = true
    }

    fun updatePageSize(size: Int) {
        if (size <= 0) return
        if (pageSize != size) {
            pageSize = size
            page = 1
            refresh()
        }
    }

    private fun applyClientSearchToUi() {
        val q = searchQuery.trim()
        if (q.isBlank()) return

        // OR روی عنوان یا متن
        val filtered = allNotesCache.filter {
            it.title.contains(q, ignoreCase = true) ||
                    it.description.contains(q, ignoreCase = true)
        }

        // تعداد کل و صفحات
        totalCount = filtered.size
        updateTotalPages()

        // کلَمپ صفحه فعلی
        if (totalPages < 1) totalPages = 1
        if (page > totalPages) page = totalPages
        if (page < 1) page = 1

        // ✨ فرمول درست: ابتدا clamp سپس (p - 1) * pageSize
        val p = page.coerceAtLeast(1)
        val start = (p - 1) * pageSize
        val end = minOf(start + pageSize, filtered.size)

        val window =
            if (start in 0..filtered.size && start < end) filtered.subList(start, end)
            else emptyList()

        notes = window.map { it.toUi() }
    }


    fun updateSearchQuery(q: String) {
        searchQuery = q
        page = 1
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(350) // debounce
            if (searchQuery.isBlank()) {
                // برگرد به مود سرور
                searchCacheValid = false
                loadNotes(page = 1, pageSize = pageSize)
            } else {
                if (!searchCacheValid) fetchAllNotesIntoCache()
                applyClientSearchToUi()
            }
        }
    }


    fun refresh() {
        if (searchQuery.isBlank()) {
            loadNotes(page = page, pageSize = pageSize)
        } else {
            viewModelScope.launch {
                if (!searchCacheValid) fetchAllNotesIntoCache()
                applyClientSearchToUi()
            }
        }
    }

    fun nextPage() {
        if (page < totalPages) {
            page += 1
            if (searchQuery.isBlank()) loadNotes(page = page, pageSize = pageSize) else applyClientSearchToUi()
        }
    }

    fun prevPage() {
        if (page > 1) {
            page -= 1
            if (searchQuery.isBlank()) loadNotes(page = page, pageSize = pageSize) else applyClientSearchToUi()
        }
    }


    // ---------- API ----------
    private val api: NotesApi = BackendApi.notes
    private fun bearer() = "Bearer ${SessionManager.fetchAccessToken()}"

    fun loadNotes(page: Int? = 1, pageSize: Int? = 20) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val res = api.listNotes(
                    page = page, pageSize = pageSize,
                    token = bearer()
                )
                if (res.isSuccessful) {
                    val body = res.body()
                    totalCount = body?.count ?: 0
                    notes = (body?.results ?: emptyList()).map { it.toUi() }
                    page?.let { this@NotesViewModel.page = it }
                    pageSize?.let { this@NotesViewModel.pageSize = it }
                    updateTotalPages()
                } else {
                    errorMessage = res.errorBody()?.string() ?: "Failed to load notes"
                }
            } catch (e: Exception) {
                errorMessage = "Network error: ${e.message}"
            } finally { isLoading = false }
        }
    }

    suspend fun loadOne(noteId: String): NoteUi? {
        val id = noteId.toIntOrNull() ?: return null
        notes.firstOrNull { it.id == noteId }?.let { return it }
        return try {
            val res = api.getNote(id, token = bearer())
            if (res.isSuccessful) res.body()?.toUi() else null
        } catch (_: Exception) { null }
    }

    fun save(note: NoteUi, onSaved: (NoteUi?) -> Unit) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val resp = if (note.id.isNullOrBlank()) {
                    api.createNote(NoteCreateRequest(note.title, note.body), token = bearer())
                } else {
                    api.updateNotePut(note.id!!.toInt(), NoteUpdateRequest(note.title, note.body), token = bearer())
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
                    // آپدیت خوش‌بینانهٔ لیست
                    savedUi?.let { s ->
                        // 1) اول تشخیص بده قبلاً این نوت در لیست هست یا نه
                        val existed = s.id != null && notes.any { it.id == s.id }

                        // 2) لیست را آپدیت کن (جایگزینی یا افزودن در ابتدای لیست)
                        notes = if (existed) {
                            notes.map { if (it.id == s.id) s else it }
                        } else {
                            listOf(s) + notes
                        }

                        // 3) شمارنده و صفحات را به‌روز کن
                        if (!existed) totalCount += 1
                        updateTotalPages()
                    }
                    onSaved(savedUi)
                } else {
                    errorMessage = resp.errorBody()?.string() ?: "Save failed"
                    onSaved(null)
                }
            } catch (e: Exception) {
                errorMessage = "Network error: ${e.message}"
                onSaved(null)
            } finally { isLoading = false }
        }
    }

    fun delete(noteId: String, onDone: () -> Unit) {
        val id = noteId.toIntOrNull() ?: return
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val resp = api.deleteNote(id, token = bearer())
                if (resp.isSuccessful) {
                    // خوش‌بینانه از لیست حذف کن
                    notes = notes.filterNot { it.id == noteId }
                    searchCacheValid = false
                    totalCount = (totalCount - 1).coerceAtLeast(0)
                    // اگر صفحه خالی شد و صفحه > 1 بود، برگرد صفحه قبل
                    if (notes.isEmpty() && page > 1) page -= 1
                    updateTotalPages()
                    // و سپس از سرور هم نوبتی تازه کن
                    refresh()
                    onDone()
                } else {
                    errorMessage = resp.errorBody()?.string() ?: "Delete failed"
                }
            } catch (e: Exception) {
                errorMessage = "Network error: ${e.message}"
            } finally { isLoading = false }
        }
    }

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
                    token = bearer()
                )
                if (res.isSuccessful) {
                    val body = res.body()
                    totalCount = body?.count ?: 0
                    notes = (body?.results ?: emptyList()).map { it.toUi() }
                    page?.let { this@NotesViewModel.page = it }
                    pageSize?.let { this@NotesViewModel.pageSize = it }
                    updateTotalPages()
                } else errorMessage = res.errorBody()?.string() ?: "Filter failed"
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
