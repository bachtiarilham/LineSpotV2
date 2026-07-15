package com.epy.linespotv2.data.remote.dto.auth

import com.google.gson.annotations.SerializedName

data class LoginRespDto (
    @SerializedName("token_set") val tokenSetDto: TokenSetDto,
    @SerializedName("role_id") val roleId: Long
)