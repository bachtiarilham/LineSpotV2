package com.epy.linespotv2.data.remote.mapper.riwayat

import com.epy.linespotv2.data.remote.dto.riwayat.DetilParkirResponseDto
import com.epy.linespotv2.domain.model.riwayat.DetilParkirResponseModel

fun DetilParkirResponseDto.toDomainModel(): DetilParkirResponseModel {
    return DetilParkirResponseModel(
        tanggal = tanggal,
        transactionId = transactionId,
        transactionCode = transactionCode,
        sessionId = sessionId,
        plateNumber = plateNumber,
        vehicleTypeId = vehicleTypeId,
        vehicleTypeCode = vehicleTypeCode,
        vehicleTypeName = vehicleTypeName,
        paymentMethodId = paymentMethodId,
        paymentMethodCode = paymentMethodCode,
        paymentMethodName = paymentMethodName,
        locationId = locationId,
        locationName = locationName,
        locationAddress = locationAddress,
        areaId = areaId,
        areaName = areaName,
        zoneId = zoneId,
        zoneName = zoneName,
        baseAmount = baseAmount,
        discountAmount = discountAmount,
        finalAmount = finalAmount,
        companyShare = companyShare,
        jukirShare = jukirShare,
        taxAmount = taxAmount,
        feeAmount = feeAmount,
        transactionStatus = transactionStatus,
        operationType = operationType,
        occurredAt = occurredAt,
        paidAt = paidAt,
        createdAt = createdAt
    )
}
