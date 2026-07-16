package com.epy.linespotv2.data.remote.dto.auth

import com.google.gson.annotations.SerializedName

data class TokenSetDto(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("expires_in") val expiresIn: Long
)