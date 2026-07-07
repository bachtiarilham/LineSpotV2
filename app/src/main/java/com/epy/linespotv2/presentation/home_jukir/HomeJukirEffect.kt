package com.epy.linespotv2.presentation.home_jukir

sealed interface HomeJukirEffect {
    object NavigateToSettings : HomeJukirEffect
    object NavigateToNotification : HomeJukirEffect
    object NavigateToRiwayat : HomeJukirEffect
    object NavigateToScanTicket : HomeJukirEffect
    object NavigateToInputManual : HomeJukirEffect
    object NavigateToLaporan : HomeJukirEffect
    object NavigateToBantuan : HomeJukirEffect
    object NavigateToTopUp : HomeJukirEffect
    object SessionExpired : HomeJukirEffect
    data class ShowToast(val message : String) : HomeJukirEffect
}