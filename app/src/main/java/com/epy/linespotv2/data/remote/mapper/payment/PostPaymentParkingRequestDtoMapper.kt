package com.epy.linespotv2.data.remote.mapper.payment

import com.epy.linespotv2.data.remote.dto.payment.PostPaymentParkingRequestDto
import com.epy.linespotv2.domain.model.payment.PostPaymentParkingReqModel

fun PostPaymentParkingReqModel.toDto(): PostPaymentParkingRequestDto = PostPaymentParkingRequestDto(
    sessionCode = sessionCode
)
