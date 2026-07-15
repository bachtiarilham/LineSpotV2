package com.epy.linespotv2.data.remote.mapper.home

import com.epy.linespotv2.data.remote.dto.home.ContentsDto
import com.epy.linespotv2.data.remote.dto.home.CustomerHomeDto
import com.epy.linespotv2.data.remote.dto.home.JukirHomeDto
import com.epy.linespotv2.data.remote.mapper.profile.toDomain
import com.epy.linespotv2.domain.model.home.ContentsModel
import com.epy.linespotv2.domain.model.home.CustomerHomeModel
import com.epy.linespotv2.domain.model.home.JukirHomeModel

fun CustomerHomeDto.toCustomerDomain(): CustomerHomeModel = CustomerHomeModel(
    customerModel = customerDto.toDomain(),
    contents = contents.map { it.toDomain() },
)

fun JukirHomeDto.toJukirDomain(): JukirHomeModel = JukirHomeModel(
    jukirModel = jukirDto.toDomain(),
    contents = contents?.map { it.toDomain() },
)

private fun ContentsDto?.toDomain(): ContentsModel = ContentsModel(
    contentId = this?.contentId,
    contentTypeId = this?.contentTypeId,
    contentTypeCode = this?.contentTypeCode,
    contentTypeName = this?.contentTypeName,
    title = this?.title,
    summary = this?.summary,
    body = this?.body,
    thumbnailUrl = this?.thumbnailUrl,
    bannerUrl = this?.bannerUrl,
    eventLocation = this?.eventLocation,
    eventStartAt = this?.eventStartAt,
    eventEndAt = this?.eventEndAt,
    publishAt = this?.publishAt,
    expiredAt = this?.expiredAt,
    priority = this?.priority
)