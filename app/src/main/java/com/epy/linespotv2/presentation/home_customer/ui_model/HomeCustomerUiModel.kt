package com.epy.linespotv2.presentation.home_customer.ui_model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.epy.linespotv2.core.ui.theme.GreyText
import com.epy.linespotv2.core.ui.theme.SmartBlue
import com.epy.linespotv2.core.ui.theme.Tangerine
import com.epy.linespotv2.core.utils.toRupiah

data class HomeCustomerUiModel(
    val greetingName: String = "Customer",
    val photoUrl: String = "",
    val balance: Long = 0L,
    val balanceText: String = 0L.toRupiah(),
    val membershipTitle: String = "Premium Gold",
    val membershipExpiredText: String = "-",
    val quickActions: List<HomeCustomerQuickActionUi> = defaultQuickActions(),
    val latestActivity: HomeCustomerActivityUi = HomeCustomerActivityUi(),
    val promo: HomeCustomerPromoUi = HomeCustomerPromoUi()
)

data class HomeCustomerQuickActionUi(
    val key: String,
    val title: String,
    val icon: ImageVector,
    val iconBg: Color,
    val iconTint: Color
)

data class HomeCustomerActivityUi(
    val title: String = "Belum ada aktivitas parkir",
    val subtitle: String = "Riwayat parkir akan muncul di sini",
    val detail: String = "",
    val actionText: String = "Detail"
)

data class HomeCustomerPromoUi(
    val title: String = "Belum ada promo tersedia",
    val description: String = "Promo spesial akan ditampilkan di sini.",
    val badge: String = "Promo"
)

fun defaultQuickActions(): List<HomeCustomerQuickActionUi> = listOf(
    HomeCustomerQuickActionUi(
        key = "booking",
        title = "Booking Parkir",
        icon = Icons.Default.GridView,
        iconBg = Color(0xFFEAF2FF),
        iconTint = SmartBlue
    ),
    HomeCustomerQuickActionUi(
        key = "promo",
        title = "Promo",
        icon = Icons.Default.Storefront,
        iconBg = Color(0xFFFFF0E3),
        iconTint = Tangerine
    ),
    HomeCustomerQuickActionUi(
        key = "history",
        title = "Riwayat",
        icon = Icons.Default.History,
        iconBg = Color(0xFFE8ECF6),
        iconTint = GreyText
    )
)
