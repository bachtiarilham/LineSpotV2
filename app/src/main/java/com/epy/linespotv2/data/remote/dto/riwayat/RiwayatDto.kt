package com.epy.linespotv2.data.remote.dto.riwayat

import com.epy.linespotv2.domain.model.riwayat.RiwayatPaymentFilter
import com.epy.linespotv2.domain.model.riwayat.RiwayatTransactionFilter
import com.epy.linespotv2.domain.model.riwayat.RiwayatVehicleFilter
import com.google.gson.annotations.SerializedName

data class RiwayatRequestDto(
    @SerializedName("user_id") val userId: Long = 0L,
    @SerializedName("username") val username: String = "",
    @SerializedName("role_id") val roleId: Long = 0L,
    @SerializedName("startDate") val startDate: String = "",
    @SerializedName("endDate") val endDate: String = "",
    @SerializedName("transaction") val transaction: RiwayatTransactionFilter = RiwayatTransactionFilter.ALL,
    @SerializedName("payment") val payment: RiwayatPaymentFilter = RiwayatPaymentFilter.ALL,
    @SerializedName("vehicle") val vehicle: RiwayatVehicleFilter = RiwayatVehicleFilter.ALL,
    // masih bingung implementasi lokasi ini
    @SerializedName("lokasi") val lokasi: String = "Semua Area"
)

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
