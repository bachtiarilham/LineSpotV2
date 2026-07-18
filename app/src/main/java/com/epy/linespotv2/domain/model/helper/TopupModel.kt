package com.epy.linespotv2.domain.model.helper

import com.epy.linespotv2.data.remote.dto.helper.MetodeItemDto

data class TopupModel(
    val nominal: List<NominalItemModel>?,
    val metodePayment : List <MetodeItemModel>?

)

data class NominalItemModel(
    val optionId: Long?,
    val nominalAmount: Long?,
    val label: String?
)

data class MetodeItemModel (
    val paymentMethodId: Long?,
    val namaPayment: String?,
    val codePayment: String?,
    val logoPayment: String?
)