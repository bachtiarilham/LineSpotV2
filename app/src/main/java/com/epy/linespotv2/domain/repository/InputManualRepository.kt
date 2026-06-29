package com.epy.linespotv2.domain.repository

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.PembayaranModel

interface InputManualRepository {
    suspend fun postParking(
        nomorPolisi: String,
        jenisKendaraan: String,
        waktuMasuk: String,
        zonaParkir: String,
        lokasiParkir: String
    ): ApiCondition<PembayaranModel>
}
