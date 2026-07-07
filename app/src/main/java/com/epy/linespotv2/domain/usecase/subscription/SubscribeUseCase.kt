package com.epy.linespotv2.domain.usecase.subscription

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.subscription.SubscribeResponseModel
import com.epy.linespotv2.domain.repository.subcription.SubscribeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubscribeUseCase @Inject constructor(
    private val repository : SubscribeRepository
){
    suspend operator fun invoke(): Flow<ApiCondition<SubscribeResponseModel>> = repository.getSubscriptionPage()
}