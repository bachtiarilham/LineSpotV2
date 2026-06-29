package com.epy.linespotv2.data.remote.mapper

import com.epy.linespotv2.data.remote.dto.LokasiDto
import com.epy.linespotv2.domain.model.LokasiModel
import kotlin.collections.orEmpty

fun LokasiDto?.toDomain(): LokasiModel = LokasiModel(
    nama_lokasi = this?.lokasi.orEmpty()
)