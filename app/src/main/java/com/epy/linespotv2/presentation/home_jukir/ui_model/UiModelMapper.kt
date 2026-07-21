package com.epy.linespotv2.presentation.home_jukir.ui_model

import com.epy.linespotv2.domain.model.home.JukirHomeModel

fun JukirHomeModel.toUiModel(): HomeJukirUiModel {
    val profile = jukirModel

    // Sesuai requirement: 6 data minggu dari data/skala yang bisa disesuaikan
    val defaultChartBars = listOf(
        ChartBarData(0.4f, "M1"),
        ChartBarData(0.6f, "M2"),
        ChartBarData(0.5f, "M3"),
        ChartBarData(0.7f, "M4"),
        ChartBarData(0.3f, "M5"),
        ChartBarData(0.8f, "M6")
    )

    return HomeJukirUiModel(
        title = profile?.fullName?.ifBlank { "Petugas Dishub" } ?: "Petugas Dishub",
        areaLabel = "Area Tugas",
        zonaValue = profile?.zoneName?.ifBlank { "-" } ?: "-",
        lokasiValue = profile?.lokasiName?.ifBlank { "-" } ?: "-",
        areaValue = profile?.areaName?.ifBlank { "-" } ?: "-",
        pendapatan = profile?.todayIncome ?: 0L,
        saldo = profile?.saldo ?: 0L,
        financeWarning = null,
        supervisorName = "Budi Santoso",
        chartPeriodText = "Bulan Ini",
        chartYLabels = listOf("1jt", "400rb", "0"),
        chartBars = defaultChartBars
    )
}
