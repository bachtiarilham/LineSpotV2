package com.epy.linespotv2.presentation.home_jukir

sealed class HomeIntent {
    object loadHome : HomeIntent()
    data class clickNotification(val notifId : Long): HomeIntent()
    object clickProfile : HomeIntent()
    object clickTopUp : HomeIntent()
    object clickRiwayat : HomeIntent()
    object clickScanTiket : HomeIntent()
    object clickInputManual: HomeIntent()
    object clickLaporan: HomeIntent()
    object clickBantuan: HomeIntent()
    object dismissError : HomeIntent()

}