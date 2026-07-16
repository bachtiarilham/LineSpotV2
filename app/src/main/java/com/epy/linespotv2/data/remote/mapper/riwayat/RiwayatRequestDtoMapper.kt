package com.epy.linespotv2.data.remote.mapper.riwayat

import com.epy.linespotv2.core.utils.toApiDateOrSelf
import com.epy.linespotv2.data.remote.dto.riwayat.RiwayatRequestDto
import com.epy.linespotv2.domain.model.riwayat.RiwayatRequestModel

fun RiwayatRequestModel.toDto(): RiwayatRequestDto = RiwayatRequestDto(
    startDate = startDate?.toApiDateOrSelf(),
    endDate = endDate?.toApiDateOrSelf(),
    paymentCode = paymentCode,
    vehicleCode = vehicleCode,
    lokasiCode = lokasiCode
)
