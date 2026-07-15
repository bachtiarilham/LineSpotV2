package com.epy.linespotv2.data.remote.dto.parking

import com.google.gson.annotations.SerializedName

data class PostParkingResponseDto(
    @SerializedName("session_id")
    val sessionId: Long?,
    @SerializedName("session_code")
    val sessionCode: String?,
    @SerializedName("trx_code")
    val transactionCode: String?,
    @SerializedName("plate_nums")
    val plateNumber: String?,
    @SerializedName("vhc_type_code")
    val vehicleTypeCode: String?,
    @SerializedName("vhc_type_name")
    val vehicleTypeName: String?,
    @SerializedName("zone_id")
    val zoneId: Long?,
    @SerializedName("zone_name")
    val zoneName: String?,
    @SerializedName("loc_id")
    val locationId: Long?,
    @SerializedName("loc_name")
    val locationName: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("adea_id")
    val areaId: Long?,
    @SerializedName("area_name")
    val areaName: String?,
    @SerializedName("amount")
    val amount: Long?,
    @SerializedName("qr_str")
    val qrString: String?,
    @SerializedName("qr_expiredat")
    val qrExpiredAt: String?,
    @SerializedName("payment_code")
    val paymentCode: String?,
    @SerializedName("payment_stat_code")
    val paymentStatusCode: String?,
    @SerializedName("payment_stat_name")
    val paymentStatusName: String?
)
