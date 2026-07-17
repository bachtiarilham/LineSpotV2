package com.epy.linespotv2.domain.repository.helper

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.helper.TopupOptionsResponseModel

interface NominalOptionRepository {
    suspend fun getNominalOption(): ApiCondition<TopupOptionsResponseModel>
}