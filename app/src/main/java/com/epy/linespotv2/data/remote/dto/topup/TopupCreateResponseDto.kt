package com.epy.linespotv2.data.remote.dto.topup

import com.google.gson.annotations.SerializedName

data class TopupCreateResponseDto(
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

    @SerializedName("paymentMethodName")
    val paymentMethodName: String?,

    @SerializedName("paymentStatusCode")
    val paymentStatusCode: String?,

    @SerializedName("paymentStatusName")
    val paymentStatusName: String?,

    @SerializedName("qrString")
    val qrString: String?,

    @SerializedName("expiredAt")
    val expiredAt: String?, // Menggunakan String untuk parsing tanggal dari JSON

    @SerializedName("createdAt")
    val createdAt: String?
)
