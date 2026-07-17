package com.epy.linespotv2.domain.model.topup

data class TopupStatusResponseModel(
    val topupTransactionId: Long?,
    val topupCode: String?,
    val amount: Long?,
    val adminFee: Long?,
    val totalAmount: Long?,
    val paymentMethodCode: String?,
    val paymentStatusCode: String?,
    val paymentMethodName: String?,
    val qrString: String?,
    val paidAt: String?,
    val expiredAt: String?,
    val failedReason: String?,
    val completedAt: String?,
    val currentBalance: Long?
)