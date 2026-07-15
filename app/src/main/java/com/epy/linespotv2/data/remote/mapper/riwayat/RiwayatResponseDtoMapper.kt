package com.epy.linespotv2.data.remote.mapper.riwayat

import com.epy.linespotv2.data.remote.dto.riwayat.RiwayatItemDto
import com.epy.linespotv2.data.remote.dto.riwayat.RiwayatResponseDto
import com.epy.linespotv2.data.remote.dto.riwayat.RiwayatSectionDto
import com.epy.linespotv2.domain.model.riwayat.RiwayatItem
import com.epy.linespotv2.domain.model.riwayat.RiwayatResponseModel
import com.epy.linespotv2.domain.model.riwayat.RiwayatSection

fun RiwayatResponseDto?.toDomain(): RiwayatResponseModel = RiwayatResponseModel(
    sections = this?.sections?.map { it.toDomain() }
)

private fun RiwayatSectionDto?.toDomain(): RiwayatSection = RiwayatSection(
    date = this?.date,
    items = this?.items?.map { it.toDomain() }
)

private fun RiwayatItemDto?.toDomain(): RiwayatItem = RiwayatItem(
    code = this?.code,
    plateNumber = this?.plateNumber,
    vehicleType = this?.vehicleType,
    time = this?.time,
    amount = this?.amount,
    isEntry = this?.isEntry
)
