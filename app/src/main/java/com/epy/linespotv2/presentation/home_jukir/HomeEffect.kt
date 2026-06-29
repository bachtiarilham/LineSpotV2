package com.epy.linespotv2.presentation.home_jukir

sealed interface HomeEffect {
    object NavigateToSettings : HomeEffect
    object NavigateToNotification : HomeEffect
    object NavigateToRiwayat : HomeEffect
    object NavigateToScanTicket : HomeEffect
    object NavigateToInputManual : HomeEffect
    object NavigateToLaporan : HomeEffect
    object NavigateToBantuan : HomeEffect
    object NavigateToTopUp : HomeEffect
    object SessionExpired : HomeEffect
    data class ShowToast(val message : String) : HomeEffect
}