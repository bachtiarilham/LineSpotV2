package com.epy.linespotv2.data.remote.dto

import com.epy.linespotv2.domain.model.JukirSummaryInfo
import com.google.gson.annotations.SerializedName
import java.sql.Time

data class HomeDto(
    @SerializedName("profile")          val profile: ProfileDto?,
    @SerializedName("summary")          val summary: SummaryDto?,
    @SerializedName("jukir_summary")    val jukirSummary: JukirSummaryDto?,
    @SerializedName("events")           val events: List<EventDto>?,
    @SerializedName("news")             val news: List<NewsDto>?,
    @SerializedName("warnings")         val warnings: WarningsDto?
)

data class ProfileDto(
    @SerializedName("id")   val id: Long?,
    @SerializedName("name") val name: String?
)

data class SummaryDto(
    @SerializedName("saldo")             val saldo: Long?,
    @SerializedName("expiredDate") val expiredDate : String?
)

data class JukirSummaryDto(
    @SerializedName("pendapatan")             val pendapatan: Long?,
    @SerializedName("lokasi") val lokasi : String?,
    @SerializedName("area") val area : String?,
    @SerializedName("zona") val zona : String?
)

data class EventDto(
    @SerializedName("id")          val id: Long?,
    @SerializedName("title")       val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("date")        val date: String?,
    @SerializedName("imageurl")    val imageUrl: String? = null,
    @SerializedName("tag")         val tag: String = "EVENT"
)

data class NewsDto(
    @SerializedName("id")          val id: Long?,
    @SerializedName("title")       val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("date")        val date: String?,
    @SerializedName("imageurl")    val imageUrl: String? = null,
    @SerializedName("tag")         val tag: String = "EVENT"
)

data class WarningsDto(
    @SerializedName("profile") val profile: String?,
    @SerializedName("parking") val parking: String?,
    @SerializedName("finance") val finance: String?
)

