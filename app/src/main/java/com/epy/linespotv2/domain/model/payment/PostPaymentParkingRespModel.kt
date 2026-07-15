package com.epy.linespotv2.domain.model.payment

data class PostPaymentParkingRespModel(
    val sessionId: Long? = null,
    val sessionCode: String? = null,
    val transactionCode: String? = null,
    val plateNumber: String? = null,
    val vehicleTypeCode: String? = null,
    val vehicleTypeName: String? = null,
    val locationId: Long? = null,
    val locationName: String? = null,
    val areaId: Long? = null,
    val areaName: String? = null,
    val amount: Long? = null,
    val parkingStatusCode: String? = null,
    val parkingStatusName: String? = null,
    val paymentStatusCode: String? = null,
    val paymentStatusName: String? = null,
    val paymentCode: String? = null,
    val failedReason: String? = null,
    val receiptNumber: Long? = null,
    val startedAt: String? = null,
    val paidAt: String? = null,
    val qrExpiredAt: String? = null
)
