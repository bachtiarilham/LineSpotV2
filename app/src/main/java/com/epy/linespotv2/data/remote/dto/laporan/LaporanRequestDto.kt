package com.epy.linespotv2.data.remote.dto.laporan

import com.google.gson.annotations.SerializedName

data class LaporanRequestDto(
    @SerializedName("user_id") val userId: Long = 0L,
    @SerializedName("username") val username: String = "",
    @SerializedName("role_id") val roleId: Long = 0L,
    @SerializedName("startDate") val startDate: String = "",
    @SerializedName("endDate") val endDate: String = "",
    @SerializedName("lokasi") val lokasi: String = ""
)
