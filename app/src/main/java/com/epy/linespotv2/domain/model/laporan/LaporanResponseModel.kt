package com.epy.linespotv2.domain.model.laporan

data class LaporanResponseModel(
    val tanggalAwal: String? = null,
    val tanggalAkhir: String? = null,
    val totalTransaksi: Long? = null,
    val totalPendapatanJukir: Long? = null,
    val pendapatanPerTanggal: List<LaporanItemModel>? = null
)

data class LaporanItemModel(
    val tanggal: String? = null,
    val totalTransaksi: Long? = null,
    val totalPendapatanJukir: Long? = null,
    val motorCount: Long? = null,
    val carCount: Long? = null,
    val qrisCount: Long? = null,
    val cashCount: Long? = null
)
