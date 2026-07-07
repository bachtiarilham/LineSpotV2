package com.epy.linespotv2.data.remote.mapper.payment

import com.epy.linespotv2.data.remote.dto.payment.PostPaymentParkingRequestDto
import com.epy.linespotv2.domain.model.payment.PostPaymentParkingReqModel

fun PostPaymentParkingReqModel.toDto() : PostPaymentParkingRequestDto = PostPaymentParkingRequestDto (
    sessionId = sessionID,
    plat_nomor = platNomor,
    lokasi = lokasi,
    waktu_masuk = waktuMasuk,
    durasi = durasi,
    nominal = nominal,
    isPaid = isPaid,
    paymentStatus = paymentStatus,
    isExpired = isExpired,
    statusMessage = statusMessage
)