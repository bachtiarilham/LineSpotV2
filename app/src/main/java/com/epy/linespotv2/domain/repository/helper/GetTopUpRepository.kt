package com.epy.linespotv2.domain.repository.helper

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.helper.TopupModel

interface GetTopUpRepository {
    suspend fun getTopUp(): ApiCondition<TopupModel>
}