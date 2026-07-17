package com.epy.linespotv2.presentation.subscribe.ui_model

data class SubscribeUiModel(
    val status: SubscribeStatusUiModel? = null,
    val tabs: List<String> = listOf("Bulanan", "6 Bulan", "Tahunan"),
    val bulananPackages: List<SubscribePackageUiModel> = emptyList(),
    val enamBulanPackages: List<SubscribePackageUiModel> = emptyList(),
    val tahunanPackages: List<SubscribePackageUiModel> = emptyList(),
    val promoTerms: List<String> = emptyList(),
    val promoItems: List<SubscribePromoUiModel> = emptyList()
) {
    fun packagesForTab(tabIndex: Int): List<SubscribePackageUiModel> {
        return when (tabIndex) {
            0 -> bulananPackages
            1 -> enamBulanPackages
            2 -> tahunanPackages
            else -> tahunanPackages
        }
    }

    fun firstAvailablePackage(): SubscribePackageUiModel? {
        return tahunanPackages.firstOrNull()
            ?: enamBulanPackages.firstOrNull()
            ?: bulananPackages.firstOrNull()
    }

    fun activePackageOrFallback(): SubscribePackageUiModel? {
        val activeName = status?.packageName.orEmpty()
        return allPackages().firstOrNull { it.name.equals(activeName, ignoreCase = true) }
            ?: firstAvailablePackage()
    }

    fun allPackages(): List<SubscribePackageUiModel> {
        return bulananPackages + enamBulanPackages + tahunanPackages
    }
}

data class SubscribeStatusUiModel(
    val packageName: String = "",
    val expiredDate: String = "",
    val benefitItems: List<String> = emptyList(),
    val benefitSummary: String = ""
)

data class SubscribePackageUiModel(
    val name: String = "",
    val price: Long = 0L,
    val priceLabel: String = "Rp 0",
    val periodLabel: String = "",
    val infoLabel: String = "",
    val badgeLabel: String? = null,
    val isHighlighted: Boolean = false,
    val benefits: List<String> = emptyList(),
    val coverageAreas: List<String> = emptyList()
)

data class SubscribePromoUiModel(
    val code: String = "",
    val description: String = "",
    val badgeLabel: String = "",
    val discountPercent: Long = 0L,
    val colorStyle: SubscribePromoColorStyle = SubscribePromoColorStyle.GREEN
)

enum class SubscribePromoColorStyle {
    GREEN,
    BLUE,
    ORANGE
}
