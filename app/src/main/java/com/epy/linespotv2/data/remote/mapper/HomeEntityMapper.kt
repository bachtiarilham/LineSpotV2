package com.epy.linespotv2.data.remote.mapper

import com.epy.linespotv2.data.local.entity.HomeEntity
import com.epy.linespotv2.domain.model.HomeModel
import com.epy.linespotv2.domain.model.HomeSummaryInfo
import com.epy.linespotv2.domain.model.HomeWarnings
import com.epy.linespotv2.domain.model.JukirSummaryInfo
import com.epy.linespotv2.domain.model.Profile

fun HomeEntity.toDomain(): HomeModel = HomeModel(
    profile = Profile(
        id = profileId,
        name = profileName,
        photoUrl = photoUrl
    ),
    summary = HomeSummaryInfo(
        saldo = saldo,
        expiredDate = expiredDate
    ),
    jukirSummary = JukirSummaryInfo(
        pendapatan = pendapatan,
        lokasi = lokasi,
        area = area,
        zona = zona
    ),
    events           = events,
    news             = news,
    warnings = HomeWarnings(
        profile = warningProfile,
        parking = warningParking,
        finance = warningFinance
    )
)

fun HomeModel.toEntity(): HomeEntity = HomeEntity(
    profileId = profile.id,
    profileName = profile.name,
    photoUrl = profile.photoUrl,
    saldo = summary.saldo,
    expiredDate = summary.expiredDate,
    pendapatan = jukirSummary.pendapatan,
    lokasi = jukirSummary.lokasi,
    area = jukirSummary.area,
    zona = jukirSummary.zona,
    events = events,
    news = news,
    warningProfile = warnings.profile,
    warningParking = warnings.parking,
    warningFinance = warnings.finance
)