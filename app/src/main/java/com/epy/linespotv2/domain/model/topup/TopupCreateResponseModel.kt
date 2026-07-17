package com.epy.linespotv2.domain.model.topup

data class TopupCreateResponseModel(
    val topupTransactionId: Long?,
    val topupCode: String?,
    val amount: Long?,
    val adminFee: Long?,
    val totalAmount: Long?,
    val paymentMethodCode: String?,
    val paymentMethodName: String?,
    val paymentStatusCode: String?,
    val paymentStatusName: String?,
    val qrString: String?,
    val expiredAt: String?,
    val createdAt: String?
)