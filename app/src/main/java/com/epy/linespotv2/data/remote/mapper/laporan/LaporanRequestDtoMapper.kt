package com.epy.linespotv2.data.remote.mapper.laporan

import com.epy.linespotv2.data.remote.dto.laporan.LaporanRequestDto
import com.epy.linespotv2.core.utils.toApiDateOrSelf
import com.epy.linespotv2.domain.model.laporan.LaporanRequestModel

fun LaporanRequestModel.toDto(): LaporanRequestDto = LaporanRequestDto(
    startDate = startDate.toApiDateOrSelf(),
    endDate = endDate.toApiDateOrSelf()
)
