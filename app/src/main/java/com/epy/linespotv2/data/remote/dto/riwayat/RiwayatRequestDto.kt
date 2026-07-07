package com.epy.linespotv2.data.remote.dto.riwayat

import com.epy.linespotv2.domain.model.riwayat.RiwayatPaymentFilter
import com.epy.linespotv2.domain.model.riwayat.RiwayatVehicleFilter
import com.google.gson.annotations.SerializedName

data class RiwayatRequestDto(
    @SerializedName("user_id") val userId: Long = 0L,
    @SerializedName("username") val username: String = "",
    @SerializedName("role_id") val roleId: Long = 0L,
    @SerializedName("startDate") val startDate: String = "",
    @SerializedName("endDate") val endDate: String = "",
    @SerializedName("payment") val payment: RiwayatPaymentFilter = RiwayatPaymentFilter.ALL,
    @SerializedName("vehicle") val vehicle: RiwayatVehicleFilter = RiwayatVehicleFilter.ALL,
    @SerializedName("lokasi") val lokasi: String = "Semua Area"
)
