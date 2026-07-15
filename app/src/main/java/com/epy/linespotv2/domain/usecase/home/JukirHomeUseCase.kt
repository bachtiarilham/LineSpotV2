package com.epy.linespotv2.domain.usecase.home

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.home.JukirHomeModel
import com.epy.linespotv2.domain.repository.home.JukirHomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class JukirHomeUseCase @Inject constructor(
    private val repository : JukirHomeRepository
){
    operator fun invoke(
    ): Flow<ApiCondition<JukirHomeModel>> = repository.getJukirHomePage()
}