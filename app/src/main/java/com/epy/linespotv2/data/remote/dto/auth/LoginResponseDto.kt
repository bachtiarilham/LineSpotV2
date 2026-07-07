package com.epy.linespotv2.data.remote.dto.auth

import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    @SerializedName("userDto") val user: UserDto,
    @SerializedName("token") val tokens: TokenSetDto
)





