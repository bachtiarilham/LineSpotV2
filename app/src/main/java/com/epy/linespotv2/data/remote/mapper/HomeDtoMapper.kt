package com.epy.linespotv2.data.remote.mapper

import com.epy.linespotv2.data.remote.dto.EventDto
import com.epy.linespotv2.data.remote.dto.HomeDto
import com.epy.linespotv2.data.remote.dto.JukirSummaryDto
import com.epy.linespotv2.data.remote.dto.NewsDto
import com.epy.linespotv2.data.remote.dto.ProfileDto
import com.epy.linespotv2.data.remote.dto.SummaryDto
import com.epy.linespotv2.data.remote.dto.WarningsDto
import com.epy.linespotv2.domain.model.HomeEventItem
import com.epy.linespotv2.domain.model.HomeModel
import com.epy.linespotv2.domain.model.HomeNewsItem
import com.epy.linespotv2.domain.model.Profile
import com.epy.linespotv2.domain.model.HomeSummaryInfo
import com.epy.linespotv2.domain.model.HomeWarnings
import com.epy.linespotv2.domain.model.JukirSummaryInfo

fun HomeDto.toDomain(): HomeModel = HomeModel(
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
