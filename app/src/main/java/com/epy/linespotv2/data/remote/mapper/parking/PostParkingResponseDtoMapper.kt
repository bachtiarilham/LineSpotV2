package com.epy.linespotv2.data.remote.mapper.parking

import com.epy.linespotv2.data.remote.dto.parking.PostParkingResponseDto
import com.epy.linespotv2.domain.model.parking.PostParkingRespModel

fun PostParkingResponseDto?.toDomain(): PostParkingRespModel = PostParkingRespModel(
    sessionId = this?.sessionId,
    sessionCode = this?.sessionCode,
    transactionCode = this?.transactionCode,
    plateNumber = this?.plateNumber,
    vehicleTypeCode = this?.vehicleTypeCode,
    vehicleTypeName = this?.vehicleTypeName,
    zoneId = this?.zoneId,
    zoneName = this?.zoneName,
    locationId = this?.locationId,
    locationName = this?.locationName,
    address = this?.address,
    areaId = this?.areaId,
    areaName = this?.areaName,
    amount = this?.amount,
    qrString = this?.qrString,
    qrExpiredAt = this?.qrExpiredAt,
    paymentCode = this?.paymentCode,
    paymentStatusCode = this?.paymentStatusCode,
    paymentStatusName = this?.paymentStatusName
)
