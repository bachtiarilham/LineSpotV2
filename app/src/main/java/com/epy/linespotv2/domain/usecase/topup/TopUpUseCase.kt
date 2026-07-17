package com.epy.linespotv2.domain.usecase.topup

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.topup.TopupCreateRequestModel
import com.epy.linespotv2.domain.model.topup.TopupCreateResponseModel
import com.epy.linespotv2.domain.repository.topup.TopUpRepository
import javax.inject.Inject

class TopUpUseCase @Inject constructor(
    private val repository: TopUpRepository
) {
    suspend operator fun invoke(
        reqModel: TopupCreateRequestModel
    ): ApiCondition<TopupCreateResponseModel>? {
        return repository.topUpCreate(reqModel)
    }
}
