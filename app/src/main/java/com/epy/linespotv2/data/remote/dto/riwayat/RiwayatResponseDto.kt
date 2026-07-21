package com.epy.linespotv2.data.remote.dto.riwayat

import com.google.gson.annotations.SerializedName

data class RiwayatResponseDto(
    @SerializedName("parking_sections")
    val parkingSections: List<ParkingSectionDto> = emptyList(),

    @SerializedName("topup_sections")
    val topUpSections: List<TopUpSectionDto> = emptyList()
)

data class ParkingSectionDto(
    @SerializedName("date")
    val date: String? = null,

    @SerializedName("items")
    val items: List<ParkingItemDto>? = null
)

data class ParkingItemDto(
    @SerializedName("code")
    val code: String? = null,

    @SerializedName("plate_number")
    val plateNumber: String? = null,

    @SerializedName("vehicle_type")
    val vehicleType: String? = null,

    @SerializedName("time")
    val time: String? = null,

    @SerializedName("amount")
    val amount: Long? = null,

    @SerializedName("is_entry")
    val isEntry: Boolean? = null
)

data class TopUpSectionDto(
    @SerializedName("date")
    val date: String? = null,

    @SerializedName("items")
    val items: List<TopUpItemDto>? = null
)

data class TopUpItemDto(
    @SerializedName("code")
    val code: String? = null,

    @SerializedName("payment_name")
    val paymentName: String? = null,

    @SerializedName("transaction_status")
    val transactionStatus: String? = null,

    @SerializedName("provider_name")
    val providerName: String? = null,

    @SerializedName("time")
    val time: String? = null,

    @SerializedName("amount")
    val amount: Long? = null
)
