package com.epy.linespotv2.data.remote.mapper.helper

import com.epy.linespotv2.data.remote.dto.helper.TarifResponseDto
import com.epy.linespotv2.data.remote.dto.helper.TarifResponseItemDto
import com.epy.linespotv2.domain.model.helper.TarifItemModel
import com.epy.linespotv2.domain.model.helper.TarifModel

fun TarifResponseDto?.toDomain(): TarifModel = TarifModel(
    tarifResponseItemDto = this?.tarifResponseItemDto.orEmpty().map { it.toDomain() }
)

private fun TarifResponseItemDto?.toDomain(): TarifItemModel = TarifItemModel(
    kendaraanId = this?.kendaraanId ?: 0L,
    kendaraanKode = this?.kendaraanKode.orEmpty(),
    kendaraanNama = this?.kendaraanNama.orEmpty(),
    nominal = this?.nominal ?: 0L,
)
