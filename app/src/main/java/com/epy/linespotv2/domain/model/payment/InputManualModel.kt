package com.epy.linespotv2.domain.model.payment

data class InputManualModel(
    val infoMessage: String? = null,
    val nomorPolisi: String? = null,
    val placeholderNomorPolisi: String? = null,
    val selectedVehicle: String? = null,
    val vehicleOptions: List<InputManualVehicleOption>? = null,
    val waktuMasuk: String? = null,
    val areaParkir: String? = null,
    val areaOptions: List<String>? = null,
    val tarifSummary: InputManualTarifSummary? = null
)

data class InputManualVehicleOption(
    val type: String? = null,
    val label: String? = null
)

data class InputManualTarifSummary(
    val durasiParkir: String? = null,
    val totalTarif: Long? = null
)
