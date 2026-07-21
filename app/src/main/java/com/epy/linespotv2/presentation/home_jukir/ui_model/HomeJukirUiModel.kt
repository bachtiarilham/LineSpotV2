package com.epy.linespotv2.presentation.home_jukir.ui_model

data class ChartBarData(
    val value: Float,       // Tinggi representasi batang (0.0f - 1.0f)
    val labelX: String      // Label di sumbu X (misal "M1", "W1", "1-7 Jul")
)

data class HomeJukirUiModel(
    val title: String = "Petugas Dishub",
    val areaLabel: String = "Area Tugas",
    val zonaValue: String = "-",
    val lokasiValue: String = "-",
    val areaValue: String = "-",
    val pendapatan: Long = 0L,
    val saldo: Long = 0L,
    val financeWarning: String? = null,
    val supervisorName: String = "Budi Santoso",
    val chartPeriodText: String = "Bulan Ini", // Teks periode (misal "Bulan Ini")
    val chartYLabels: List<String> = listOf("1jt", "400rb", "0"), // Skala label Y-axis
    val chartBars: List<ChartBarData> = listOf( // 6 data minggu/titik dari database
        ChartBarData(0.4f, "M1"),
        ChartBarData(0.6f, "M2"),
        ChartBarData(0.5f, "M3"),
        ChartBarData(0.8f, "M4"),
        ChartBarData(0.3f, "M5"),
        ChartBarData(0.7f, "M6")
    )
)
