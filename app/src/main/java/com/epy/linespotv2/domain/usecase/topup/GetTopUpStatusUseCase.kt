package com.epy.linespotv2.domain.usecase.topup

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.topup.TopupStatusResponseModel
import com.epy.linespotv2.domain.repository.topup.TopUpRepository
import javax.inject.Inject

class GetTopUpStatusUseCase @Inject constructor(
    private val repository: TopUpRepository
) {
    suspend operator fun invoke(
        topupCode: String
    ): ApiCondition<TopupStatusResponseModel> {
        return repository.topUpStatus(topupCode)
    }
}
