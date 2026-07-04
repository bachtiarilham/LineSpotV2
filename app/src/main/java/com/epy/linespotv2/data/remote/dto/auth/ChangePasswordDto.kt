package com.epy.linespotv2.data.remote.dto.auth

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequestDto (
    @SerializedName ("old_password") val oldPassword : String,
    @SerializedName ("new_password") val newPassword : String,
)

data class ChangePasswordResponseDto (
    @SerializedName ("message") val message : String,
)