package com.epy.linespotv2.domain.repository.subcription

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.subscription.SubscribeResponseModel
import kotlinx.coroutines.flow.Flow

interface SubscribeRepository {
    suspend fun getSubscriptionPage(): Flow<ApiCondition<SubscribeResponseModel>>
}