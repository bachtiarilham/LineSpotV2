package com.epy.linespotv2.domain.model.payment

data class InputManualModel(
    val nomorPolisi: String = "",
    val selectedVehicle: InputManualVehicleType = InputManualVehicleType.MOTOR,
    val waktuMasuk: String = "",
    val areaParkir: String = "",
    val tarifSummary: InputManualTarifSummary = InputManualTarifSummary()
)

data class InputManualVehicleOption(
    val type: InputManualVehicleType,
    val label: String = type.label
)

enum class InputManualVehicleType(val label: String) {
    MOTOR("Motor"),
    MOBIL("Mobil")
}

data class InputManualTarifSummary(
    val durasiParkir: String = "",
    val totalTarif: Long = 0L
)
