package com.epy.linespotv2.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LokasiDto(
    @SerializedName("lokasi")    val lokasi: List<String>? = null
)