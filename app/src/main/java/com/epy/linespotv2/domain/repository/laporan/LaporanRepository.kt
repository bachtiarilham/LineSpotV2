package com.epy.linespotv2.domain.repository.laporan

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.laporan.LaporanRequestModel
import com.epy.linespotv2.domain.model.laporan.LaporanResponseModel

interface LaporanRepository {
    suspend fun getLaporanPage( reqModel : LaporanRequestModel): ApiCondition<LaporanResponseModel>
}