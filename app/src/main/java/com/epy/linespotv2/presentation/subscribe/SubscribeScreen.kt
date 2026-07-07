package com.epy.linespotv2.presentation.subscribe

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.epy.linespotv2.core.ui.theme.DarkBlue
import com.epy.linespotv2.core.ui.theme.GreyText
import com.epy.linespotv2.core.ui.theme.PageBg
import com.epy.linespotv2.core.ui.theme.SmartBlue
import com.epy.linespotv2.core.ui.theme.Tangerine
import com.epy.linespotv2.core.ui.theme.White
import com.epy.linespotv2.core.utils.toRupiah
import com.epy.linespotv2.domain.model.subscription.PackageCard
import com.epy.linespotv2.domain.model.subscription.StatusCard
import com.epy.linespotv2.domain.model.subscription.SubscribeResponseModel
import com.epy.linespotv2.presentation.home_customer.FullScreenLoading

private data class PackageOption(
    val name: String,
    val price: Long,
    val period: String,
    val discountInfo: String,
    val badge: String? = null,
    val highlighted: Boolean = false
)

@Composable
fun SubscribeScreen(
    model: SubscribeResponseModel? = null,
    onBack: () -> Unit = {},
    onChoosePackage: (String) -> Unit = {},
    onOpenBenefit: () -> Unit = {},
    onOpenPromoCode: () -> Unit = {},
) {
    val viewModel: SubscribeViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        viewModel.onIntent(SubscribeIntent.loadPage)
    }
    SubscribeScreenContent(
        model = model,
        consumeEffect = { viewModel.consumeEffect() },
        state = viewModel.state.collectAsStateWithLifecycle().value,
        onBack = onBack,
        onChoosePackage = onChoosePackage,
        onOpenBenefit = onOpenBenefit,
        onOpenPromoCode = onOpenPromoCode,
        onTabSelected = { viewModel.onIntent(SubscribeIntent.onClickTab(it)) },
        onRetry = { viewModel.onIntent(SubscribeIntent.loadPage) }
    )
}

@Composable
fun SubscribeScreenContent(
    model: SubscribeResponseModel? = null,
    consumeEffect: () -> Unit = {},
    state: SubscribeState = SubscribeState(),
    onBack: () -> Unit = {},
    onChoosePackage: (String) -> Unit = {},
    onOpenBenefit: () -> Unit = {},
    onOpenPromoCode: () -> Unit = {},
    onTabSelected: (Int) -> Unit = {},
    onRetry: () -> Unit = {},
) {
    val tabs = listOf("Bulanan", "6 Bulan", "Tahunan")
    val selectedTab = state.selectedTabIndex.coerceIn(0, tabs.lastIndex)
    val packages = model?.packageCard
        ?.filter { it.matchesTab(selectedTab) }
        ?.map { it.toUiOption() }
        ?: listOf(PackageOption("Error", 0, "Error", "Error"))
    val namaPackage = packages.firstOrNull()?.name.orEmpty()

    LaunchedEffect(state.subscribeEffect) {
        when (state.subscribeEffect) {
            is SubscribeEffect.NavigateToPackage -> { onChoosePackage(namaPackage); consumeEffect() }
            is SubscribeEffect.NavigateToBenefit -> { onOpenBenefit() ; consumeEffect() }
            is SubscribeEffect.NavigateToPromo -> { onOpenPromoCode() ; consumeEffect() }
            null -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBg)
            .systemBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF071B44), DarkBlue)
                    )
                )
                .padding(horizontal = 20.dp, vertical = 18.dp)
