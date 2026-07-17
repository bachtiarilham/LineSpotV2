package com.epy.linespotv2.domain.repository.topup

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.topup.TopupCreateRequestModel
import com.epy.linespotv2.domain.model.topup.TopupCreateResponseModel
import com.epy.linespotv2.domain.model.topup.TopupStatusResponseModel

interface TopUpRepository {
    suspend fun topUpCreate(reqModel: TopupCreateRequestModel):  ApiCondition<TopupCreateResponseModel>?
    suspend fun topUpStatus(req: String): ApiCondition<TopupStatusResponseModel>
}