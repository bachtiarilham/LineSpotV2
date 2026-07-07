package com.epy.linespotv2.domain.model.auth

data class RefreshTokenRespModel(
    val accessToken : String,
    val refreshToken : String,
    val expiresAt :    Long,
)
