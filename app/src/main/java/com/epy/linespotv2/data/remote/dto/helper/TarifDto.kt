package com.epy.linespotv2.data.remote.dto.helper

import com.google.gson.annotations.SerializedName

data class TarifDto(
    @SerializedName("kendaraan") val kendaraan: String,
    @SerializedName("nominal") val nominal: String,
)