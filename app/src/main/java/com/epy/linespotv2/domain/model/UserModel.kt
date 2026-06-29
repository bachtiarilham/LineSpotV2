// domain/model/UserModel.kt
package com.epy.linespotv2.domain.model

data class UserModel(
    val userId: Long,
    val nik: String,
    val fullName: String,
    val phone: String,
    val email: String,
    val username: String,
    val role: Long,
    val isVerified: Boolean
)