package com.epy.linespotv2.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AuthLoginRequestDto(
    @SerializedName("identity") val identity: String,
    @SerializedName("password") val password: String
)

data class RefreshTokenRequestDto(
    @SerializedName("refresh_token") val refreshToken: String
)

data class AuthLoginResultDto(
    @SerializedName("auth_user") val user: AuthUserDto,
    @SerializedName("token") val tokens: TokenSetDto
)

data class AuthUserDto(
    @SerializedName("user_id") val userId: Long,
    @SerializedName("nik") val nik: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String,
    @SerializedName("username") val username: String,
    @SerializedName("role_id") val role: Long,
    @SerializedName("is_verified") val isVerified: Boolean,
    @SerializedName("lokasi") val lokasi: String,
    @SerializedName("zona") val zona: String,
    @SerializedName("tarif") val tarif: List<TarifDto>,
)

data class TarifDto(
    @SerializedName("kendaraan") val kendaraan: String,
    @SerializedName("nominal") val nominal: String,
)

data class TokenSetDto(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("expires_at") val expiresAt: Long
)