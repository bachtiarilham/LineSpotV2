package com.epy.linespotv2.domain.model.parking

data class PostParkingRespModel(
    val sessionId: Long? = null,
    val sessionCode: String? = null,
    val transactionCode: String? = null,
    val plateNumber: String? = null,
    val vehicleTypeCode: String? = null,
    val vehicleTypeName: String? = null,
    val zoneId: Long? = null,
    val zoneName: String? = null,
    val locationId: Long? = null,
    val locationName: String? = null,
    val address: String? = null,
    val areaId: Long? = null,
    val areaName: String? = null,
    val amount: Long? = null,
    val qrString: String? = null,
    val qrExpiredAt: String? = null,
    val paymentCode: String? = null,
    val paymentStatusCode: String? = null,
    val paymentStatusName: String? = null
)
