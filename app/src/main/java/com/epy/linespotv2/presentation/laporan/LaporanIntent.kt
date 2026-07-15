package com.epy.linespotv2.presentation.laporan

sealed class LaporanIntent {
    object loadFilterPage : LaporanIntent()
    object loadPage : LaporanIntent()

    data class submitFilter(
        val startDate: String,
        val endDate: String,
    ) : LaporanIntent()
}
