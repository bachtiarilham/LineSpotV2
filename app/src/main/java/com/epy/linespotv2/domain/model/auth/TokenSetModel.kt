package com.epy.linespotv2.domain.model.auth

data class TokenSetModel(
    val accessToken: String,
    val refreshToken: String,
    val expiresAt: Long
)
