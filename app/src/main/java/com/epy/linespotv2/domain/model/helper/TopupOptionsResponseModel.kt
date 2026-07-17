package com.epy.linespotv2.domain.model.helper

data class TopupOptionsResponseModel(
    val nominal: List<TopupOptionItemModel>?
)

data class TopupOptionItemModel(
    val optionId: Long?,
    val nominalAmount: Long?,
    val label: String?
)