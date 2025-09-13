package com.example.simplenote.network

import com.example.simplenote.data.NotesApi
import com.example.simplenote.data.SimpleNoteApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val okHttpClient = OkHttpClient.Builder()
    .authenticator(TokenAuthenticator())
    .build()

object BackendApi {
    private const val BASE_URL = "https://simple.darkube.app"  // Replace with actual base URL

    // یک بار Retrofit بساز
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory()) // اینجا Companion لازم نیست
            .build()
    }

    // اینستنس‌ها
    val api: SimpleNoteApi by lazy { retrofit.create(SimpleNoteApi::class.java) }
    val notes: NotesApi by lazy { retrofit.create(NotesApi::class.java) }
}