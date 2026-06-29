package com.epy.linespotv2.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SubmitQrRequestDto(
    @SerializedName("session_id") val sessionId : Long = 0L,
    @SerializedName("plat_nomor") val plat_nomor : String = "",
    @SerializedName("lokasi") val lokasi : String = "",
    @SerializedName("waktu_masuk") val waktu_masuk : String = "",
    @SerializedName("durasi") val durasi : String = "",
    @SerializedName("nominal") val nominal : Long = 0L,
    @SerializedName("isPaid") val isPaid : Boolean = false,
    @SerializedName("paymentStatus") val paymentStatus : Long = 0L,
    @SerializedName("isExpired") val isExpired : Boolean = false,
    @SerializedName("statusMessage") val statusMessage : String = ""
)