package com.epy.linespotv2.domain.model.home

data class HomeResponseModel(
    val profile: Profile = Profile(),
    val events: List<HomeEventItem> = emptyList(),
    val news: List<HomeNewsItem> = emptyList(),
    val warnings: HomeWarnings = HomeWarnings()
)

data class Profile(
    val id: Long = 0L,
    val name: String = "",
    val photoUrl: String? = null,
    val saldo : Long = 0L,
    val expiredDate: String = "",
    val pendapatan: Long = 0L,
    val lokasi: String = "",
    val area: String = "",
    val zona: String = "",
)

data class HomeEventItem(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val imageUrl: String? = null,
    val tag: String = "EVENT"
)

data class HomeNewsItem(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val imageUrl: String? = null,
    val tag: String = "Info Parkir"
)

data class HomeWarnings(
    val profile: String? = null,
    val parking: String? = null,
    val finance: String? = null
)

