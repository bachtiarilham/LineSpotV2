package com.epy.linespotv2.domain.model.riwayat


data class DetilTransaksiResponseModel(
    val tanggal: String,
    val topUpTransactionId: Long,
    val topUpCode: String,
    val userId: Long,
    val walletId: Long,
    val paymentMethodId: Long,
    val paymentMethodCode: String,
    val paymentMethodName: String,
    val amount: Long,
    val adminFee: Long,
    val totalAmount: Long,
    val transactionStatus: String,
    val externalReference: String? = null,
    val providerName: String? = null,
    val createdAt: String,
    val expiredAt: String? = null,
    val paidAt: String? = null,
    val completedAt: String? = null,
    val failedReason: String? = null
)