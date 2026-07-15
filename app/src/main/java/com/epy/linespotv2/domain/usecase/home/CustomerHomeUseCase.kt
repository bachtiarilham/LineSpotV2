package com.epy.linespotv2.domain.usecase.home

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.home.CustomerHomeModel
import com.epy.linespotv2.domain.repository.home.CustomerHomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CustomerHomeUseCase @Inject constructor(
    private val repository : CustomerHomeRepository
){
    operator fun invoke(
    ): Flow<ApiCondition<CustomerHomeModel>> = repository.getCustomerHomePage()
}