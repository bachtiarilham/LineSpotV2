package com.epy.linespotv2.domain.model

data class LoginModel(
    val token: String,

    val id: Int,
    val nama: String,
    val email: String,
    val role: Long,
    val avatar_url: String,

    val theme: String,
    val language: String
)