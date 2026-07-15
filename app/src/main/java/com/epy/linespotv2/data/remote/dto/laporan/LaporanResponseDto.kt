package com.epy.linespotv2.data.remote.dto.laporan

import com.google.gson.annotations.SerializedName

data class LaporanResponseDto(
    @SerializedName("tanggal_awal")    val tanggalAwal: String?,
    @SerializedName("tanggal_akhir")    val tanggalAkhir: String?,
    @SerializedName("total_transaksi")    val totalTransaksi: Long?,
    @SerializedName("total_pendapatan_jukir")    val totalPendapatanJukir: Long?,
    @SerializedName("pendapatan_per_tanggal")    val pendapatanPerTanggal: List<LaporanItem>?
)

data class LaporanItem(
    @SerializedName("tanggal")    val tanggal: String?,
    @SerializedName("total_transaksi")    val totalTransaksi: Long?,
    @SerializedName("total_pendapatan_jukir")    val totalPendapatanJukir: Long?,
    @SerializedName("motor_count")    val motorCount: Long?,
    @SerializedName("car_count")    val carCount: Long?,
    @SerializedName("qris_count")    val qrisCount: Long?,
    @SerializedName("cash_count")    val cashCount: Long?
)
