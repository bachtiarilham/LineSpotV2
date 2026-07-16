package com.epy.linespotv2.domain.repository.helper

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.helper.TarifModel
import kotlinx.coroutines.flow.Flow

interface GetTarifRepository {
    suspend fun getTarif(): Flow<ApiCondition<TarifModel>>
}