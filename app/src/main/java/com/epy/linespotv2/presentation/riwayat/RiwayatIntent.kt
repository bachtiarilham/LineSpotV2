package com.epy.linespotv2.presentation.riwayat

sealed class RiwayatIntent {
    object loadPage : RiwayatIntent()
    object clickRiwayatDetail : RiwayatIntent()
    object loadFilterPage : RiwayatIntent()
    data class selectVehicle(
        val vehicle : String
    ) : RiwayatIntent()
    data class selectPayment(
        val payment : String
    ) : RiwayatIntent()
    data class selectLokasi(
        val lokasi: String
    ) : RiwayatIntent()
    data class submitFilter(
        val startDate: String,
        val endDate: String,
        val payment: String,
        val vehicle: String,
        val lokasi: String
    ) : RiwayatIntent()
}
