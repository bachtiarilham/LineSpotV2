package com.epy.linespotv2.presentation.home_customer

import com.epy.linespotv2.domain.model.home.CustomerHomeModel
import com.epy.linespotv2.presentation.home_customer.ui_model.HomeCustomerUiModel

data class HomeCustomerState(
    val isLoading: Boolean = false,
    val isRefresh: Boolean = false,
    val error: String? = null,
    val customerHomeEffect: HomeCustomerEffect? = null,
    val customerHomeModel: CustomerHomeModel? = null,
    val uiModel: HomeCustomerUiModel? = null,
    val showFeatureUnavailableDialog: Boolean = false,
    val unavailableFeatureName: String = ""
)
