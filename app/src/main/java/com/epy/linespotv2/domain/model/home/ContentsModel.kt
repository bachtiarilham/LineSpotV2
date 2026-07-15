package com.epy.linespotv2.domain.model.home

data class ContentsModel(
    val contentId: Long? = null,
    val contentTypeId: Long? = null,
    val contentTypeCode: String? = null,
    val contentTypeName: String? = null,
    val title: String? = null,
    val summary: String? = null,
    val body: String? = null,
    val thumbnailUrl: String? = null,
    val bannerUrl: String? = null,
    val eventLocation: String? = null,
    val eventStartAt: String? = null,
    val eventEndAt: String? = null,
    val publishAt: String? = null,
    val expiredAt: String? = null,
    val priority: Long? = null
)