package com.epy.linespotv2.domain.model.payment

data class PostParkingReqModel(
    val nomorPolisi : String,
    val jenisKendaraan : String,
    val waktuMasuk : String,
    val zonaParkir : String,
    val lokasiParkir : String,
)
