package com.epy.linespotv2.data.remote.dto.payment

import com.google.gson.annotations.SerializedName

data class PostPaymentParkingRequestDto(
    @SerializedName("session_code")
    val sessionCode: String?
)