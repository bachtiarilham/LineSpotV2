package com.epy.linespotv2.domain.usecase

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.SubscribeModel
import com.epy.linespotv2.domain.repository.SubscribeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubscribeUseCase @Inject constructor(
    private val repository : SubscribeRepository
){
    operator fun invoke(): Flow<ApiCondition<SubscribeModel>> = repository.getPage()
}