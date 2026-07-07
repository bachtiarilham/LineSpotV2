package com.epy.linespotv2.domain.model.auth

data class RegisterReqModel(
    val fullName: String,
    val nik: String,
    val phone: String,
    val email: String,
    val username: String,
    val password: String,
    val confirmPassword : String,
)
