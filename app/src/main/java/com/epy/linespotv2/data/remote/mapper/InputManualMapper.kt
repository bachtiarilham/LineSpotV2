package com.epy.linespotv2.data.remote.mapper

import com.epy.linespotv2.data.remote.dto.InputManualDto
import com.epy.linespotv2.data.remote.dto.InputManualTarifSummaryDto
import com.epy.linespotv2.data.remote.dto.InputManualVehicleOptionDto
import com.epy.linespotv2.domain.model.InputManualModel
import com.epy.linespotv2.domain.model.InputManualTarifSummary
import com.epy.linespotv2.domain.model.InputManualVehicleOption
import com.epy.linespotv2.domain.model.InputManualVehicleType

fun InputManualDto?.toDomain(): InputManualModel {
    return InputManualModel(
        infoMessage = this?.infoMessage.orEmpty(),
        nomorPolisi = this?.nomorPolisi.orEmpty(),
        placeholderNomorPolisi = this?.placeholderNomorPolisi.orEmpty(),
        selectedVehicle = this?.selectedVehicle.toVehicleType(),
        vehicleOptions = this?.vehicleOptions.toVehicleOptions(),
        waktuMasuk = this?.waktuMasuk.orEmpty(),
        areaParkir = this?.areaParkir.orEmpty(),
        areaOptions = this?.areaOptions.orEmpty(),
        tarifSummary = this?.tarifSummary.toTarifSummary()
    )
}

private fun List<InputManualVehicleOptionDto>?.toVehicleOptions(): List<InputManualVehicleOption> {
    val mapped = this.orEmpty().map { it.toDomain() }
    return if (mapped.isNotEmpty()) {
        mapped
    } else {
        InputManualVehicleType.entries.map { type ->
            InputManualVehicleOption(type = type)
        }
    }
}

private fun InputManualVehicleOptionDto?.toDomain(): InputManualVehicleOption {
    val type = this?.type.toVehicleType()
    return InputManualVehicleOption(
        type = type,
        label = this?.label?.takeIf { it.isNotBlank() } ?: type.label
    )
}

private fun String?.toVehicleType(): InputManualVehicleType {
    return when (this?.trim()?.uppercase()) {
        "MOBIL", "CAR" -> InputManualVehicleType.MOBIL
        else -> InputManualVehicleType.MOTOR
    }
}

private fun InputManualTarifSummaryDto?.toTarifSummary(): InputManualTarifSummary {
    return InputManualTarifSummary(
        durasiParkir = this?.durasiParkir.orEmpty(),
        totalTarif = this?.totalTarif ?: 0L
    )
}
