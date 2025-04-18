package com.example.simplenote.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class TokenProvider(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun getToken(): String? {
        return prefs.getString("jwt_token", null)
    }

    fun saveToken(token: String) {
        prefs.edit { putString("jwt_token", token) }
    }

    fun clearToken() {
        prefs.edit { remove("jwt_token") }
    }
}
