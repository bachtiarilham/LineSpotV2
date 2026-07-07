package com.epy.linespotv2.data.remote.mapper.home

import com.epy.linespotv2.data.local.entity.HomeEntity
import com.epy.linespotv2.domain.model.home.HomeEventItem
import com.epy.linespotv2.domain.model.home.HomeNewsItem
import com.epy.linespotv2.domain.model.home.HomeResponseModel
import com.epy.linespotv2.domain.model.home.HomeWarnings
import com.epy.linespotv2.domain.model.home.Profile

fun HomeEntity.toDomain(): HomeResponseModel = HomeResponseModel(
    profile = Profile(
        id = profileId,
        name = profileName,
        photoUrl = photoUrl,
        saldo = saldo,
        expiredDate = expiredDate,
        pendapatan = pendapatan,
        lokasi = lokasi,
        area = area,
        zona = zona
    ),
    events = events.orEmptyEventList(),
    news = news.orEmptyNewsList(),
    warnings = HomeWarnings(
        profile = warningProfile,
        parking = warningParking,
        finance = warningFinance
    )
)

fun HomeResponseModel.toEntity(): HomeEntity = HomeEntity(
    profileId = profile.id,
    profileName = profile.name,
    photoUrl = profile.photoUrl,
    saldo = profile.saldo,
    expiredDate = profile.expiredDate,
    pendapatan = profile.pendapatan,
    lokasi = profile.lokasi,
    area = profile.area,
    zona = profile.zona,
    events = events.orEmptyEventList(),
    news = news.orEmptyNewsList(),
    warningProfile = warnings.profile,
    warningParking = warnings.parking,
    warningFinance = warnings.finance
)

private fun List<HomeEventItem>?.orEmptyEventList(): List<HomeEventItem> = this.orEmpty()

private fun List<HomeNewsItem>?.orEmptyNewsList(): List<HomeNewsItem> = this.orEmpty()
