package com.epy.linespotv2.presentation.subscribe

import com.epy.linespotv2.domain.model.subscription.SubscribeResponseModel

data class SubscribeState (
    val isLoading: Boolean = false,
    val isRefresh: Boolean = false,
    val error: String? = null,
    val subscribeEffect: SubscribeEffect? = null,
    val subscribeResponseModel: SubscribeResponseModel? = null,
    val selectedTabIndex: Int = 2
)
