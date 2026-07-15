package com.epy.linespotv2.presentation.home_jukir.ui_model

data class HomeJukirUiModel(
    val title: String = "Petugas Dishub",
    val areaLabel: String = "Area Tugas",
    val zonaValue: String = "-",
    val lokasiValue: String = "-",
    val areaValue: String = "-",
    val pendapatan: Long = 0L,
    val saldo: Long = 0L,
    val financeWarning: String? = null,
    val supervisorName: String = "Budi Santoso"
)
