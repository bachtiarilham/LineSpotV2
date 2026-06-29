package com.epy.linespotv2.domain.model

data class InputManualModel(
    val infoMessage: String = "",
    val nomorPolisi: String = "",
    val placeholderNomorPolisi: String = "",
    val selectedVehicle: InputManualVehicleType = InputManualVehicleType.MOTOR,
    val vehicleOptions: List<InputManualVehicleOption> = InputManualVehicleType.entries.map {
        InputManualVehicleOption(type = it)
    },
    val waktuMasuk: String = "",
    val areaParkir: String = "",
    val areaOptions: List<String> = emptyList(),
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
