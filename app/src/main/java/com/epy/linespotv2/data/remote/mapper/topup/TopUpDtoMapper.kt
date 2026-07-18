package com.epy.linespotv2.data.remote.mapper.topup

import com.epy.linespotv2.data.remote.dto.topup.TopupCreateRequestDto
import com.epy.linespotv2.data.remote.dto.topup.TopupCreateResponseDto
import com.epy.linespotv2.data.remote.dto.topup.TopupStatusResponseDto
import com.epy.linespotv2.domain.model.topup.TopupCreateRequestModel
import com.epy.linespotv2.domain.model.topup.TopupCreateResponseModel
import com.epy.linespotv2.domain.model.topup.TopupStatusResponseModel

fun TopupCreateRequestModel.toDto(): TopupCreateRequestDto {
    return TopupCreateRequestDto(
        amount = amount,
        paymentMethodCode = paymentMethodCode
    )
}

fun TopupCreateResponseDto?.toModel(): TopupCreateResponseModel? {
    this ?: return null
    return TopupCreateResponseModel(
        topupTransactionId = topupTransactionId,
        topupCode = topupCode,
        amount = amount,
        adminFee = adminFee,
        totalAmount = totalAmount,
        paymentMethodCode = paymentMethodCode,
        paymentMethodName = paymentMethodName,
        paymentStatusCode = paymentStatusCode,
        paymentStatusName = paymentStatusName,
        qrString = qrString,
        expiredAt = expiredAt,
        createdAt = createdAt
    )
}

fun TopupStatusResponseDto?.toModel(): TopupStatusResponseModel? {
    this ?: return null
    return TopupStatusResponseModel(
        topupTransactionId = topupTransactionId,
        topupCode = topupCode,
        amount = amount,
        adminFee = adminFee,
        totalAmount = totalAmount,
        paymentMethodCode = paymentMethodCode,
        paymentStatusCode = paymentStatusCode,
        paymentMethodName = paymentMethodName,
        qrString = qrString,
        paidAt = paidAt,
        expiredAt = expiredAt,
        failedReason = failedReason,
        completedAt = completedAt,
        currentBalance = currentBalance
    )
}
