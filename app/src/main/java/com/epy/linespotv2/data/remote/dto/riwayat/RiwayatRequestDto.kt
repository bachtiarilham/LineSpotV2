package com.epy.linespotv2.data.remote.dto.riwayat

import com.google.gson.annotations.SerializedName

data class RiwayatRequestDto(
    @SerializedName("user_id") val userId: Long = 0L,
    @SerializedName("username") val username: String = "",
    @SerializedName("role_id") val roleId: Long = 0L,
    @SerializedName("startDate") val startDate: String = "",
    @SerializedName("endDate") val endDate: String = "",
    @SerializedName("payment") val payment: String = "ALL",
    @SerializedName("vehicle") val vehicle: String = "ALL",
    // masih bingung implementasi lokasi ini
    @SerializedName("lokasi") val lokasi: String = "Semua Area"
)
