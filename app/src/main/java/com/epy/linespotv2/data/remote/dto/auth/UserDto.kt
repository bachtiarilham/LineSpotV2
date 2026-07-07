package com.epy.linespotv2.data.remote.dto.auth

import com.epy.linespotv2.data.remote.dto.helper.TarifDto
import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("user_id") val userId: Long = 0L,
    @SerializedName("nik") val nik: String = "",
    @SerializedName("full_name") val fullName: String = "",
    @SerializedName("phone") val phone: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("avatar_url") val avatarUrl: String = "",
    @SerializedName("username") val username: String = "",
    @SerializedName("role_id") val role: Long = 0L,
    @SerializedName("is_verified") val isVerified: Boolean = false,
    @SerializedName("lokasi") val lokasi: String = "",
    @SerializedName("zona") val zona: String = "",
    @SerializedName("tarif") val tarif: List<TarifDto?>? = emptyList(),
    @SerializedName("registeredAt") val registeredAt : String = "",
    @SerializedName("createdAt") val createdAt : String = "",
    @SerializedName("updatedAt")  val updatedAt : String = "",
)
