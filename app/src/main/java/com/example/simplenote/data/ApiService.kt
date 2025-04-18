package com.example.simplenote.data

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET
    fun getData(@Url path: String): Call<ResponseBody>

    @POST
    fun postData(
        @Url path: String,
        @Body body: RequestBody
    ): Call<ResponseBody>
}
