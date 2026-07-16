package com.epy.linespotv2.presentation.laporan.ui_model

import com.epy.linespotv2.core.utils.toRupiah
import com.epy.linespotv2.domain.model.laporan.LaporanItemModel
import com.epy.linespotv2.domain.model.laporan.LaporanResponseModel

fun LaporanResponseModel.toUiModel(): LaporanScreenUiModel {
    val items = pendapatanPerTanggal.orEmpty()
    val totalTransaksiValue = totalTransaksi ?: 0L
    val totalPendapatanValue = totalPendapatanJukir ?: 0L
    val rataRata = if (totalTransaksiValue > 0) {
        totalPendapatanValue / totalTransaksiValue
    } else {
        0L
    }

    return LaporanScreenUiModel(
        selectedDateLabel = buildSelectedDateLabel(),
        totalTransaksiLabel = totalTransaksiValue.toString(),
        totalPendapatanLabel = totalPendapatanValue.toRupiah(),
        rataRataTransaksiLabel = rataRata.toRupiah(),
        summaryItems = items.toSummaryItems(),
        chartItems = items.toChartItems()
    )
}

private fun LaporanResponseModel.buildSelectedDateLabel(): String {
    val start = tanggalAwal.orEmpty()
    val end = tanggalAkhir.orEmpty()

    return when {
        start.isNotBlank() && end.isNotBlank() -> "$start - $end"
        start.isNotBlank() -> start
        end.isNotBlank() -> end
        else -> "-"
    }
}

private fun List<LaporanItemModel>.toSummaryItems(): List<LaporanSummaryItemUiModel> {
    if (isEmpty()) {
        return emptyList()
    }

    val totalMotor = sumOf { it.motorCount ?: 0L }
    val totalMobil = sumOf { it.carCount ?: 0L }
    val totalQris = sumOf { it.qrisCount ?: 0L }
    val totalTunai = sumOf { it.cashCount ?: 0L }

    return listOf(
        LaporanSummaryItemUiModel(label = "Tunai", value = totalTunai.toString()),
        LaporanSummaryItemUiModel(label = "QRIS", value = totalQris.toString()),
        LaporanSummaryItemUiModel(label = "Motor", value = totalMotor.toString()),
        LaporanSummaryItemUiModel(label = "Mobil", value = totalMobil.toString())
    )
}

private fun List<LaporanItemModel>.toChartItems(): List<LaporanChartItemUiModel> {
    if (isEmpty()) return emptyList()

    val maxAmount = maxOf { it.totalPendapatanJukir ?: 0L }

    return map { item ->
        val amount = item.totalPendapatanJukir ?: 0L
        LaporanChartItemUiModel(
            tanggal = item.tanggal.toChartLabel(),
            valueLabel = amount.toChartAmountLabel(),
            amount = amount,
            isHighlighted = amount == maxAmount && maxAmount > 0L
        )
    }
}

private fun String?.toChartLabel(): String {
    val value = this.orEmpty().trim()
    if (value.isBlank()) return "-"

    return value
        .split("-")
        .getOrNull(2)
        ?.take(2)
        ?: value
}

private fun Long.toChartAmountLabel(): String {
    if (this <= 0L) return "0"
    return when {
        this >= 1_000_000L -> "${this / 1_000_000} jt"
        this >= 1_000L -> "${this / 1_000} rb"
        else -> toString()
    }
}
