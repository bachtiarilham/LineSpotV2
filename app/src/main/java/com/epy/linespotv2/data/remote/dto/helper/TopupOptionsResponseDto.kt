package com.epy.linespotv2.data.remote.dto.helper

import com.google.gson.annotations.SerializedName

data class TopupOptionsResponseDto(
    @SerializedName("nominal")
    val nominal: List<TopupOptionItemDto>? // Menggunakan List nullable (?) karena tipe aslinya pointer (*[])
)

data class TopupOptionItemDto(
    @SerializedName("optionId")
    val optionId: Long?,

    @SerializedName("nominalAmout") // Typo dari JSON dipertahankan di sini agar parsing tetap berhasil
    val nominalAmount: Long?, // Nama variabel di Kotlin bisa ditulis benar (Amount) agar rapi

    @SerializedName("label")
    val label: String?
)