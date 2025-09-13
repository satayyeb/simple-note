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
        @Body request: ChangePasswordRequest,
    ): Response<ChangePasswordResponse>
}