package com.epy.linespotv2.data.remote.dto

import com.google.gson.annotations.SerializedName

data class InputManualDto(
    @SerializedName("info_message") val infoMessage: String? = null,
    @SerializedName("nomor_polisi") val nomorPolisi: String? = null,
    @SerializedName("placeholder_nomor_polisi") val placeholderNomorPolisi: String? = null,
    @SerializedName("selected_vehicle") val selectedVehicle: String? = null,
    @SerializedName("vehicle_options") val vehicleOptions: List<InputManualVehicleOptionDto>? = null,
    @SerializedName("waktu_masuk") val waktuMasuk: String? = null,
    @SerializedName("area_parkir") val areaParkir: String? = null,
    @SerializedName("area_options") val areaOptions: List<String>? = null,
    @SerializedName("tarif_summary") val tarifSummary: InputManualTarifSummaryDto? = null
)

data class InputManualVehicleOptionDto(
    @SerializedName("type") val type: String? = null,
    @SerializedName("label") val label: String? = null
)

data class InputManualTarifSummaryDto(
    @SerializedName("durasi_parkir") val durasiParkir: String? = null,
    @SerializedName("total_tarif") val totalTarif: Long? = null
)


