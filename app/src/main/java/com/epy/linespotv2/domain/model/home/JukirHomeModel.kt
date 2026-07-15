package com.epy.linespotv2.domain.model.home

import com.epy.linespotv2.domain.model.profile.CustomerModel
import com.epy.linespotv2.domain.model.profile.JukirModel

data class JukirHomeModel (
    val jukirModel: JukirModel? = null,
    val contents: List<ContentsModel?>? = emptyList(),
)