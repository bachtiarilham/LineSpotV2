package com.epy.linespotv2.domain.repository.home

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.home.HomeResponseModel
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getHomePage(): Flow<ApiCondition<HomeResponseModel>>
}