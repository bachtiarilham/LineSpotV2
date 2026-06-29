package com.epy.linespotv2.domain.usecase

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.PembayaranModel
import com.epy.linespotv2.domain.repository.PembayaranStatusRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPembayaranStatusUseCase @Inject constructor(
    private val repository : PembayaranStatusRepository,
){
    suspend operator fun invoke(sessionId : Long): Flow<ApiCondition<PembayaranModel>> = repository.getPembayaranStatus(sessionId)
}