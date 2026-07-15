package com.epy.linespotv2.data.remote.mapper.helper

import com.epy.linespotv2.data.remote.dto.helper.LokasiItemResponseDto
import com.epy.linespotv2.data.remote.dto.helper.LokasiResponseDto
import com.epy.linespotv2.domain.model.helper.LokasiItemModel
import com.epy.linespotv2.domain.model.helper.LokasiModel

fun LokasiResponseDto?.toDomain(): LokasiModel = LokasiModel(
    lokasi = this?.lokasi?.map { it.toDomain() }
)

private fun LokasiItemResponseDto?.toDomain(): LokasiItemModel = LokasiItemModel(
    lokasiId = this?.lokasiId,
    namaLokasi = this?.namaLokasi,
    areaId = this?.areaId,
    namaArea = this?.namaArea,
    zonaId = this?.zonaId,
    namaZona = this?.namaZona,
    address = this?.address
)
