package com.epy.linespotv2.domain.model.auth

import com.epy.linespotv2.domain.model.helper.TarifModel

data class UserModel(
    val userId: Long,
    val nik: String,
    val fullName: String,
    val phone: String,
    val email: String,
    val username: String,
    val avatar_url : String,
    val role: Long,
    val isVerified: Boolean,
    val lokasi: String,
    val zona: String,
    val tarif: List<TarifModel>,
    val registeredAt : String,
    val createdAt : String,
    val updatedAt : String,
)