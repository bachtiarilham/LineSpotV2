package com.epy.linespotv2.data.remote.dto.topup

import com.google.gson.annotations.SerializedName

data class TopupCreateRequestDto(
    @SerializedName("amount")
    val amount: Long?,

    @SerializedName("paymentMethodCode")
    val paymentMethodCode: String?
)