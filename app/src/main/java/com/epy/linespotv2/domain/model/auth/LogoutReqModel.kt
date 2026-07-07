package com.epy.linespotv2.domain.model.auth

data class LogoutReqModel(
    val accesToken: String,
    val refreshToken : String,
)
