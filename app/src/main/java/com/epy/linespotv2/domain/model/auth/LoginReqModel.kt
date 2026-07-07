package com.epy.linespotv2.domain.model.auth

data class LoginReqModel (
    val identity: String,
    val password: String,
)