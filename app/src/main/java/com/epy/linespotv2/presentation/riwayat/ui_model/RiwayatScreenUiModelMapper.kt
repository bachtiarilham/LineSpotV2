package com.epy.linespotv2.presentation.riwayat.ui_model

import com.epy.linespotv2.core.utils.toRupiah
import com.epy.linespotv2.domain.model.riwayat.RiwayatItem
import com.epy.linespotv2.domain.model.riwayat.RiwayatResponseModel
import com.epy.linespotv2.domain.model.riwayat.RiwayatSection

fun RiwayatResponseModel.toUiModel(): RiwayatScreenUiModel {
    return RiwayatScreenUiModel(
        sections = sections.orEmpty().mapNotNull { it.toUiModelOrNull() }
    )
}

private fun RiwayatSection.toUiModelOrNull(): RiwayatSectionUiModel? {
    val mappedItems = items.orEmpty().map { it.toUiModel() }
    if (date.isNullOrBlank() && mappedItems.isEmpty()) return null

    return RiwayatSectionUiModel(
        date = date.orEmpty().ifBlank { "-" },
        items = mappedItems
    )
}

private fun RiwayatItem.toUiModel(): RiwayatItemUiModel {
    return RiwayatItemUiModel(
        code = code.orEmpty().ifBlank { "-" },
        plateNumber = plateNumber.orEmpty().ifBlank { "-" },
        vehicleType = vehicleType.orEmpty().ifBlank { "-" },
        time = time.orEmpty().ifBlank { "-" },
        amountLabel = (amount ?: 0L).toRupiah(),
        statusLabel = "Masuk"
    )
}
