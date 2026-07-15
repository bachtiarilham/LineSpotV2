package com.epy.linespotv2.domain.repository.home

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.home.JukirHomeModel
import kotlinx.coroutines.flow.Flow

interface JukirHomeRepository {
    fun getJukirHomePage() : Flow<ApiCondition<JukirHomeModel>>
}