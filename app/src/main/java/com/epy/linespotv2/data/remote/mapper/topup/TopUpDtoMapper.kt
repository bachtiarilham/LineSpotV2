package com.epy.linespotv2.data.remote.mapper.topup

import com.epy.linespotv2.data.remote.dto.topup.TopupCreateRequestDto
import com.epy.linespotv2.data.remote.dto.topup.TopupCreateResponseDto
import com.epy.linespotv2.data.remote.dto.topup.TopupStatusResponseDto
import com.epy.linespotv2.domain.model.topup.TopupCreateRequestModel
import com.epy.linespotv2.domain.model.topup.TopupCreateResponseModel
import com.epy.linespotv2.domain.model.topup.TopupStatusResponseModel

// ==============================================================================
// 2. POST /topup/create
// ==============================================================================

fun TopupCreateRequestModel?.toDto(): TopupCreateRequestDto? {
    this ?: return null
    return TopupCreateRequestDto(
        amount = this.amount,
        paymentMethodCode = this.paymentMethodCode
    )
}

//fun TopupCreateRequestDto?.toModel(): TopupCreateRequestModel? {
//    this ?: return null
//    return TopupCreateRequestModel(
//        amount = this.amount,
//        paymentMethodCode = this.paymentMethodCode,
//    )
//}

fun TopupCreateResponseModel?.toDto(): TopupCreateResponseDto? {
    this ?: return null
    return TopupCreateResponseDto(
        topupTransactionId = this.topupTransactionId,
        topupCode = this.topupCode,
        amount = this.amount,
        adminFee = this.adminFee,
        totalAmount = this.totalAmount,
        paymentMethodCode = this.paymentMethodCode,
        paymentMethodName = this.paymentMethodName,
        paymentStatusCode = this.paymentStatusCode,
        paymentStatusName = this.paymentStatusName,
        qrString = this.qrString,
        expiredAt = this.expiredAt,
        createdAt = this.createdAt
    )
}

fun TopupCreateResponseDto?.toModel(): TopupCreateResponseModel? {
    this ?: return null
    return TopupCreateResponseModel(
        topupTransactionId = this.topupTransactionId,
        topupCode = this.topupCode,
        amount = this.amount,
        adminFee = this.adminFee,
        totalAmount = this.totalAmount,
        paymentMethodCode = this.paymentMethodCode,
        paymentMethodName = this.paymentMethodName,
        paymentStatusCode = this.paymentStatusCode,
        paymentStatusName = this.paymentStatusName,
        qrString = this.qrString,
        expiredAt = this.expiredAt,
        createdAt = this.createdAt
    )
}

// ==============================================================================
// 3. GET /topup/{topupCode}/status
// ==============================================================================

fun TopupStatusResponseModel?.toDto(): TopupStatusResponseDto? {
    this ?: return null
    return TopupStatusResponseDto(
        topupTransactionId = this.topupTransactionId,
        topupCode = this.topupCode,
        amount = this.amount,
        adminFee = this.adminFee,
        totalAmount = this.totalAmount,
        paymentMethodCode = this.paymentMethodCode,
        paymentStatusCode = this.paymentStatusCode,
        paymentMethodName = this.paymentMethodName,
        qrString = this.qrString,
        paidAt = this.paidAt,
        expiredAt = this.expiredAt,
        failedReason = this.failedReason,
        completedAt = this.completedAt,
        currentBalance = this.currentBalance
    )
}

fun TopupStatusResponseDto?.toModel(): TopupStatusResponseModel? {
    this ?: return null
    return TopupStatusResponseModel(
        topupTransactionId = this.topupTransactionId,
        topupCode = this.topupCode,
        amount = this.amount,
        adminFee = this.adminFee,
        totalAmount = this.totalAmount,
        paymentMethodCode = this.paymentMethodCode,
        paymentStatusCode = this.paymentStatusCode,
        paymentMethodName = this.paymentMethodName,
        qrString = this.qrString,
        paidAt = this.paidAt,
        expiredAt = this.expiredAt,
        failedReason = this.failedReason,
        completedAt = this.completedAt,
        currentBalance = this.currentBalance
    )
}