//                .clip(1.dp, border, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(5.dp))
        ) {
            when {
                state.isLoading -> FullScreenLoading()
                state.error != null -> ErrorScreen(
                    message = state.error,
                    onRetry = onRetry
                )
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = "Back",
                    tint = White,
                    modifier = Modifier.clickable { onBack() }
                )

                Spacer(modifier = Modifier.width(14.dp))

                Text(
                    text = "Membership",
                    modifier = Modifier.weight(1f),
                    color = White,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(52.dp))
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (model?.statusCard != null) {
                StatusCard(
                    statusCard = model.statusCard,
                    onOpenBenefit = onOpenBenefit
                )
            } else {
                UnsubscribedStatusCard()
            }

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                tabs.forEachIndexed { index, label ->
                    TabChip(
                        text = label,
                        selected = selectedTab == index,
                        modifier = Modifier.weight(1f)
                    ) { onTabSelected(index) }
                }
            }

            Text(
                text = "Pilih Paket",
                color = DarkBlue,
                style = MaterialTheme.typography.titleSmall
            )

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                packages.forEach { option ->
                    PackageCard(
                        option = option,
                        onClickPackage = { onChoosePackage(option.name) }
                    )
                }
            }

            PromoCard(onOpenPromoCode = onOpenPromoCode)

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun StatusCard(
    statusCard: StatusCard,
    onOpenBenefit: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFB87312))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        listOf(Color(0xFFC08A22), Color(0xFFD9A63A), Color(0xFF9B690F))
                    )
                )
                .padding(18.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Status Anda",
                        color = White.copy(alpha = 0.9f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Benefit",
                        tint = White,
                        modifier = Modifier.clickable { onOpenBenefit() }
                    )
                }

                Text(
                    text = statusCard.paketAktif,
                    color = White,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Aktif hingga ${statusCard.kadaluarsa}",
                    color = White.copy(alpha = 0.92f),
                    style = MaterialTheme.typography.bodyMedium
                )

//                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
//                    Surface(
//                        color = Color.Black.copy(alpha = 0.12f),
//                        shape = RoundedCornerShape(12.dp)
//                    ) {
//                        Text(
//                            text = "Lihat Benefit",
//                            color = White,
//                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
//                            style = MaterialTheme.typography.labelLarge
//                        )
//                    }
//                }

                Text(
                    text = statusCard.benefit,
                    color = White.copy(alpha = 0.88f),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun UnsubscribedStatusCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF2DC))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        listOf(Color(0xFFFFF4DE), Color(0xFFFFE9C2))
                    )
                )
                .padding(18.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = Color(0xFFFFC96A).copy(alpha = 0.25f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Tangerine,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Belum Berlangganan",
                                color = DarkBlue,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        tint = Color(0xFFF4C15A),
                        modifier = Modifier.size(28.dp)
                    )
                }

                Text(
                    text = "Jadi Member Sekarang",
                    color = DarkBlue,
                    style = MaterialTheme.typography.headlineSmall
                )

//                Text(
//                    text = "Nikmati diskon parkir, gratis jam parkir, dan berbagai benefit eksklusif lainnya.",
//                    color = DarkBlue,
//                    style = MaterialTheme.typography.bodySmall
//                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BenefitMiniItem(
                        icon = Icons.Default.LocalOffer,
                        text = "Diskon hingga 25%",
                        modifier = Modifier.weight(1f)
                    )
                    BenefitMiniItem(
                        icon = Icons.Default.CardGiftcard,
                        text = "Gratis parkir bulanan",
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BenefitMiniItem(
                        icon = Icons.Default.Star,
                        text = "Promo eksklusif",
                        modifier = Modifier.weight(1f)
                    )
                    BenefitMiniItem(
                        icon = Icons.Default.SupportAgent,
                        text = "Prioritas customer service",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

//                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
//                    Surface(
//                        color = SmartBlue,
//                        shape = RoundedCornerShape(12.dp),
//                        modifier = Modifier.clickable { onOpenSubscribe() }
//                    ) {
//                        Text(
//                            text = "Lihat Paket",
//                            color = White,
//                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
//                            style = MaterialTheme.typography.labelLarge
//                        )
//                    }
//                }
            }
        }
    }
}

