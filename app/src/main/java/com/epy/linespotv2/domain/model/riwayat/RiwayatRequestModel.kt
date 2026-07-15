package com.epy.linespotv2.domain.model.riwayat

data class RiwayatRequestModel(
    val startDate: String?,
    val endDate: String?,
    val paymentCode: String?,
    val vehicleCode: String?,
    val lokasiCode: String?
)
