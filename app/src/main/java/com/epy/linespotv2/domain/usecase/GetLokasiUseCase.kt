package com.epy.linespotv2.domain.usecase

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.LokasiModel
import com.epy.linespotv2.domain.repository.GetLokasiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLokasiUseCase @Inject constructor(
    private val repository : GetLokasiRepository
){
     operator fun invoke(): Flow<ApiCondition<LokasiModel>> = repository.getLokasi()
}
