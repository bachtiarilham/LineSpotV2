package com.epy.linespotv2.presentation.input_manual.ui_model

import com.epy.linespotv2.core.utils.toRupiah
import com.epy.linespotv2.domain.model.payment.InputManualModel

fun InputManualModel.toUiModel(): InputManualUiModel {
    val selected = selectedVehicle.toVehicleUiFilter()

    return InputManualUiModel(
        nomorPolisi = nomorPolisi.orEmpty(),
        selectedVehicle = selected,
        waktuMasuk = waktuMasuk.orEmpty().ifBlank { "-" },
        areaParkir = areaParkir.orEmpty().ifBlank { "-" },
        totalTarifLabel = (tarifSummary?.totalTarif ?: 0L).toRupiah()
    )
}

fun String?.toVehicleUiFilter(): InputManualVehicleUiFilter {
    return InputManualVehicleUiFilter.entries.firstOrNull {
        it.code.equals(this, ignoreCase = true) || it.label.equals(this, ignoreCase = true)
    } ?: InputManualVehicleUiFilter.MOTOR
}
