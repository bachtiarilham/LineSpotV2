package com.epy.linespotv2.data.remote.mapper.helper

import com.epy.linespotv2.data.remote.dto.helper.TopupOptionItemDto
import com.epy.linespotv2.data.remote.dto.helper.TopupOptionsResponseDto
import com.epy.linespotv2.domain.model.helper.TopupOptionItemModel
import com.epy.linespotv2.domain.model.helper.TopupOptionsResponseModel

// ==============================================================================
// Helper Mappers
// ==============================================================================

fun TopupOptionsResponseModel?.toDto(): TopupOptionsResponseDto? {
    this ?: return null

    // Jika nominal null, Kotlin otomatis memberikan emptyList() sesuai logika Go
    return TopupOptionsResponseDto(
        nominal = this.nominal?.map { it.toDto() } ?: emptyList()
    )
}

fun TopupOptionItemModel.toDto(): TopupOptionItemDto {
    return TopupOptionItemDto(
        optionId = this.optionId,
        nominalAmount = this.nominalAmount,
        label = this.label
    )
}

fun TopupOptionsResponseDto?.toModel(): TopupOptionsResponseModel? {
    this ?: return null

    // Jika nominal null, Kotlin otomatis memberikan emptyList() sesuai logika Go
    return TopupOptionsResponseModel(
        nominal = this.nominal?.map { it.toModel() } ?: emptyList()
    )
}

fun TopupOptionItemDto.toModel(): TopupOptionItemModel {
    return TopupOptionItemModel(
        optionId = this.optionId,
        nominalAmount = this.nominalAmount,
        label = this.label
    )
}