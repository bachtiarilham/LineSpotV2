package com.epy.linespotv2.data.remote.dto.riwayat

import com.google.gson.annotations.SerializedName

data class RiwayatResponseDto(
    @SerializedName("sections")    val sections: List<RiwayatSectionDto>? = null
)

data class RiwayatSectionDto(
    @SerializedName("date")    val date: String? = null,
    @SerializedName("items")    val items: List<RiwayatItemDto>? = null
)

data class RiwayatItemDto(
    @SerializedName("code") val code: String? = null,
    @SerializedName("plate_number") val plateNumber: String? = null,
    @SerializedName("vehicle_type") val vehicleType: String? = null,
    @SerializedName("time") val time: String? = null,
    @SerializedName("amount")   val amount: Long? = null,
    @SerializedName("is_entry") val isEntry: Boolean? = null
)
