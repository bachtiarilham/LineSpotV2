package com.epy.linespotv2.presentation.laporan.ui_model

data class LaporanScreenUiModel(
    val selectedDateLabel: String = "-",
    val totalTransaksiLabel: String = "0",
    val totalPendapatanLabel: String = "Rp 0",
    val rataRataTransaksiLabel: String = "Rp 0",
    val summaryItems: List<LaporanSummaryItemUiModel> = emptyList(),
    val chartItems: List<LaporanChartItemUiModel> = emptyList()
)

data class LaporanSummaryItemUiModel(
    val label: String,
    val value: String
)

data class LaporanChartItemUiModel(
    val tanggal: String,
    val valueLabel: String,
    val amount: Long,
    val isHighlighted: Boolean = false
)
