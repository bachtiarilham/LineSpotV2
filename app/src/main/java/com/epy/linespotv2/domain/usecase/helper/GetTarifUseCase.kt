package com.epy.linespotv2.domain.usecase.helper

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.helper.TarifModel
import com.epy.linespotv2.domain.repository.helper.GetTarifRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTarifUseCase @Inject constructor(
    private val repository : GetTarifRepository
){
    suspend operator fun invoke(): Flow<ApiCondition<TarifModel>> = repository.getTarif()
}