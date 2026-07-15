package com.epy.linespotv2.domain.repository.home

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.home.CustomerHomeModel
import kotlinx.coroutines.flow.Flow
interface CustomerHomeRepository {
    fun getCustomerHomePage() : Flow<ApiCondition<CustomerHomeModel>>
}