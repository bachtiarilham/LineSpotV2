package com.epy.linespotv2.data.remote.mapper.helper

import com.epy.linespotv2.data.remote.dto.helper.LokasiDto
import com.epy.linespotv2.domain.model.helper.LokasiModel
import kotlin.collections.orEmpty

fun LokasiDto?.toDomain(): LokasiModel = LokasiModel(
    nama_lokasi = this?.lokasi.orEmpty()
)