@Composable
private fun BenefitMiniItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = DarkBlue,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = text,
            color = DarkBlue,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun TabChip(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClickTab: () -> Unit
) {
    val bg = if (selected) White else Color.White.copy(alpha = 0.72f)
    val border = if (selected) SmartBlue else Color.Transparent
    val content = if (selected) SmartBlue else DarkBlue

    Box(
        modifier = modifier
            .height(22.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(bg)
            .border(1.dp, border, RoundedCornerShape(5.dp))
            .clickable { onClickTab() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = content,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
private fun PackageCard(
    option: PackageOption,
    onClickPackage: () -> Unit
) {
    val container = if (option.highlighted) Color(0xFFFDF4DF) else White
    val border = if (option.highlighted) Tangerine else Color(0xFFE5E7EB)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, border, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = container)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClickPackage() }
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = option.name,
                        color = DarkBlue,
                        style = MaterialTheme.typography.titleMedium
                    )
                    if (option.badge != null) {
                        Spacer(modifier = Modifier.width(10.dp))
                        Surface(
                            color = SmartBlue,
                            shape = RoundedCornerShape(999.dp)
                        ) {
                            Text(
                                text = option.badge,
                                color = White,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }

                Text(
                    text = "${option.price.toRupiah()} ${option.period}",
                    color = DarkBlue,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = option.discountInfo,
                    color = GreyText,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Box(
                modifier = Modifier.align(Alignment.CenterEnd),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Pilih paket",
                    tint = GreyText
                )
            }
        }
    }
}

@Composable
private fun PromoCard(onOpenPromoCode: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onOpenPromoCode() }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(SmartBlue.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CardGiftcard,
                    contentDescription = "Promo",
                    tint = SmartBlue
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Punya kode promo?",
                    color = DarkBlue,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Gunakan kode untuk mendapatkan diskon",
                    color = GreyText,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Buka promo",
                tint = GreyText
            )
        }
    }
}

@Composable
private fun ErrorScreen(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Terjadi kendala", color = DarkBlue, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = message, color = GreyText, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(20.dp))
        TextButton(onClick = onRetry) {
            Text("Coba Lagi", color = DarkBlue, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
private fun BenefitItem(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = Color(0xFF2FA84F),
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            color = DarkBlue,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

private fun PackageCard.toUiOption(): PackageOption {
    val highlighted = namaPaket.equals("Premium", ignoreCase = true)
    val badge = if (highlighted) "Populer" else null
    return PackageOption(
        name = namaPaket,
        price = harga,
        period = "/${masaBerlaku.trimStart('/')}",
        discountInfo = "${jumlahDiskon}% • $deskripsi",
        badge = badge,
        highlighted = highlighted
    )
}

private fun PackageCard.matchesTab(tabIndex: Int): Boolean {
    val period = masaBerlaku.lowercase()
    return when (tabIndex) {
        0 -> period.contains("bulan") && !period.contains("6")
        1 -> period.contains("6") || period.contains("enam")
        2 -> period.contains("tahun")
        else -> true
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SubscribeScreenPreview() {
    val mockState = SubscribeState(
        isLoading = false,
        error = null,
        subscribeResponseModel = null,
        selectedTabIndex = 2
    )

    MaterialTheme {
        SubscribeScreenContent(
            state = mockState,
            model = SubscribeResponseModel(
                statusCard = StatusCard(
                    paketAktif = "Premium Gold",
                    kadaluarsa = "20 Mei 2025",
                    benefit = "Nikmati benefit parkir premium"
                ),
                packageCard = listOf(
                    PackageCard(
                        namaPaket = "VIP",
                        harga = 990_000L,
                        masaBerlaku = "tahun",
                        jumlahDiskon = 25,
                        deskripsi = "6 jam gratis / bulan",
                        benefit = listOf(
                            "Diskon 25%",
                            "6 jam parkir gratis / bulan"
                        )
                    )
                ),
                promo = emptyList()
            )
        )
    }
}
