package com.epy.linespotv2.domain.model

data class LaporanModel(
    val tanggalTerpilih : String = "",
    val periode: LaporanDateRange = LaporanDateRange(),
    val summary: LaporanSummary = LaporanSummary(),
    val chartBars: List<LaporanChartBar> = emptyList(),
    val paymentSummaries: List<LaporanPaymentSummary> = emptyList(),
    val recentTransactions: List<LaporanRecentTransaction> = emptyList()
)

data class LaporanDateRange(
    val startDate: String = "",
    val endDate: String = "",
    val label: String = ""
)

data class LaporanSummary(
    val totalTransaksi: Int = 0,
    val totalPendapatan: Long = 0L,
    val rataRataTransaksi: Long = 0L
)

data class LaporanChartBar(
    val tanggal: String = "",
    val amount: Long = 0L,
    val value: Float = 0f,
    val periodLabel: String = tanggal,
    val periodStartDate: String = "",
    val periodEndDate: String = ""
)

data class LaporanPaymentSummary(
    val label: String = "",
    val amount: Long = 0L,
    val percentage: Int = 0
)

data class LaporanRecentTransaction(
    val code: String = "",
    val time: String = "",
    val total: Long = 0L,
    val paymentTag: String = ""
)
