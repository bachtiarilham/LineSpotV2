package com.epy.linespotv2.data.remote.mapper.payment

import com.epy.linespotv2.data.remote.dto.payment.PostPaymentParkingResponseDto
import com.epy.linespotv2.domain.model.payment.PostPaymentParkingRespModel

fun PostPaymentParkingResponseDto?.toDomain(): PostPaymentParkingRespModel = PostPaymentParkingRespModel(
    sessionId = this?.sessionId,
    sessionCode = this?.sessionCode,
    transactionCode = this?.transactionCode,
    plateNumber = this?.plateNumber,
    vehicleTypeCode = this?.vehicleTypeCode,
    vehicleTypeName = this?.vehicleTypeName,
    locationId = this?.locationId,
    locationName = this?.locationName,
    areaId = this?.areaId,
    areaName = this?.areaName,
    amount = this?.amount,
    parkingStatusCode = this?.parkingStatusCode,
    parkingStatusName = this?.parkingStatusName,
    paymentStatusCode = this?.paymentStatusCode,
    paymentStatusName = this?.paymentStatusName,
    paymentCode = this?.paymentCode,
    failedReason = this?.failedReason,
    receiptNumber = this?.receiptNumber,
    startedAt = this?.startedAt,
    paidAt = this?.paidAt,
    qrExpiredAt = this?.qrExpiredAt
)
