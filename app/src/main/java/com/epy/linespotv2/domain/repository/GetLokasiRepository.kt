package com.epy.linespotv2.domain.repository

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.helper.LokasiModel
import kotlinx.coroutines.flow.Flow

interface GetLokasiRepository {
    fun getLokasi(): Flow<ApiCondition<LokasiModel>>
}
