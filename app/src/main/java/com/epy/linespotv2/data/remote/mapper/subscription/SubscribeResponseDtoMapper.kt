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

fun SubscribeResponseDto.toDomain(): SubscribeResponseModel = SubscribeResponseModel(
    statusCard = statusCard.toDomain(),
    packageCard = packageCard?.map { it.toDomain() },
    promo = promo?.map { it.toDomain() }
)

private fun StatusCardDto?.toDomain(): StatusCard = StatusCard(
    paketAktif = this?.paketAktif,
    kadaluarsa = this?.kadaluarsa,
    benefit = this?.benefit
)

private fun PackageCardDto?.toDomain(): PackageCard = PackageCard(
    namaPaket = this?.namaPaket,
    harga = this?.harga,
    masaBerlaku = this?.masaBerlaku,
    jumlahDiskon = this?.jumlahDiskon,
    deskripsi = this?.deskripsi,
    benefit = this?.benefit
)

private fun PromoDto?.toDomain(): Promo = Promo(
    sNk = this?.sNk,
    promo = this?.promo?.map { it.toDomain() }
)

private fun PromoTerpilihDto?.toDomain(): PromoTerpilih = PromoTerpilih(
    namaPromo = this?.namaPromo,
    deskripsi = this?.deskripsi,
    jumlahDiskon = this?.jumlahDiskon
)
