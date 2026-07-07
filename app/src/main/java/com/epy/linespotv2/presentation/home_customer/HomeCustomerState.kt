package com.epy.linespotv2.presentation.home_customer

import com.epy.linespotv2.domain.model.home.HomeResponseModel

data class HomeCustomerState (
    val isLoading: Boolean = false,
    val isRefresh: Boolean = false,
    val error: String? = null,
    val homeCustomerEffect: HomeCustomerEffect? = null,
    val homeResponseModel: HomeResponseModel? = null
)



