package com.epy.linespotv2.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PembayaranDto(
    @SerializedName("title") val title: String? = null,
    @SerializedName("status_card") val statusCard: PembayaranStatusCardDto? = null,
    @SerializedName("total_pembayaran") val totalPembayaran: Long? = null,
    @SerializedName("detail_label") val detailLabel: String? = null,
    @SerializedName("qris_section") val qrisSection: PembayaranQrisSectionDto? = null,
    @SerializedName("payment_options_title") val paymentOptionsTitle: String? = null,
    @SerializedName("payment_options") val paymentOptions: List<PembayaranOptionDto>? = null,
    @SerializedName("print_button_label") val printButtonLabel: String? = null
)

data class PembayaranStatusCardDto(
    @SerializedName("title") val title: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("is_success") val isSuccess: Boolean? = null
)

data class PembayaranQrisSectionDto(
    @SerializedName("title") val title: String? = null,
    @SerializedName("qr_content") val qrContent: IsiQrDto? = null,
    @SerializedName("masaBerlakuQr") val information: String? = null,
    @SerializedName("countdown") val countdown: Long? = 0L,
    @SerializedName("alternative_label") val alternativeLabel: String? = null
)

data class IsiQrDto(
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

data class PembayaranOptionDto(
    @SerializedName("type") val type: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("subtitle") val subtitle: String? = null
)
