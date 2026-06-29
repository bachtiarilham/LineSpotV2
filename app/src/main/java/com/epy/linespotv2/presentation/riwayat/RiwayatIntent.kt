package com.epy.linespotv2.presentation.riwayat

import com.epy.linespotv2.domain.model.RiwayatPaymentFilter
import com.epy.linespotv2.domain.model.RiwayatTransactionFilter
import com.epy.linespotv2.domain.model.RiwayatVehicleFilter

sealed class RiwayatIntent {
    object loadPage : RiwayatIntent()
    object clickRiwayatDetail : RiwayatIntent()
    object loadFilterPage : RiwayatIntent()
    data class selectLokasi(
        val lokasi: String
    ) : RiwayatIntent()
    data class submitFilter(
        val startDate: String,
        val endDate: String,
        val transaction: RiwayatTransactionFilter,
        val payment: RiwayatPaymentFilter,
        val vehicle: RiwayatVehicleFilter,
        val lokasi: String
    ) : RiwayatIntent()
}
