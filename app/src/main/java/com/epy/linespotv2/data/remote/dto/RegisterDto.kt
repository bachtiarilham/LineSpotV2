package com.epy.linespotv2.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RegisterRequestDto(
    @SerializedName("full_name")  val fullName: String,
    @SerializedName("nik")        val nik: String,
    @SerializedName("email")      val email: String,
    @SerializedName("phone")      val phone: String,
    @SerializedName("username")   val username: String,
    @SerializedName("password")   val password: String
)

// Response register pakai struktur user yang sama dengan login
data class RegisterResultDto(
    @SerializedName("user") val user: AuthUserDto  // AuthUserDto sudah ada di LoginDto.kt
)