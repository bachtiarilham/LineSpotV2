package com.epy.linespotv2.domain.model.riwayat

data class RiwayatRequestModel(
    val userId : Long,
    val username : String,
    val roleId : Long,
    val startDate : String,
    val endDate : String,
    val payment : String,
    val vehicle : String,
    val lokasi : String,
)

enum class RiwayatPaymentFilter {
    ALL,
    QRIS,
    NON_TUNAI
}

enum class RiwayatVehicleFilter {
    ALL,
    MOTOR,
    MOBIL
}