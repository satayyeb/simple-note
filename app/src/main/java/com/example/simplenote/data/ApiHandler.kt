package com.example.simplenote.data

import android.content.Context
import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiHandler {
    fun get(
        context: Context,
        path: String,
        needToken: Boolean,
        onResult: (String?) -> Unit
    ) {
        val retrofit = ApiClient.getRetrofit(context, needToken)
        val service = retrofit.create(ApiService::class.java)

        service.getData(path).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    onResult(response.body()?.string())
                } else {
                    Log.e("ApiHandler", "Failed: ${response.code()}")
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("ApiHandler", "Error: ${t.message}")
                onResult(null)
            }
        })
    }

    fun post(
        context: Context,
        path: String,
        needToken: Boolean,
        jsonBody: String,
        onResult: (String?) -> Unit
    ) {
        val retrofit = ApiClient.getRetrofit(context, needToken)
        val service = retrofit.create(ApiService::class.java)

        val requestBody = jsonBody
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        Log.d("ApiHandler", "Request Body: $requestBody")

        service.postData(path, requestBody).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    onResult(response.body()?.string())
                } else {
                    Log.e("ApiHandler", "POST Failed: ${response.code()}")
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("ApiHandler", "POST Error: ${t.message}")
                onResult(null)
            }
        })
    }
}
