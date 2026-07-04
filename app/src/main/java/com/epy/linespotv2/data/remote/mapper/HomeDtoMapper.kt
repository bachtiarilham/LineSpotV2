package com.epy.linespotv2.data.remote.mapper

import com.epy.linespotv2.data.remote.dto.home.EventDto
import com.epy.linespotv2.data.remote.dto.home.HomeResponseDto
import com.epy.linespotv2.data.remote.dto.home.JukirSummaryDto
import com.epy.linespotv2.data.remote.dto.home.NewsDto
import com.epy.linespotv2.data.remote.dto.home.ProfileDto
import com.epy.linespotv2.data.remote.dto.home.SummaryDto
import com.epy.linespotv2.data.remote.dto.home.WarningsDto
import com.epy.linespotv2.domain.model.home.HomeEventItem
import com.epy.linespotv2.domain.model.home.HomeModel
import com.epy.linespotv2.domain.model.home.HomeNewsItem
import com.epy.linespotv2.domain.model.home.Profile
import com.epy.linespotv2.domain.model.home.HomeSummaryInfo
import com.epy.linespotv2.domain.model.home.HomeWarnings
import com.epy.linespotv2.domain.model.home.JukirSummaryInfo

fun HomeResponseDto.toDomain(): HomeModel = HomeModel(
    profile = profile.toDomain(),
    summary = summary.toDomain(),
    jukirSummary = jukirSummary.toDomain(),
    events = events?.map { it.toDomain() }.orEmpty(),
    news = news?.map { it.toDomain() }.orEmpty(),
    warnings = warnings.toDomain()
)

private fun ProfileDto?.toDomain(): Profile = Profile(
    id = this?.id ?: 0L,
    name = this?.name?.takeIf { it.isNotBlank() } ?: "Customer"
)

private fun SummaryDto?.toDomain(): HomeSummaryInfo = HomeSummaryInfo(
    saldo       = this?.saldo           ?: 0L,
    expiredDate = this?.expiredDate?.takeIf { it.isNotBlank() } ?: ""

//            expiredDate = this.expiredDate.takeIf { !it.isNullOrBlank() } ?: ""
)

private fun JukirSummaryDto?.toDomain() : JukirSummaryInfo = JukirSummaryInfo(
    pendapatan = this?.pendapatan ?: 0L,
    lokasi = this?.lokasi ?: "",
    area = this?.area ?: "",
    zona = this?.zona ?: ""
)

private fun EventDto.toDomain(): HomeEventItem = HomeEventItem(
//    id          = id.orEmpty(),
    id = this.id?.toString()?.takeIf { it.isNotBlank() } ?: "",
    title       = title.orEmpty(),
    description = description.orEmpty(),
    date = this.date?.takeIf { it.isNotBlank() } ?: "",
//    date        = date.orEmpty(),
    imageUrl    = imageUrl.orEmpty(),
    tag         = tag
)

private fun NewsDto.toDomain(): HomeNewsItem = HomeNewsItem(
//    id          = id.orEmpty(),
    id = this.id?.toString()?.takeIf { it.isNotBlank() } ?: "",
    title       = title.orEmpty(),
    description = description.orEmpty(),
    date        = date.orEmpty(),
    imageUrl    = imageUrl.orEmpty(),
    tag         = tag
)

private fun WarningsDto?.toDomain(): HomeWarnings = HomeWarnings(
    profile = this?.profile?.takeIf { it.isNotBlank() },
    parking = this?.parking?.takeIf { it.isNotBlank() },
    finance = this?.finance?.takeIf { it.isNotBlank() }
)
