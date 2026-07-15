package com.epy.linespotv2.domain.usecase.payment

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.parking.PostParkingRespModel
import com.epy.linespotv2.domain.repository.payment.GetPembayaranStatusRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPembayaranStatusUseCase @Inject constructor(
    private val repository : GetPembayaranStatusRepository,
){
    suspend operator fun invoke(sessionId : Long): Flow<ApiCondition<PostParkingRespModel>> = repository.getPembayaranStatus(sessionId)
}