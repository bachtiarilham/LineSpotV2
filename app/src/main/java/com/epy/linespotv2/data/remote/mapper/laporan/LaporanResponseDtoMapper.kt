package com.epy.linespotv2.data.remote.mapper.laporan

import com.epy.linespotv2.data.remote.dto.laporan.LaporanItem
import com.epy.linespotv2.data.remote.dto.laporan.LaporanResponseDto
import com.epy.linespotv2.domain.model.laporan.LaporanItemModel
import com.epy.linespotv2.domain.model.laporan.LaporanResponseModel

fun LaporanResponseDto?.toDomain(): LaporanResponseModel = LaporanResponseModel(
    tanggalAwal = this?.tanggalAwal,
    tanggalAkhir = this?.tanggalAkhir,
    totalTransaksi = this?.totalTransaksi,
    totalPendapatanJukir = this?.totalPendapatanJukir,
    pendapatanPerTanggal = this?.pendapatanPerTanggal?.map { it.toDomain() }
)

private fun LaporanItem?.toDomain(): LaporanItemModel = LaporanItemModel(
    tanggal = this?.tanggal,
    totalTransaksi = this?.totalTransaksi,
    totalPendapatanJukir = this?.totalPendapatanJukir,
    motorCount = this?.motorCount,
    carCount = this?.carCount,
    qrisCount = this?.qrisCount,
    cashCount = this?.cashCount
)
