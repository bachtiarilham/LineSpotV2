package com.epy.linespotv2.domain.repository

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.home.HomeModel
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getHome(): Flow<ApiCondition<HomeModel>>
}