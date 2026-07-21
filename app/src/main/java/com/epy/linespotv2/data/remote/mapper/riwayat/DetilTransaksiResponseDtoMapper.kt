package com.epy.linespotv2.data.remote.mapper.riwayat

import com.epy.linespotv2.data.remote.dto.riwayat.DetilTransaksiResponseDto
import com.epy.linespotv2.domain.model.riwayat.DetilTransaksiResponseModel

fun DetilTransaksiResponseDto.toDomainModel(): DetilTransaksiResponseModel {
    return DetilTransaksiResponseModel(
        tanggal = tanggal,
        topUpTransactionId = topUpTransactionId,
        topUpCode = topUpCode,
        userId = userId,
        walletId = walletId,
        paymentMethodId = paymentMethodId,
        paymentMethodCode = paymentMethodCode,
        paymentMethodName = paymentMethodName,
        amount = amount,
        adminFee = adminFee,
        totalAmount = totalAmount,
        transactionStatus = transactionStatus,
        externalReference = externalReference,
        providerName = providerName,
        createdAt = createdAt,
        expiredAt = expiredAt,
        paidAt = paidAt,
        completedAt = completedAt,
        failedReason = failedReason
    )
}
