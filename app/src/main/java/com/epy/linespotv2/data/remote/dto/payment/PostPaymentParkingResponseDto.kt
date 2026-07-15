package com.epy.linespotv2.data.remote.dto.payment

import com.google.gson.annotations.SerializedName

data class PostPaymentParkingResponseDto(
    @SerializedName("session_id")
    val sessionId: Long?,

    @SerializedName("session_code")
    val sessionCode: String?,

    @SerializedName("trx_code")
    val transactionCode: String?,

    @SerializedName("plate_number")
    val plateNumber: String?,

    @SerializedName("vhc_type_code")
    val vehicleTypeCode: String?,

    @SerializedName("vhc_type_name")
    val vehicleTypeName: String?,

    @SerializedName("loc_id")
    val locationId: Long?,

    @SerializedName("loc_name")
    val locationName: String?,

    @SerializedName("area_id")
    val areaId: Long?,

    @SerializedName("area_name")
    val areaName: String?,

    @SerializedName("amount")
    val amount: Long?,

    @SerializedName("parking_stat_code")
    val parkingStatusCode: String?,

    @SerializedName("parking_stat_name")
    val parkingStatusName: String?,

    @SerializedName("payment_stat_code")
    val paymentStatusCode: String?,

    @SerializedName("payment_stat_name")
    val paymentStatusName: String?,

    @SerializedName("payment_code")
    val paymentCode: String?,

    @SerializedName("failed_reason")
    val failedReason: String?,

    @SerializedName("receipt_number")
    val receiptNumber: Long?,

    @SerializedName("startedat")
    val startedAt: String?,

    @SerializedName("paidat")
    val paidAt: String?,

    @SerializedName("qr_expiredat")
    val qrExpiredAt: String?
)
