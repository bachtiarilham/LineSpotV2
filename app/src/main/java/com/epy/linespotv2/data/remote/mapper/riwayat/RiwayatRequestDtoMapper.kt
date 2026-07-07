package com.epy.linespotv2.data.remote.mapper.riwayat

import com.epy.linespotv2.data.remote.dto.riwayat.RiwayatRequestDto
import com.epy.linespotv2.domain.model.riwayat.RiwayatRequestModel

fun RiwayatRequestModel.toDto() : RiwayatRequestDto = RiwayatRequestDto (
    userId = userId,
    username = username,
    roleId = roleId,
    startDate = startDate,
    endDate = endDate,
    payment = payment,
    vehicle = vehicle,
    lokasi = lokasi
)