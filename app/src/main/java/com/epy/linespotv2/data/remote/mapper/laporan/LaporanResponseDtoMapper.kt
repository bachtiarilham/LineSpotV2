package com.epy.linespotv2.data.remote.mapper.laporan

import com.epy.linespotv2.data.remote.dto.laporan.LaporanChartBarDto
import com.epy.linespotv2.data.remote.dto.laporan.LaporanDateRangeDto
import com.epy.linespotv2.data.remote.dto.laporan.LaporanResponseDto
import com.epy.linespotv2.data.remote.dto.laporan.LaporanPaymentSummaryDto
import com.epy.linespotv2.data.remote.dto.laporan.LaporanRecentTransactionDto
import com.epy.linespotv2.data.remote.dto.laporan.LaporanSummaryDto
import com.epy.linespotv2.domain.model.laporan.LaporanChartBar
import com.epy.linespotv2.domain.model.laporan.LaporanDateRange
import com.epy.linespotv2.domain.model.laporan.LaporanResponseModel
import com.epy.linespotv2.domain.model.laporan.LaporanPaymentSummary
import com.epy.linespotv2.domain.model.laporan.LaporanRecentTransaction
import com.epy.linespotv2.domain.model.laporan.LaporanSummary

fun LaporanResponseDto?.toDomain(): LaporanResponseModel {
    return LaporanResponseModel(
        tanggalTerpilih = this?.tanggalTerpilih.orEmpty(),
        periode = this?.periode.toDomain(),
        summary = this?.summary.toDomain(),
        chartBars = this?.chartBars.orEmpty().map { it.toDomain() },
        paymentSummaries = this?.paymentSummaries.orEmpty().map { it.toDomain() },
        recentTransactions = this?.recentTransactions.orEmpty().map { it.toDomain() }
    )
}

fun LaporanDateRangeDto?.toDomain(): LaporanDateRange {
    return LaporanDateRange(
        startDate = this?.startDate.orEmpty(),
        endDate = this?.endDate.orEmpty(),
        label = this?.label.orEmpty()
    )
}

fun LaporanSummaryDto?.toDomain(): LaporanSummary {
    return LaporanSummary(
        totalTransaksi = this?.totalTransaksi ?: 0,
        totalPendapatan = this?.totalPendapatan ?: 0L,
        rataRataTransaksi = this?.rataRataTransaksi ?: 0L
    )
}

fun LaporanChartBarDto?.toDomain(): LaporanChartBar {
    return LaporanChartBar(
        tanggal = this?.tanggal.orEmpty(),
        amount = this?.amount ?: 0L,
        value = this?.value ?: 0f,
        periodLabel = this?.periodLabel ?: this?.tanggal.orEmpty(),
        periodStartDate = this?.periodStartDate.orEmpty(),
        periodEndDate = this?.periodEndDate.orEmpty()
    )
}

fun LaporanPaymentSummaryDto?.toDomain(): LaporanPaymentSummary {
    return LaporanPaymentSummary(
        label = this?.label.orEmpty(),
        amount = this?.amount ?: 0L,
        percentage = this?.percentage ?: 0
    )
}

fun LaporanRecentTransactionDto?.toDomain(): LaporanRecentTransaction {
    return LaporanRecentTransaction(
        code = this?.code.orEmpty(),
        time = this?.time.orEmpty(),
        total = this?.total ?: 0L,
        paymentTag = this?.paymentTag.orEmpty()
    )
}
