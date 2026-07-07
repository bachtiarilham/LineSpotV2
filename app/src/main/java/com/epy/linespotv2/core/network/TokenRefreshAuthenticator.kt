package com.epy.linespotv2.core.network

import com.epy.linespotv2.core.preferences.AppPreferences
import com.epy.linespotv2.data.remote.api.ApiService
import com.epy.linespotv2.data.remote.dto.auth.RefreshTokenRequestDto
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class TokenRefreshAuthenticator @Inject constructor(
    private val prefs: AppPreferences,
    @Named("refresh_api_service")
    private val authApiService: ApiService
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= 2) return null
        if (response.request.url.encodedPath == ("/api/v2/linespot/auth/refreshToken")) return null

        val requestToken = response.request.header("Authorization")
            ?.removePrefix("Bearer ")
            ?.trim()
            .orEmpty()
        val currentToken = prefs.token
        val currentRefreshToken = prefs.refreshtoken

        if (currentRefreshToken.isBlank()) return null

        synchronized(this) {
            val latestToken = prefs.token
            if (currentToken.isNotBlank() && latestToken.isNotBlank() && latestToken != requestToken) {
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $latestToken")
                    .build()
            }

            val refreshTokenToUse = prefs.refreshtoken.ifBlank { currentRefreshToken }
            if (refreshTokenToUse.isBlank()) return null

            val refreshed = runBlocking {
                runCatching {
                    authApiService.refreshToken(
                        RefreshTokenRequestDto(refreshToken = refreshTokenToUse)
                    )
                }.getOrNull()
            } ?: return null

            val tokenSet = refreshed.data
            if (!refreshed.success || tokenSet == null || tokenSet.accessToken.isBlank()) {
                prefs.token = ""
                prefs.refreshtoken = ""
                return null
            }

            prefs.token = tokenSet.accessToken
            if (tokenSet.refreshToken.isNotBlank()) {
                prefs.refreshtoken = tokenSet.refreshToken
            }

            return response.request.newBuilder()
                .header("Authorization", "Bearer ${tokenSet.accessToken}")
                .build()
        }
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var prior = response.priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }
}
