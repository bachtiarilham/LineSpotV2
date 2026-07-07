package com.epy.linespotv2.data.remote.mapper.subscription

import com.epy.linespotv2.data.remote.dto.subscription.PackageCardDto
import com.epy.linespotv2.data.remote.dto.subscription.PromoDto
import com.epy.linespotv2.data.remote.dto.subscription.PromoTerpilihDto
import com.epy.linespotv2.data.remote.dto.subscription.StatusCardDto
import com.epy.linespotv2.data.remote.dto.subscription.SubscribeResponseDto
import com.epy.linespotv2.domain.model.subscription.PackageCard
import com.epy.linespotv2.domain.model.subscription.Promo
import com.epy.linespotv2.domain.model.subscription.PromoTerpilih
import com.epy.linespotv2.domain.model.subscription.StatusCard
import com.epy.linespotv2.domain.model.subscription.SubscribeResponseModel

fun SubscribeResponseDto.toDomain() : SubscribeResponseModel = SubscribeResponseModel (
    statusCard = statusCard.toDomain(),
    packageCard = this.packageCard?.map { it.toDomain() }.orEmpty(),
    promo = this.promo?.map { it.toDomain() }.orEmpty(),
)

private fun StatusCardDto?.toDomain(): StatusCard = StatusCard(
    paketAktif = this?.paketAktif.orEmpty(),
    kadaluarsa = this?.kadaluarsa.orEmpty(),
    benefit = this?.benefit.orEmpty(),
)

private fun PackageCardDto?.toDomain() : PackageCard = PackageCard(
    namaPaket = this?.namaPaket.orEmpty(),
    harga = this?.harga ?: 0L,
    masaBerlaku = this?.masaBerlaku.orEmpty(),
    jumlahDiskon = this?.jumlahDiskon ?: 0L,
    deskripsi = this?.deskripsi.orEmpty(),
    benefit = this?.benefit.orEmpty(),
)

private fun PromoDto?.toDomain() : Promo = Promo (
    sNk = this?.sNk.orEmpty(),
    promo = this?.promo?.map { it.toDomain() }.orEmpty(),
)

private fun PromoTerpilihDto?.toDomain() : PromoTerpilih = PromoTerpilih (
    namaPromo = this?.namaPromo.orEmpty(),
    deskripsi = this?.deskripsi.orEmpty(),
    jumlahDiskon = this?.jumlahDiskon ?: 0L,
)
