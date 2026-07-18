package com.epy.linespotv2.data.remote.mapper.helper

import com.epy.linespotv2.data.remote.dto.helper.MetodeItemDto
import com.epy.linespotv2.data.remote.dto.helper.NominalItemDto
import com.epy.linespotv2.data.remote.dto.helper.TopupResponseDto
import com.epy.linespotv2.domain.model.helper.MetodeItemModel
import com.epy.linespotv2.domain.model.helper.NominalItemModel
import com.epy.linespotv2.domain.model.helper.TopupModel

fun TopupModel?.toDto(): TopupResponseDto? {
    this ?: return null

    // Jika nominal null, Kotlin otomatis memberikan emptyList() sesuai logika Go
    return TopupResponseDto(
        nominal = this.nominal?.map { it.toDto() } ?: emptyList(),
        metodePayment = this.metodePayment?.map { it.toDto() } ?: emptyList(),
    )
}

fun NominalItemModel.toDto(): NominalItemDto {
    return NominalItemDto(
        optionId = this.optionId,
        nominalAmount = this.nominalAmount,
        label = this.label
    )
}

fun MetodeItemModel.toDto(): MetodeItemDto {
    return MetodeItemDto(
        paymentMethodId = this.paymentMethodId,
        namaPayment = this.namaPayment,
        codePayment = this.codePayment,
        logoPayment = this.logoPayment
    )
}

fun TopupResponseDto?.toModel(): TopupModel? {
    this ?: return null

    // Jika nominal null, Kotlin otomatis memberikan emptyList() sesuai logika Go
    return TopupModel(
        nominal = this.nominal?.map { it.toModel() } ?: emptyList(),
        metodePayment = this.metodePayment?.map { it.toModel() } ?: emptyList()

    )
}

fun NominalItemDto.toModel(): NominalItemModel {
    return NominalItemModel(
        optionId = this.optionId,
        nominalAmount = this.nominalAmount,
        label = this.label
    )
}

fun MetodeItemDto.toModel(): MetodeItemModel {
    return MetodeItemModel(
        paymentMethodId = this.paymentMethodId,
        namaPayment = this.namaPayment,
        codePayment = this.codePayment,
        logoPayment = this.logoPayment
    )
}