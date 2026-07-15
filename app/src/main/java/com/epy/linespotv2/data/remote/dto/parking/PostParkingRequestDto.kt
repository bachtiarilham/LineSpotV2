package com.epy.linespotv2.data.remote.dto.parking

import com.google.gson.annotations.SerializedName

data class PostParkingRequestDto(
    @SerializedName("nomor_polisi")
    val plateNumber: String?,
    @SerializedName("jenis_kendaraan")
    val vehicleTypeCode: String?,
    @SerializedName("area_parkir")
    val selectedAreaId: Long?
)
