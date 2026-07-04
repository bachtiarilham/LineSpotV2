package com.epy.linespotv2.data.remote.mapper

import com.epy.linespotv2.data.remote.dto.riwayat.RiwayatResponseDto
import com.epy.linespotv2.data.remote.dto.riwayat.RiwayatItemDto
import com.epy.linespotv2.data.remote.dto.riwayat.RiwayatSectionDto
import com.epy.linespotv2.domain.model.riwayat.RiwayatItem
import com.epy.linespotv2.domain.model.riwayat.RiwayatModel
import com.epy.linespotv2.domain.model.riwayat.RiwayatSection

fun RiwayatResponseDto?.toDomain(): RiwayatModel {
    return RiwayatModel(
        sections = this?.sections?.map { it.toDomain() }.orEmpty()
    )
}

private fun RiwayatSectionDto?.toDomain(): RiwayatSection {
    return RiwayatSection(
        date = this?.date.orEmpty(),
        items = this?.items?.map { it.toDomain() }.orEmpty()
    )
}

private fun RiwayatItemDto?.toDomain(): RiwayatItem {
    return RiwayatItem(
        code = this?.code.orEmpty(),
        plateNumber = this?.plateNumber.orEmpty(),
        vehicleType = this?.vehicleType.orEmpty(),
        time = this?.time.orEmpty(),
        amount = this?.amount ?: 0L,
        isEntry = this?.isEntry ?: false
    )
}

