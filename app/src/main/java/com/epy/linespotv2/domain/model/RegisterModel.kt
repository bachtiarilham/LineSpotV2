package com.epy.linespotv2.domain.model

data class RegisterModel(
    val userId: Long,
    val fullName: String,
    val email: String,
    val username: String,
    val role: Long,
    val isVerified: Boolean
)