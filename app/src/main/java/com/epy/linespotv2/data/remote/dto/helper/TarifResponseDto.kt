package com.epy.linespotv2.data.remote.dto.helper

import com.google.gson.annotations.SerializedName

data class TarifResponseDto(
    @SerializedName("tarif_item") val tarifResponseItemDto: List<TarifResponseItemDto>,
)

data class TarifResponseItemDto(
    @SerializedName("kendaraan_id") val kendaraanId: Long? = 0L,
    @SerializedName("kendaraan_kode") val kendaraanKode: String? = "",
    @SerializedName("kendaraan_nama") val kendaraanNama: String? = "",
    @SerializedName("nominal") val nominal: Long? = 0L,
)