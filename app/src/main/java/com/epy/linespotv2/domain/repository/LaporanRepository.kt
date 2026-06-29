package com.epy.linespotv2.domain.repository

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.LaporanModel

interface LaporanRepository {
    suspend fun getLaporanPage(
        userId: Long,
        username: String,
        roleId: Long,
        startDate: String,
        endDate: String,
        lokasi: String
    ): ApiCondition<LaporanModel>
}
