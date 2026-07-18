package com.epy.linespotv2.domain.usecase.helper

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.helper.TopupModel
import com.epy.linespotv2.domain.repository.helper.GetTopUpRepository
import javax.inject.Inject

class NominalOptionUseCase @Inject constructor(
    private val repository : GetTopUpRepository
){
    suspend operator fun invoke(): ApiCondition<TopupModel> = repository.getTopUp()
}