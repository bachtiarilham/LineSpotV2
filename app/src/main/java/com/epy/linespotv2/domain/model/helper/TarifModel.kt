package com.epy.linespotv2.domain.model.helper

data class TarifModel(
    val tarifResponseItemDto: List<TarifItemModel> = emptyList()
)

data class TarifItemModel(
    val kendaraanId: Long? = 0L,
    val kendaraanKode: String? = "",
    val kendaraanNama: String? = "",
    val nominal: Long? = 0L
)
