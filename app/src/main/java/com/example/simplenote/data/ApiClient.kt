package com.example.simplenote.data

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://simple-note.amirsalarsafaei.com/"

    fun getRetrofit(context: Context, needToken: Boolean): Retrofit {
        val clientBuilder = OkHttpClient.Builder()

        if (needToken) {
            val tokenProvider = TokenProvider(context)
            val token = tokenProvider.getToken()

            if (!token.isNullOrEmpty()) {
                val authInterceptor = Interceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                    chain.proceed(request)
                }
                clientBuilder.addInterceptor(authInterceptor)
            }
        }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientBuilder.build())
            .build()
    }
}
