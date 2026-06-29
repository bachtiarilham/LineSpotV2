package com.epy.linespotv2.presentation.riwayat

sealed interface RiwayatEffect {
    object NavigateToFilter : RiwayatEffect
    object NavigateToDetail : RiwayatEffect
    object NavigateToRiwayat : RiwayatEffect
}