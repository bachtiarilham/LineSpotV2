package com.epy.linespotv2.domain.model.auth

//data class RegisterModel(
//    val userId: Long,
//    val fullName: String,
//    val email: String,
//    val username: String,
//    val role: Long,
//    val isVerified: Boolean
//)

data class RegisterRequestModel(
    val fullName: String,
    val nik: String,
    val phone: String,
    val email: String,
    val username: String,
    val password: String,
)