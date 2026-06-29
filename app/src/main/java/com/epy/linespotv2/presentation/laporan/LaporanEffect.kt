package com.epy.linespotv2.presentation.laporan

sealed class LaporanEffect {
    object NavigateToLaporan : LaporanEffect()
    object NavigateToFilter : LaporanEffect()
}
