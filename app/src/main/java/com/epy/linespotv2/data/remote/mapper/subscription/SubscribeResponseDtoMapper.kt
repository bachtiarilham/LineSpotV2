package com.epy.linespotv2.data.remote.mapper.subscription

import com.epy.linespotv2.core.utils.normalizeBackendDateToApiDate
import com.epy.linespotv2.data.remote.dto.subscription.DetailPaket
import com.epy.linespotv2.data.remote.dto.subscription.DetailPromo
import com.epy.linespotv2.data.remote.dto.subscription.ListPaket
import com.epy.linespotv2.data.remote.dto.subscription.PromoTersedia
import com.epy.linespotv2.data.remote.dto.subscription.SubscribeResponseDto
import com.epy.linespotv2.domain.model.subscription.SubscribeResponseModel

fun SubscribeResponseDto.toDomain(): SubscribeResponseModel = SubscribeResponseModel(
    activePackageName = activePackageName,
    activePackageExpired = activePackageExpired.normalizeBackendDateToApiDate(),
    activePackageBenefits = activePackageBenefits.orEmpty(),
    listPaket = listPaket.toDomain(),
    promoTersedia = promoTersedia.toDomain()
)

private fun ListPaket?.toDomain(): com.epy.linespotv2.domain.model.subscription.ListPaket? {
    if (this == null) return null

    return com.epy.linespotv2.domain.model.subscription.ListPaket(
        bulanan = bulanan.orEmpty().map { it.toDomain() },
        enamBulan = enamBulan.orEmpty().map { it.toDomain() },
        tahunan = tahunan.orEmpty().map { it.toDomain() }
    )
}

private fun DetailPaket?.toDomain(): com.epy.linespotv2.domain.model.subscription.DetailPaket {
    return com.epy.linespotv2.domain.model.subscription.DetailPaket(
        namaPaket = this?.namaPaket,
        harga = this?.harga,
        coverageLokasi = this?.coverageLokasi.orEmpty(),
        benefitPackage = this?.benefitPackage.orEmpty()
    )
}

private fun PromoTersedia?.toDomain(): com.epy.linespotv2.domain.model.subscription.PromoTersedia? {
    if (this == null) return null

    return com.epy.linespotv2.domain.model.subscription.PromoTersedia(
        syaratDanKetentuan = syaratDanKetentuan.orEmpty(),
        listPromo = listPromo.orEmpty().map { it.toDomain() }
    )
}

private fun DetailPromo?.toDomain(): com.epy.linespotv2.domain.model.subscription.DetailPromo {
    return com.epy.linespotv2.domain.model.subscription.DetailPromo(
        namaPromo = this?.namaPromo,
        besarDiskon = this?.besarDiskon
    )
}
