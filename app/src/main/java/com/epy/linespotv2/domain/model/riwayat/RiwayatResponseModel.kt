package com.epy.linespotv2.domain.model.riwayat

data class RiwayatResponseModel(
    val parkingSections: List<ParkingSectionModel> = emptyList(),
    val topUpSections: List<TopUpSectionModel> = emptyList()
)

data class ParkingSectionModel(
    val date: String? = null,
    val items: List<ParkingItemModel>? = null
)

data class ParkingItemModel(
    val code: String? = null,
    val plateNumber: String? = null,
    val vehicleType: String? = null,
    val time: String? = null,
    val amount: Long? = null,
    val isEntry: Boolean? = null
)

data class TopUpSectionModel(
    val date: String? = null,
    val items: List<TopUpItemModel>? = null
)

data class TopUpItemModel(
    val code: String? = null,
    val paymentName: String? = null,
    val transactionStatus: String? = null,
    val providerName: String? = null,
    val time: String? = null,
    val amount: Long? = null
)
