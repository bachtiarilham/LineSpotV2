package com.epy.linespotv2.presentation.home_customer.ui_model

import com.epy.linespotv2.core.utils.toRupiah
import com.epy.linespotv2.domain.model.home.ContentsModel
import com.epy.linespotv2.domain.model.home.CustomerHomeModel
import kotlin.collections.filterNotNull
import kotlin.collections.orEmpty

fun CustomerHomeModel.toUiModel(): HomeCustomerUiModel {
    val profile = customerModel
    val contentItems = contents.orEmpty().filterNotNull()

    return HomeCustomerUiModel(
        greetingName = profile?.fullName?.ifBlank { "Customer" } ?: "Customer",
        photoUrl = profile?.photoUrl.orEmpty(),
        balance = profile?.saldo ?: 0L,
        balanceText = (profile?.saldo ?: 0L).toRupiah(),
        membershipTitle = profile?.membershipPackageName?.ifBlank { "Premium Gold" } ?: "Premium Gold",
        membershipExpiredText = profile?.membershipExpiredAt?.ifBlank { "-" } ?: "-",
        quickActions = defaultQuickActions(),
        latestActivity = contentItems.toLatestActivityUi(),
        promo = contentItems.toPromoUi()
    )
}

private fun List<ContentsModel>.toLatestActivityUi(): HomeCustomerActivityUi {
    val item = firstOrNull {
        it.contentTypeCode.equals("ACTIVITY", ignoreCase = true) ||
                it.contentTypeCode.equals("PARKING", ignoreCase = true) ||
                it.contentTypeName.equals("activity", ignoreCase = true)
    }

    return if (item == null) {
        HomeCustomerActivityUi()
    } else {
        HomeCustomerActivityUi(
            title = item.title?.ifBlank { "Aktivitas parkir" } ?: "Aktivitas parkir",
            subtitle = item.summary?.ifBlank { item.eventLocation.orEmpty() }.orEmpty(),
            detail = item.publishAt.orEmpty(),
            actionText = "Lihat Detail"
        )
    }
}

private fun List<ContentsModel>.toPromoUi(): HomeCustomerPromoUi {
    val item = firstOrNull {
        it.contentTypeCode.equals("PROMO", ignoreCase = true) ||
                it.contentTypeName.equals("promo", ignoreCase = true) ||
                it.contentTypeName.equals("news", ignoreCase = true)
    }

    return if (item == null) {
        HomeCustomerPromoUi()
    } else {
        HomeCustomerPromoUi(
            title = item.title?.ifBlank { "Promo spesial" } ?: "Promo spesial",
            description = item.summary?.ifBlank { item.body.orEmpty() }.orEmpty(),
            badge = item.contentTypeName?.ifBlank { "Promo" } ?: "Promo"
        )
    }
}