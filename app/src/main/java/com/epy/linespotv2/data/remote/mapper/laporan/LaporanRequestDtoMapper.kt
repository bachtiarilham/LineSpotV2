package com.epy.linespotv2.data.remote.mapper.laporan

import com.epy.linespotv2.data.remote.dto.laporan.LaporanRequestDto
import com.epy.linespotv2.domain.model.laporan.LaporanRequestModel

fun LaporanRequestModel.toDto() : LaporanRequestDto = LaporanRequestDto (
    userId = userId,
    username = username,
    roleId = roleId,
    startDate = startDate,
    endDate = endDate,
    lokasi = lokasi
)