package com.epy.linespotv2.domain.model.helper

data class LokasiModel(
    val lokasi: List<LokasiItemModel>? = null
)

data class LokasiItemModel(
    val lokasiId: Long? = 0L,
    val namaLokasi: String? = "",
    val areaId: Long? = 0L,
    val namaArea: String? = "",
    val zonaId: Long? = 0L,
    val namaZona: String? = "",
    val address: String? = ""
)
