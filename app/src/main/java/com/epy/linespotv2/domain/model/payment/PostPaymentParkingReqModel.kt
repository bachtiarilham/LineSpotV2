package com.epy.linespotv2.domain.model.payment

data class PostPaymentParkingReqModel (
    val sessionID : Long,
    val platNomor : String,
    val lokasi : String,
    val waktuMasuk : String,
    val durasi : String,
    val nominal : Long,
    val isPaid : Boolean,
    val paymentStatus : Long,
    val isExpired : Boolean,
    val statusMessage : String,
)