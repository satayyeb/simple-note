package com.example.simplenote.data

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @GET
    fun getData(@Url path: String): Call<ResponseBody>

    @POST
    fun postData(
        @Url path: String,
        @Body body: RequestBody
    ): Call<ResponseBody>
}


// Model for Register Request (POST /api/auth/register/)
data class RegisterRequest(
    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("first_name")
    val firstName: String? = null,  // Optional

    @SerializedName("last_name")
    val lastName: String? = null     // Optional
)

// Model for Register Response (201 Created)
data class RegisterResponse(
    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("first_name")
    val firstName: String? = null,  // May be empty if not provided

    @SerializedName("last_name")
    val lastName: String? = null    // May be empty if not provided
)

// Model for Login Request (POST /api/auth/token/)
data class LoginRequest(
    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String
)

data class LoginResponse(
    @SerializedName("access")
    val access: String,

    @SerializedName("refresh")
    val refresh: String
)

data class RefreshRequest(
    @SerializedName("refresh")
    val refresh: String,
)

data class RefreshResponse(
    @SerializedName("access")
    val access: String,
)

data class ChangePasswordRequest(
    @SerializedName("old_password")
    val oldPassword: String,

    @SerializedName("new_password")
    val newPassword: String,
)

data class ChangePasswordResponse(
    @SerializedName("detail")
    val detail: String,
)
data class NoteDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("created") val created: String? = null,
    @SerializedName("updated") val updated: String? = null,
)

data class NoteCreateRequest(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
)

data class NoteUpdateRequest(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
)

data class PaginatedNotesResponse<T>(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: List<T>
)



// Retrofit API Interface
interface SimpleNoteApi {

    @POST("api/auth/register/")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("api/auth/token/")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("api/auth/token/refresh/")
    suspend fun refreshToken(
        @Body request: RefreshRequest
    ): Response<RefreshResponse>

    @POST("api/auth/change-password/")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body request: ChangePasswordRequest,
    ): Response<ChangePasswordResponse>
}

interface NotesApi{

    @GET("api/notes/")
    suspend fun listNotes(
        @Query("page") page: Int? = null,
        @Query("page_size") pageSize: Int? = null,
    ): Response<PaginatedNotesResponse<NoteDto>>

    // POST /api/notes/
    @POST("api/notes/")
    suspend fun createNote(
        @Body body: NoteCreateRequest
    ): Response<NoteDto>

    // GET /api/notes/{id}/
    @GET("api/notes/{id}/")
    suspend fun getNote(
        @Path("id") id: Int
    ): Response<NoteDto>

    // PUT /api/notes/{id}/
    @PUT("api/notes/{id}/")
    suspend fun updateNotePut(
        @Path("id") id: Int,
        @Body body: NoteUpdateRequest
    ): Response<NoteDto>

    // PATCH /api/notes/{id}/
    @PATCH("api/notes/{id}/")
    suspend fun updateNotePatch(
        @Path("id") id: Int,
        @Body body: Map<String, @JvmSuppressWildcards Any>
    ): Response<NoteDto>

    // DELETE /api/notes/{id}/
    @DELETE("api/notes/{id}/")
    suspend fun deleteNote(
        @Path("id") id: Int
    ): Response<Unit>

    // POST /api/notes/bulk?page=
    @POST("api/notes/bulk")
    suspend fun bulkCreate(
        @Query("page") page: Int? = null,
        @Body items: List<NoteCreateRequest>
    ): Response<List<NoteDto>>

    // GET /api/notes/filter?title=&description=&updated__gte=&updated__lte=&page=&page_size=
    @GET("api/notes/filter")
    suspend fun filterNotes(
        @Query("title") title: String? = null,
        @Query("description") description: String? = null,
        @Query("updated__gte") updatedGte: String? = null,
        @Query("updated__lte") updatedLte: String? = null,
        @Query("page") page: Int? = null,
        @Query("page_size") pageSize: Int? = null,
    ): Response<PaginatedNotesResponse<NoteDto>>
}