package com.epy.linespotv2.domain.repository.payment

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.parking.PostParkingRespModel
import kotlinx.coroutines.flow.Flow

interface GetPembayaranStatusRepository {
    suspend fun getPembayaranStatus(sessionId: Long) : Flow<ApiCondition<PostParkingRespModel>>
}