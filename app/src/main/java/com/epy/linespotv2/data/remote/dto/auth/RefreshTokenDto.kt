package com.epy.linespotv2.data.remote.dto.auth

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequestDto (
    @SerializedName ("refresh_token") val refreshToken : String
)

data class RefreshTokenResponseDto (
    @SerializedName ("TokenSetDto") val tokenSetDto: TokenSetDto
)