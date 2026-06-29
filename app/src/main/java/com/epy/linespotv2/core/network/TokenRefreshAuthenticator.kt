package com.epy.linespotv2.core.network

import com.epy.linespotv2.core.preferences.AppPreferences
import com.epy.linespotv2.data.remote.api.ApiService
import com.epy.linespotv2.data.remote.dto.RefreshTokenRequestDto
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
        if (response.request.url.encodedPath.contains("/api/v2/linespot/auth/refresh")) return null

        val requestToken = response.request.header("Authorization")
            ?.removePrefix("Bearer ")
            ?.trim()
            .orEmpty()
        val currentToken = prefs.token
        val refreshToken = prefs.refreshtoken

        if (refreshToken.isBlank()) return null

        synchronized(this) {
            if (prefs.token.isNotBlank() && prefs.token != requestToken) {
                return response.request.newBuilder()
                    .header("Authorization", "Bearer ${prefs.token}")
                    .build()
            }

            val refreshed = runBlocking {
                runCatching {
                    authApiService.refreshToken(
                        RefreshTokenRequestDto(refreshToken = refreshToken)
                    )
                }.getOrNull()
            } ?: return null

            val tokenSet = refreshed.data?.tokens
            if (!refreshed.success || tokenSet == null || tokenSet.accessToken.isBlank()) {
                prefs.token = ""
                prefs.refreshtoken = ""
                return null
            }

            prefs.token = tokenSet.accessToken
            prefs.refreshtoken = tokenSet.refreshToken

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