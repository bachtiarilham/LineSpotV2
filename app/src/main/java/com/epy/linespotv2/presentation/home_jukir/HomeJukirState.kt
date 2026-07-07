package com.epy.linespotv2.presentation.home_jukir

import com.epy.linespotv2.domain.model.home.HomeResponseModel

data class HomeJukirState (
    val isLoading: Boolean = false,
    val isRefresh: Boolean = false,
    val error: String? = null,
    val homeJukirEffect: HomeJukirEffect? = null,
    val homeResponseModel: HomeResponseModel? = null
)



