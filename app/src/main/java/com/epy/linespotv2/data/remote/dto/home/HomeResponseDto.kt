package com.epy.linespotv2.data.remote.dto.home

import com.google.gson.annotations.SerializedName

data class HomeResponseDto(
    @SerializedName("profile")          val profile: ProfileDto? = null,
    @SerializedName("events")           val events: List<EventDto?>? = emptyList(),
    @SerializedName("news")             val news: List<NewsDto?>? = emptyList(),
    @SerializedName("warnings")         val warnings: WarningsDto? = null
)

data class ProfileDto(
    @SerializedName("id")   val id: Long? = 0L,
    @SerializedName("name") val name: String? = "",
    @SerializedName("photo_url") val photoUrl: String?,
    @SerializedName("saldo")             val saldo: Long? = 0L,
    @SerializedName("expiredDate") val expiredDate : String? = "",
    @SerializedName("pendapatan")             val pendapatan: Long? = 0L,
    @SerializedName("lokasi") val lokasi : String? = "",
    @SerializedName("area") val area : String? = "",
    @SerializedName("zona") val zona : String? = ""
)

data class EventDto(
    @SerializedName("id")          val id: Long? = 0L,
    @SerializedName("title")       val title: String? = "",
    @SerializedName("description") val description: String? = "",
    @SerializedName("date")        val date: String? = "",
    @SerializedName("imageurl")    val imageUrl: String? = null,
    @SerializedName("tag")         val tag: String = "EVENT"
)

data class NewsDto(
    @SerializedName("id")          val id: Long? = 0L,
    @SerializedName("title")       val title: String? = "",
    @SerializedName("description") val description: String? = "",
    @SerializedName("date")        val date: String? = "",
    @SerializedName("imageurl")    val imageUrl: String? = null,
    @SerializedName("tag")         val tag: String = "EVENT"
)

data class WarningsDto(
    @SerializedName("profile") val profile: String? = null,
    @SerializedName("parking") val parking: String? = null,
    @SerializedName("finance") val finance: String? = null
)

