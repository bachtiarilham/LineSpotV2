package com.epy.linespotv2.data.remote.dto.payment

import com.google.gson.annotations.SerializedName

data class PostPaymentParkingResponseDto(
    @SerializedName("title") val title: String? = null,
    @SerializedName("success_title") val successTitle: String? = null,
    @SerializedName("success_description") val successDescription: String? = null,
    @SerializedName("total_amount") val totalAmount: String? = null,
    @SerializedName("payment_status") val paymentStatus: String? = null,
    @SerializedName("reference_number") val referenceNumber: String? = null,
    @SerializedName("verification_message") val verificationMessage: String? = null,
    @SerializedName("thank_you_title") val thankYouTitle: String? = null,
    @SerializedName("thank_you_description") val thankYouDescription: String? = null,
    @SerializedName("download_label") val downloadLabel: String? = null,
    @SerializedName("back_to_home_label") val backToHomeLabel: String? = null,
    @SerializedName("details") val details: List<PostPaymentParkingDetailItemDto>? = null
)

data class PostPaymentParkingDetailItemDto(
    @SerializedName("label") val label: String? = null,
    @SerializedName("value") val value: String? = null
)
