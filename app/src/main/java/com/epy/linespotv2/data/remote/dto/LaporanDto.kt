package com.epy.linespotv2.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LaporanFilterRequestDto(
    @SerializedName("user_id") val userId: Long = 0L,
    @SerializedName("username") val username: String = "",
    @SerializedName("role_id") val roleId: Long = 0L,
    @SerializedName("startDate") val startDate: String = "",
    @SerializedName("endDate") val endDate: String = "",
    @SerializedName("lokasi") val lokasi: String = ""
)

data class LaporanDto(
    @SerializedName("tanggal_terpilih") val tanggalTerpilih: String? = null,
    @SerializedName("periode") val periode: LaporanDateRangeDto? = null,
    @SerializedName("summary") val summary: LaporanSummaryDto? = null,
    @SerializedName("chart_bars") val chartBars: List<LaporanChartBarDto>? = null,
    @SerializedName("payment_summaries") val paymentSummaries: List<LaporanPaymentSummaryDto>? = null,
    @SerializedName("recent_transactions") val recentTransactions: List<LaporanRecentTransactionDto>? = null
)

data class LaporanDateRangeDto(
    @SerializedName("start_date") val startDate: String? = null,
    @SerializedName("end_date") val endDate: String? = null,
    @SerializedName("label") val label: String? = null
)

data class LaporanSummaryDto(
    @SerializedName("total_transaksi") val totalTransaksi: Int? = null,
    @SerializedName("total_pendapatan") val totalPendapatan: Long? = null,
    @SerializedName("rata_rata_transaksi") val rataRataTransaksi: Long? = null
)

data class LaporanChartBarDto(
    @SerializedName("tanggal") val tanggal: String? = null,
    @SerializedName("amount") val amount: Long? = null,
    @SerializedName("value") val value: Float? = null,
    @SerializedName("period_label") val periodLabel: String? = null,
    @SerializedName("period_start_date") val periodStartDate: String? = null,
    @SerializedName("period_end_date") val periodEndDate: String? = null
)

data class LaporanPaymentSummaryDto(
    @SerializedName("label") val label: String? = null,
    @SerializedName("amount") val amount: Long? = null,
    @SerializedName("percentage") val percentage: Int? = null
)

data class LaporanRecentTransactionDto(
    @SerializedName("code") val code: String? = null,
    @SerializedName("time") val time: String? = null,
    @SerializedName("total") val total: Long? = null,
    @SerializedName("payment_tag") val paymentTag: String? = null
)
