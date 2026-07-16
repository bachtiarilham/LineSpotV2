package com.epy.linespotv2.data.remote.dto.helper

import com.google.gson.annotations.SerializedName

data class LokasiResponseDto(
    @SerializedName("lokasi_item") val lokasi: List<LokasiItemResponseDto>? = null
)

data class LokasiItemResponseDto(
    @SerializedName("lokasi_id") val lokasiId: Long? = 0L,
    @SerializedName("nama_lokasi") val namaLokasi: String? = "",
    @SerializedName("area_id") val areaId: Long? = 0L,
    @SerializedName("nama_area") val namaArea: String? = "",
    @SerializedName("zona_id") val zonaId: Long? = 0L,
    @SerializedName("nama_zona") val namaZona: String? = "",
    @SerializedName("address") val address: String? = "",
)
