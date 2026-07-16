package com.epy.linespotv2.domain.model.parking

data class PostParkingReqModel(
    val plateNumber: String?,
    val vehicleTypeCode: String?,
    val selectedAreaId: Long?,
)
