package com.example.simplenote.network

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.core.content.edit

object SessionManager {

    private const val PREFS_NAME = "my_secure_prefs"
    private const val KEY_ACCESS_TOKEN = "access_token"
    private const val KEY_REFRESH_TOKEN = "refresh_token"

    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = getPrefs(context)
    }
    private fun getPrefs(context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveTokens(access: String, refresh: String) {
        prefs.edit {
            putString(KEY_ACCESS_TOKEN, access)
                .putString(KEY_REFRESH_TOKEN, refresh)
        }
    }

    fun fetchAccessToken(): String? =
        prefs.getString(KEY_ACCESS_TOKEN, null)

    fun fetchRefreshToken(): String? =
        prefs.getString(KEY_REFRESH_TOKEN, null)

    fun clearTokens() {
        prefs.edit { clear() }
    }

    fun isLoggedIn(): Boolean {
        return fetchAccessToken() != null
    }
}


