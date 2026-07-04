package com.epy.linespotv2.data.remote.dto.payment

import com.google.gson.annotations.SerializedName

data class PostParkingRequestDto(
    @SerializedName("nomor_polisi") val nomorPolisi: String,
    @SerializedName("jenis_kendaraan") val jenisKendaraan: String,
    @SerializedName("waktu_masuk") val waktuMasuk: String,
    @SerializedName("zona_parkir") val zonaParkir: String,
    @SerializedName("lokasi_parkir") val lokasiParkir: String
)
