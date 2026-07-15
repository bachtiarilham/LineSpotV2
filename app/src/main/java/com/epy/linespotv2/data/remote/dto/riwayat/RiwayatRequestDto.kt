package com.epy.linespotv2.data.remote.dto.riwayat

import com.google.gson.annotations.SerializedName

data class RiwayatRequestDto(
    @SerializedName("startDate")
    val startDate: String?,

    @SerializedName("endDate")
    val endDate: String?,

    @SerializedName("paymentCode")
    val paymentCode: String?,

    @SerializedName("vehicleCode")
    val vehicleCode: String?,

    @SerializedName("lokasiCode")
    val lokasiCode: String?
)
