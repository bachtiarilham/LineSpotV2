package com.epy.linespotv2.presentation.riwayat.ui_model

import com.epy.linespotv2.core.utils.toRupiah
import com.epy.linespotv2.domain.model.riwayat.ParkingItemModel
import com.epy.linespotv2.domain.model.riwayat.RiwayatResponseModel
import com.epy.linespotv2.domain.model.riwayat.TopUpItemModel

fun RiwayatResponseModel.toUiModel(): RiwayatScreenUiModel {
    val sections = mutableListOf<RiwayatSectionUiModel>()

    // Map Parking sections ke UI Model
    parkingSections.forEach { section ->
        val mappedItems = section.items.orEmpty().map { it.toUiModel() }
        if (!section.date.isNullOrBlank() || mappedItems.isNotEmpty()) {
            sections.add(
                RiwayatSectionUiModel(
                    date = section.date.orEmpty().ifBlank { "-" },
                    items = mappedItems
                )
            )
        }
    }

    // Map TopUp / Keuangan sections ke UI Model
    topUpSections.forEach { section ->
        val mappedItems = section.items.orEmpty().map { it.toUiModel() }
        if (!section.date.isNullOrBlank() || mappedItems.isNotEmpty()) {
            sections.add(
                RiwayatSectionUiModel(
                    date = section.date.orEmpty().ifBlank { "-" },
                    items = mappedItems
                )
            )
        }
    }

    // Menggabungkan section dengan tanggal yang sama (opsional) atau urutkan
    return RiwayatScreenUiModel(sections = sections)
}

private fun ParkingItemModel.toUiModel(): RiwayatItemUiModel {
    return RiwayatItemUiModel(
        code = code.orEmpty().ifBlank { "-" },
        plateNumber = plateNumber.orEmpty().ifBlank { "-" },
        vehicleType = vehicleType.orEmpty().ifBlank { "-" },
        time = time.orEmpty().ifBlank { "-" },
        amountLabel = (amount ?: 0L).toRupiah(),
        statusLabel = if (isEntry == true) "Masuk" else "Keluar"
    )
}

private fun TopUpItemModel.toUiModel(): RiwayatItemUiModel {
    return RiwayatItemUiModel(
        code = code.orEmpty().ifBlank { "-" },
        plateNumber = "", // Transaksi keuangan tidak memiliki plat nomor
        vehicleType = providerName.orEmpty().ifBlank { "Top Up Saldo" },
        time = time.orEmpty().ifBlank { "-" },
        amountLabel = (amount ?: 0L).toRupiah(),
        statusLabel = transactionStatus.orEmpty().ifBlank { "Pending" }
    )
}
