package com.epy.linespotv2.data.remote.dto.laporan

import com.google.gson.annotations.SerializedName

data class LaporanRequestDto(
    @SerializedName("startDate")
    val startDate: String = "",
    @SerializedName("endDate")
    val endDate: String = "",
)
