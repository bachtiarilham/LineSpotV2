package com.epy.linespotv2.presentation.riwayat

sealed class RiwayatIntent {
    object loadPage : RiwayatIntent()
    object clickRiwayatDetail : RiwayatIntent()
    object loadFilterPage : RiwayatIntent()
    data class updateVehicleCode(
        val vehicleCode : String
    ) : RiwayatIntent()
    data class updatePaymentCode(
        val paymentCode : String
    ) : RiwayatIntent()
    data class updateLokasi(
        val lokasi: String
    ) : RiwayatIntent()
    data class submitFilter(
        val startDate: String,
        val endDate: String,
        val paymentCode: String?,
        val vehicleCode: String?,
        val lokasiCode: String?
    ) : RiwayatIntent()
}
