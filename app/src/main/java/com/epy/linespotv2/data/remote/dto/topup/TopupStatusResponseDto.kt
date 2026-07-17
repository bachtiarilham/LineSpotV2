package com.epy.linespotv2.data.remote.dto.topup

import com.google.gson.annotations.SerializedName

data class TopupStatusResponseDto(
    @SerializedName("topupTransactionId")
    val topupTransactionId: Long?,

    @SerializedName("topupCode")
    val topupCode: String?,

    @SerializedName("amount")
    val amount: Long?,

    @SerializedName("adminFee")
    val adminFee: Long?,

    @SerializedName("totalAmount")
    val totalAmount: Long?,

    @SerializedName("paymentMethodCode")
    val paymentMethodCode: String?,

    @SerializedName("paymentStatusCode")
    val paymentStatusCode: String?,

    @SerializedName("paymentMethodName")
    val paymentMethodName: String?,

    @SerializedName("qrString")
    val qrString: String?,

    @SerializedName("paidAt")
    val paidAt: String?, // Nullable karena pointer & omitempty

    @SerializedName("expiredAt")
    val expiredAt: String?,

    @SerializedName("failedReason")
    val failedReason: String?, // Nullable karena omitempty

    @SerializedName("completedAt")
    val completedAt: String?, // Nullable karena pointer & omitempty

    @SerializedName("currentBalance")
    val currentBalance: Long?
)
