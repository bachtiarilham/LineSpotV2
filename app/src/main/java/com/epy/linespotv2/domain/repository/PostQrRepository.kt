package com.epy.linespotv2.domain.repository

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.HasilBayarParkirModel

interface PostQrRepository {
    suspend fun submitQr(
        sessionId : Long,
        plat_nomor : String,
        lokasi : String,
        waktu_masuk : String,
        durasi : String,
        nominal : Long,
        isPaid : Boolean,
        paymentStatus : Long,
        isExpired : Boolean,
        statusMessage : String
    ): ApiCondition<HasilBayarParkirModel>
}