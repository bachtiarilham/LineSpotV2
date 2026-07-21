package com.epy.linespotv2.presentation.riwayat

sealed class RiwayatIntent {
    object loadPage : RiwayatIntent()
    object loadFilterPage : RiwayatIntent()
    
    data class changeTab(val tab: RiwayatTab) : RiwayatIntent()
    
    data class clickRiwayatDetail(
        val code: String,
        val isParking: Boolean
    ) : RiwayatIntent()
    
    data class submitFilter(
        val startDate: String,
        val endDate: String
    ) : RiwayatIntent()
}
