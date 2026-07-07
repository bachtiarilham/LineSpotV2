package com.epy.linespotv2.domain.usecase.helper

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.helper.LokasiModel
import com.epy.linespotv2.domain.repository.helper.GetLokasiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLokasiUseCase @Inject constructor(
    private val repository : GetLokasiRepository
){
     suspend operator fun invoke(): Flow<ApiCondition<LokasiModel>> = repository.getLokasi()
}