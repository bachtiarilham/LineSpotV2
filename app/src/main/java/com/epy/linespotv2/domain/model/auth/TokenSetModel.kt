package com.epy.linespotv2.domain.model.auth

data class TokenSetModel(
    val accessToken      : String,
    val refreshToken     : String,
    val tokenType        : String,
    val expiresInSeconds : Long,
)
