package com.epy.linespotv2.data.remote.dto.home

import com.google.gson.annotations.SerializedName

data class ContentsDto(
    @SerializedName("content_id")    val contentId: Long?,
    @SerializedName("content_type_id")    val contentTypeId: Long?,
    @SerializedName("content_type_code")    val contentTypeCode: String?,
    @SerializedName("content_type_name")    val contentTypeName: String?,
    @SerializedName("title")    val title: String?,
    @SerializedName("summary")    val summary: String?,
    @SerializedName("body")    val body: String?,
    @SerializedName("thumbnail_url")    val thumbnailUrl: String?,
    @SerializedName("banner_url")    val bannerUrl: String?,
    @SerializedName("event_location")    val eventLocation: String?,
    @SerializedName("event_startat")    val eventStartAt: String?,
    @SerializedName("event_endat")    val eventEndAt: String?,
    @SerializedName("publishat")    val publishAt: String?,
    @SerializedName("expiredat")    val expiredAt: String?,
    @SerializedName("priority")    val priority: Long?
)
