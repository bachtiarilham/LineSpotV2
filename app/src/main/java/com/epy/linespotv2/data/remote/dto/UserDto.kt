// data/remote/dto/UserDto.kt
package com.epy.linespotv2.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserProfileDto(
    @SerializedName("user_id")  val userId: Long?,
    @SerializedName("nik")      val nik: String?,
    @SerializedName("full_name") val fullName: String?,
    @SerializedName("phone")    val phone: String?,
    @SerializedName("email")    val email: String?,
    @SerializedName("username") val username: String?,
    @SerializedName("roleId")     val role: Long?,
    @SerializedName("is_verified") val isVerified: Boolean?
)