package com.example.simplenote.network

import com.example.simplenote.data.RefreshRequest
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Route

class TokenAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: okhttp3.Response): Request? {
        // This will be called if the server returns 401 Unauthorized

        val refreshToken = SessionManager.fetchRefreshToken().toString()
        val newTokenResponse = BackendApi.api.refreshToken(RefreshRequest(refreshToken)).execute()

        return if (newTokenResponse.isSuccessful) {
            val tokens = newTokenResponse.body()
            if (tokens != null) {
                SessionManager.saveTokens(tokens.access, refreshToken)
                // Retry the original request with new token
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${tokens.access}")
                    .build()
            } else {
                null // give up -> user must log in again
            }
        } else {
            null
        }
    }
}
