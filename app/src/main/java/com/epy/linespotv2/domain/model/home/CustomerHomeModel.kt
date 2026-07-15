package com.epy.linespotv2.domain.model.home

import com.epy.linespotv2.domain.model.profile.CustomerModel
import com.epy.linespotv2.domain.model.profile.JukirModel

data class CustomerHomeModel (
    val customerModel: CustomerModel? = null,
    val contents: List<ContentsModel?>? = emptyList(),
)