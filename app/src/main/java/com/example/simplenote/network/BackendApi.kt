package com.example.simplenote.network

import com.example.simplenote.data.SimpleNoteApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BackendApi {
    private const val BASE_URL = "http://10.0.2.2:8000"  // Replace with actual base URL
    val api: SimpleNoteApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory.Companion())  // For suspend functions
            .build()
            .create(SimpleNoteApi::class.java)
    }
}