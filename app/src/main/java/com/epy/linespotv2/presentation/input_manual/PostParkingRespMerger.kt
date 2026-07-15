package com.epy.linespotv2.presentation.input_manual

import com.epy.linespotv2.domain.model.parking.PostParkingRespModel

internal fun PostParkingRespModel.mergeWithDefaults(
    current: PostParkingRespModel?
): PostParkingRespModel {
    return copy(
        sessionId = sessionId ?: current?.sessionId,
        sessionCode = sessionCode ?: current?.sessionCode,
        transactionCode = transactionCode ?: current?.transactionCode,
        plateNumber = plateNumber ?: current?.plateNumber,
        vehicleTypeCode = vehicleTypeCode ?: current?.vehicleTypeCode,
        vehicleTypeName = vehicleTypeName ?: current?.vehicleTypeName,
        zoneId = zoneId ?: current?.zoneId,
        zoneName = zoneName ?: current?.zoneName,
        locationId = locationId ?: current?.locationId,
        locationName = locationName ?: current?.locationName,
        address = address ?: current?.address,
        areaId = areaId ?: current?.areaId,
        areaName = areaName ?: current?.areaName,
        amount = amount ?: current?.amount,
        qrString = qrString ?: current?.qrString,
        qrExpiredAt = qrExpiredAt ?: current?.qrExpiredAt,
        paymentCode = paymentCode ?: current?.paymentCode,
        paymentStatusCode = paymentStatusCode ?: current?.paymentStatusCode,
        paymentStatusName = paymentStatusName ?: current?.paymentStatusName
    )
}

internal fun PostParkingRespModel.mergeWithStatus(
    current: PostParkingRespModel?
): PostParkingRespModel {
    return mergeWithDefaults(current)
}
