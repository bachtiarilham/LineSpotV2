package com.epy.linespotv2.presentation.subscribe.ui_model

import com.epy.linespotv2.core.utils.normalizeBackendDateToApiDate
import com.epy.linespotv2.core.utils.toRupiah
import com.epy.linespotv2.domain.model.subscription.DetailPaket
import com.epy.linespotv2.domain.model.subscription.DetailPromo
import com.epy.linespotv2.domain.model.subscription.ListPaket
import com.epy.linespotv2.domain.model.subscription.PromoTersedia
import com.epy.linespotv2.domain.model.subscription.SubscribeResponseModel

fun SubscribeResponseModel?.toUiModel(): SubscribeUiModel {
    if (this == null) return SubscribeUiModel()

    return SubscribeUiModel(
        status = activeStatusUiModel(),
        bulananPackages = listPaket.toBulananPackages(),
        enamBulanPackages = listPaket.toEnamBulanPackages(),
        tahunanPackages = listPaket.toTahunanPackages(),
        promoTerms = promoTersedia.toPromoTerms(),
        promoItems = promoTersedia.toPromoItems()
    )
}

private fun SubscribeResponseModel.activeStatusUiModel(): SubscribeStatusUiModel? {
    val packageName = activePackageName.orEmpty().trim()
    val benefitItems = activePackageBenefits.orEmpty().filterNotNull().map { it.trim() }.filter { it.isNotBlank() }
    if (packageName.isBlank() && benefitItems.isEmpty() && activePackageExpired.isNullOrBlank()) {
        return null
    }

    return SubscribeStatusUiModel(
        packageName = packageName,
        expiredDate = activePackageExpired.normalizeBackendDateToApiDate(),
        benefitItems = benefitItems,
        benefitSummary = benefitItems.joinToString(" • ")
    )
}

private fun ListPaket?.toBulananPackages(): List<SubscribePackageUiModel> {
    return this?.bulanan.orEmpty().mapNotNull { it.toUiModel(periodLabel = "/bulan") }
}

private fun ListPaket?.toEnamBulanPackages(): List<SubscribePackageUiModel> {
    return this?.enamBulan.orEmpty().mapNotNull { it.toUiModel(periodLabel = "/6 bulan") }
}

private fun ListPaket?.toTahunanPackages(): List<SubscribePackageUiModel> {
    return this?.tahunan.orEmpty().mapNotNull { it.toUiModel(periodLabel = "/tahun") }
}

private fun DetailPaket?.toUiModel(periodLabel: String): SubscribePackageUiModel? {
    if (this == null) return null

    val name = namaPaket.orEmpty().trim().ifBlank { return null }
    val benefitItems = benefitPackage.orEmpty().filterNotNull().map { it.trim() }.filter { it.isNotBlank() }
    val coverageAreas = coverageLokasi.orEmpty().filterNotNull().map { it.trim() }.filter { it.isNotBlank() }
    val isHighlighted = name.contains("premium", ignoreCase = true) || name.contains("gold", ignoreCase = true)
    val badgeLabel = if (isHighlighted) "Populer" else null
    val infoLabel = buildList {
        benefitItems.firstOrNull()?.let { add(it) }
        if (coverageAreas.isNotEmpty()) add("${coverageAreas.size} area parkir")
    }.joinToString(" • ").ifBlank { "Benefit membership" }

    return SubscribePackageUiModel(
        name = name,
        price = harga ?: 0L,
        priceLabel = (harga ?: 0L).toRupiah(),
        periodLabel = periodLabel,
        infoLabel = infoLabel,
        badgeLabel = badgeLabel,
        isHighlighted = isHighlighted,
        benefits = benefitItems,
        coverageAreas = coverageAreas
    )
}

private fun PromoTersedia?.toPromoTerms(): List<String> {
    return this?.syaratDanKetentuan.orEmpty()
        .filterNotNull()
        .map { it.trim() }
        .filter { it.isNotBlank() }
}

private fun PromoTersedia?.toPromoItems(): List<SubscribePromoUiModel> {
    return this?.listPromo.orEmpty().mapNotNull { it.toUiModel() }
}

private fun DetailPromo?.toUiModel(): SubscribePromoUiModel? {
    if (this == null) return null

    val promoName = namaPromo.orEmpty().trim().ifBlank { return null }
    val discount = besarDiskon ?: 0L
    val colorStyle = when {
        discount >= 20L -> SubscribePromoColorStyle.ORANGE
        discount >= 15L -> SubscribePromoColorStyle.BLUE
        else -> SubscribePromoColorStyle.GREEN
    }

    return SubscribePromoUiModel(
        code = promoName,
        description = "Diskon ${discount}% untuk paket membership",
        badgeLabel = "HEMAT ${discount}%",
        discountPercent = discount,
        colorStyle = colorStyle
    )
}
