package com.epy.linespotv2.presentation.home_jukir

sealed class HomeJukirIntent {
    object loadHomeJukir : HomeJukirIntent()
    data class clickNotification(val notifId : Long): HomeJukirIntent()
    object clickProfile : HomeJukirIntent()
    object clickTopUp : HomeJukirIntent()
    object clickRiwayat : HomeJukirIntent()
    object clickScanTiket : HomeJukirIntent()
    object clickInputManual: HomeJukirIntent()
    object clickLaporan: HomeJukirIntent()
    object clickBantuan: HomeJukirIntent()
    object dismissError : HomeJukirIntent()

}