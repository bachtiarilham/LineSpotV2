package com.epy.linespotv2.data.remote.dto.riwayat

import com.google.gson.annotations.SerializedName

data class DetilTransaksiResponseDto(
    @SerializedName("tanggal")
    val tanggal: String,

    @SerializedName("topup_transaction_id")
    val topUpTransactionId: Long,

    @SerializedName("topup_code")
    val topUpCode: String,

    @SerializedName("user_id")
    val userId: Long,

    @SerializedName("wallet_id")
    val walletId: Long,

    @SerializedName("payment_method_id")
    val paymentMethodId: Long,

    @SerializedName("payment_method_code")
    val paymentMethodCode: String,

    @SerializedName("payment_method_name")
    val paymentMethodName: String,

    @SerializedName("amount")
    val amount: Long,

    @SerializedName("admin_fee")
    val adminFee: Long,

    @SerializedName("total_amount")
    val totalAmount: Long,

    @SerializedName("transaction_status")
    val transactionStatus: String,

    @SerializedName("external_reference")
    val externalReference: String? = null,

    @SerializedName("provider_name")
    val providerName: String? = null,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("expired_at")
    val expiredAt: String? = null,

    @SerializedName("paid_at")
    val paidAt: String? = null,

    @SerializedName("completed_at")
    val completedAt: String? = null,

    @SerializedName("failed_reason")
    val failedReason: String? = null
)