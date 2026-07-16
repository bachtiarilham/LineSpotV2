package com.epy.linespotv2.domain.model.riwayat

data class RiwayatResponseModel(
    val sections: List<RiwayatSection>? = null
)

data class RiwayatSection(
    val date: String? = null,
    val items: List<RiwayatItem>? = null
)

data class RiwayatItem(
    val code: String? = null,
    val plateNumber: String? = null,
    val vehicleType: String? = null,
    val time: String? = null,
    val amount: Long? = null,
)
