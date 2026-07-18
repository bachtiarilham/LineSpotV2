package com.epy.linespotv2.data.remote.dto.helper

import com.google.gson.annotations.SerializedName

data class TopupResponseDto(
    @SerializedName("nominal")
    val nominal: List<NominalItemDto>?,
    @SerializedName(value = "metode_bayar")
    val metodePayment : List <MetodeItemDto>?
)

data class NominalItemDto(
    @SerializedName("optionId")
    val optionId: Long?,

    @SerializedName("nominalAmout")
    val nominalAmount: Long?,

    @SerializedName("label")
    val label: String?
)

data class MetodeItemDto (
    @SerializedName("method_id")
    val paymentMethodId: Long?,
    @SerializedName("nama_payment")
    val namaPayment: String?,
    @SerializedName("code_payment")
    val codePayment: String?,
    @SerializedName("logo_payment")
    val logoPayment: String?
)
