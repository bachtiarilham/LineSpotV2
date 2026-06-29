package com.epy.linespotv2.data.remote.mapper

import com.epy.linespotv2.data.remote.dto.RiwayatDto
import com.epy.linespotv2.data.remote.dto.RiwayatItemDto
import com.epy.linespotv2.data.remote.dto.RiwayatSectionDto
import com.epy.linespotv2.domain.model.RiwayatItem
import com.epy.linespotv2.domain.model.RiwayatModel
import com.epy.linespotv2.domain.model.RiwayatSection

fun RiwayatDto?.toDomain(): RiwayatModel {
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

