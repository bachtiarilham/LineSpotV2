package com.epy.linespotv2.data.remote.dto.auth

import com.google.gson.annotations.SerializedName

data class LogoutRequestDto (
    @SerializedName ("refresh_token") val refreshToken : String
)