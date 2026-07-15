package com.epy.linespotv2.data.remote.mapper.parking

import com.epy.linespotv2.data.remote.dto.parking.PostParkingRequestDto
import com.epy.linespotv2.domain.model.parking.PostParkingReqModel

fun PostParkingReqModel.toDto(): PostParkingRequestDto = PostParkingRequestDto(
    plateNumber = plateNumber,
    vehicleTypeCode = vehicleTypeCode,
    selectedAreaId = selectedAreaId
)
