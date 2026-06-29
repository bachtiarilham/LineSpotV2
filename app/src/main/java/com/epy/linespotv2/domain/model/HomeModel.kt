package com.epy.linespotv2.domain.model

data class HomeModel(
    val profile: Profile,
    val summary: HomeSummaryInfo,
    val jukirSummary : JukirSummaryInfo,
    val events: List<HomeEventItem>,
    val news: List<HomeNewsItem>,
    val warnings: HomeWarnings
)

data class Profile(
    val id: Long,
    val name: String,
    val photoUrl: String? = null
)

data class HomeSummaryInfo(
    val saldo : Long,
    val expiredDate: String,
)

data class JukirSummaryInfo(
    val pendapatan: Long,
    val lokasi: String,
    val area: String,
    val zona: String,
)

data class HomeEventItem(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val imageUrl: String? = null,
    val tag: String = "EVENT"
)

data class HomeNewsItem(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val imageUrl: String? = null,
    val tag: String = "Info Parkir"
)

data class HomeWarnings(
    val profile: String?,
    val parking: String?,
    val finance: String?
)

