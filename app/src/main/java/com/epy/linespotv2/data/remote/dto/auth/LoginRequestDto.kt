package com.epy.linespotv2.data.remote.dto.auth

import com.google.gson.annotations.SerializedName

data class LoginRequestDto(
    @SerializedName("identity") val identity: String,
    @SerializedName("password") val password: String
)
