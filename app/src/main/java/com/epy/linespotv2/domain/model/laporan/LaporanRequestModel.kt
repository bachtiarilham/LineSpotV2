package com.epy.linespotv2.domain.model.laporan

data class LaporanRequestModel(
    val userId: Long,
    val username: String,
    val roleId: Long,
    val startDate: String,
    val endDate: String,
    val lokasi: String
)
