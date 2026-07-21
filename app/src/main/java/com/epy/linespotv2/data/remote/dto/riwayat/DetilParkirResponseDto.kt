package com.epy.linespotv2.data.remote.dto.riwayat

import com.google.gson.annotations.SerializedName

data class DetilParkirResponseDto(
    @SerializedName("tanggal")
    val tanggal: String,

    @SerializedName("transaction_id")
    val transactionId: Long,

    @SerializedName("transaction_code")
    val transactionCode: String,

    @SerializedName("session_id")
    val sessionId: Long,

    @SerializedName("plate_number")
    val plateNumber: String,

    @SerializedName("vehicle_type_id")
    val vehicleTypeId: Long,

    @SerializedName("vehicle_type_code")
    val vehicleTypeCode: String,

    @SerializedName("vehicle_type_name")
    val vehicleTypeName: String,

    @SerializedName("payment_method_id")
    val paymentMethodId: Long,

    @SerializedName("payment_method_code")
    val paymentMethodCode: String,

    @SerializedName("payment_method_name")
    val paymentMethodName: String,

    @SerializedName("location_id")
    val locationId: Long,

    @SerializedName("location_name")
    val locationName: String,

    @SerializedName("location_address")
    val locationAddress: String,

    @SerializedName("area_id")
    val areaId: Long? = null,

    @SerializedName("area_name")
    val areaName: String? = null,

    @SerializedName("zone_id")
    val zoneId: Long? = null,

    @SerializedName("zone_name")
    val zoneName: String? = null,

    @SerializedName("base_amount")
    val baseAmount: Long,

    @SerializedName("discount_amount")
    val discountAmount: Long,

    @SerializedName("final_amount")
    val finalAmount: Long,

    @SerializedName("company_share")
    val companyShare: Long,

    @SerializedName("jukir_share")
    val jukirShare: Long,

    @SerializedName("tax_amount")
    val taxAmount: Long,

    @SerializedName("fee_amount")
    val feeAmount: Long,

    @SerializedName("transaction_status")
    val transactionStatus: String,

    @SerializedName("operation_type")
    val operationType: String,

    @SerializedName("occurred_at")
    val occurredAt: String,

    @SerializedName("paid_at")
    val paidAt: String,

    @SerializedName("created_at")
    val createdAt: String
)