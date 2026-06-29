package com.epy.linespotv2.presentation.home_customer

import com.epy.linespotv2.domain.model.HomeModel

data class HomeState (
    val isLoading: Boolean = false,
    val isRefresh: Boolean = false,
    val error: String? = null,
    val homeEffect: HomeEffect? = null,
    val homeModel: HomeModel? = null
)



