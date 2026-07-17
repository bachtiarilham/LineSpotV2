package com.epy.linespotv2.domain.usecase.helper

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.helper.TopupOptionsResponseModel
import com.epy.linespotv2.domain.repository.helper.NominalOptionRepository
import javax.inject.Inject

class NominalOptionUseCase @Inject constructor(
    private val repository : NominalOptionRepository
){
    suspend operator fun invoke(): ApiCondition<TopupOptionsResponseModel> = repository.getNominalOption()
}