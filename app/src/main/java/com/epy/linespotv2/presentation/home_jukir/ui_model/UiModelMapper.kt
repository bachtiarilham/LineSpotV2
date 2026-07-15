package com.epy.linespotv2.presentation.home_jukir.ui_model

import com.epy.linespotv2.domain.model.home.JukirHomeModel

fun JukirHomeModel.toUiModel(): HomeJukirUiModel {
    val profile = jukirModel

    return HomeJukirUiModel(
        title = profile?.fullName?.ifBlank { "Petugas Dishub" } ?: "Petugas Dishub",
        areaLabel = "Area Tugas",
        zonaValue = profile?.zoneName?.ifBlank { "-" } ?: "-",
        lokasiValue = profile?.lokasiName?.ifBlank { "-" } ?: "-",
        areaValue = profile?.areaName?.ifBlank { "-" } ?: "-",
        pendapatan = profile?.todayIncome ?: 0L,
        saldo = profile?.saldo ?: 0L,
        financeWarning = null,
        supervisorName = "Budi Santoso"
    )
}
