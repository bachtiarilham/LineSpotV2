package com.epy.linespotv2.data.remote.mapper.home

import com.epy.linespotv2.data.remote.dto.home.EventDto
import com.epy.linespotv2.data.remote.dto.home.HomeResponseDto
import com.epy.linespotv2.data.remote.dto.home.NewsDto
import com.epy.linespotv2.data.remote.dto.home.ProfileDto
import com.epy.linespotv2.data.remote.dto.home.WarningsDto
import com.epy.linespotv2.domain.model.home.HomeEventItem
import com.epy.linespotv2.domain.model.home.HomeNewsItem
import com.epy.linespotv2.domain.model.home.HomeResponseModel
import com.epy.linespotv2.domain.model.home.HomeWarnings
import com.epy.linespotv2.domain.model.home.Profile

fun HomeResponseDto.toDomain(): HomeResponseModel = HomeResponseModel(
    profile = profile.toDomain(),
    events = events.toEventDomain(),
    news = news.toNewsDomain(),
    warnings = warnings.toDomain()
)

private fun ProfileDto?.toDomain(): Profile = Profile(
    id = this?.id ?: 0L,
    name = this?.name?.takeIf { it.isNotBlank() } ?: "Customer",
    photoUrl = this?.photoUrl.orEmpty(),
    saldo = this?.saldo ?: 0L,
    expiredDate = this?.expiredDate?.takeIf { it.isNotBlank() } ?: "",
    pendapatan = this?.pendapatan ?: 0L,
    lokasi = this?.lokasi.orEmpty(),
    area = this?.area.orEmpty(),
    zona = this?.zona.orEmpty()
)

private fun List<EventDto?>?.toEventDomain(): List<HomeEventItem> {
    return this.orEmpty()
        .filterNotNull()
        .map { it.toDomain() }
}

private fun EventDto?.toDomain(): HomeEventItem = HomeEventItem(
    id = this?.id?.toString()?.takeIf { it.isNotBlank() } ?: "",
    title = this?.title.orEmpty(),
    description = this?.description.orEmpty(),
    date = this?.date?.takeIf { it.isNotBlank() } ?: "",
    imageUrl = this?.imageUrl.orEmpty(),
    tag = this?.tag ?: "EVENT"
)

private fun List<NewsDto?>?.toNewsDomain(): List<HomeNewsItem> {
    return this.orEmpty()
        .filterNotNull()
        .map { it.toDomain() }
}

private fun NewsDto?.toDomain(): HomeNewsItem = HomeNewsItem(
    id = this?.id?.toString()?.takeIf { it.isNotBlank() } ?: "",
    title = this?.title.orEmpty(),
    description = this?.description.orEmpty(),
    date = this?.date?.takeIf { it.isNotBlank() } ?: "",
    imageUrl = this?.imageUrl.orEmpty(),
    tag = this?.tag ?: "EVENT"
)

private fun WarningsDto?.toDomain(): HomeWarnings = HomeWarnings(
    profile = this?.profile?.takeIf { it.isNotBlank() },
    parking = this?.parking?.takeIf { it.isNotBlank() },
    finance = this?.finance?.takeIf { it.isNotBlank() }
)
