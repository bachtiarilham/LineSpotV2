package com.epy.linespotv2.presentation.home_customer

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.LocalParking
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.epy.linespotv2.core.ui.theme.DarkBlue
import com.epy.linespotv2.core.ui.theme.GreyText
import com.epy.linespotv2.core.ui.theme.LightGold
import com.epy.linespotv2.core.ui.theme.PageBg
import com.epy.linespotv2.core.ui.theme.SmartBlue
import com.epy.linespotv2.core.ui.theme.White
import com.epy.linespotv2.domain.model.home.CustomerHomeModel
import com.epy.linespotv2.presentation.home_customer.ui_model.HomeCustomerActivityUi
import com.epy.linespotv2.presentation.home_customer.ui_model.HomeCustomerPromoUi
import com.epy.linespotv2.presentation.home_customer.ui_model.HomeCustomerQuickActionUi
import com.epy.linespotv2.presentation.home_customer.ui_model.HomeCustomerUiModel

private data class QuickActionItem(
    val title: String,
    val icon: ImageVector,
    val iconBg: Color,
    val iconTint: Color,
    val onClick: () -> Unit
)

@Composable
fun HomeCustomerScreen(
    onNavigateToSettings: () -> Unit = {},
    onNavigateToPayment: () -> Unit = {},
    onNavigateToSubscription: () -> Unit = {},
    onNavigateToTopUp: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
    onNavigateToPromo: () -> Unit = {},
    onNavigateToBooking: () -> Unit = {},
    onNavigateToLayananLain: () -> Unit = {},
    viewModel: HomeCustomerViewModel = hiltViewModel(),
    bottomBar: @Composable () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onIntent(HomeCustomerIntent.LoadHomeCustomer)
    }

    Scaffold(
        containerColor = PageBg,
        bottomBar = bottomBar
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(PageBg)
        ) {
            when {
                state.isLoading -> FullScreenLoading()
                state.customerHomeModel != null && state.uiModel != null -> {
                    HomeScreenContent(
                        uiModel = state.uiModel!!,
                        state = state,
                        onIntent = viewModel::onIntent,
                        consumeEffect = viewModel::consumeEffect,
                        onNavigateToPayment = onNavigateToPayment,
                        onNavigateToSettings = onNavigateToSettings,
                        onNavigateToSubscription = onNavigateToSubscription,
                        onNavigateToTopUp = onNavigateToTopUp,
                        onNavigateToLogin = onNavigateToLogin,
                        onNavigateToPromo = onNavigateToPromo,
                        onNavigateToBooking = onNavigateToBooking,
                        onNavigateToLayananLain = onNavigateToLayananLain
                    )
                }
                else -> ErrorScreen(
                    message = state.error ?: "Terjadi kesalahan",
                    onRetry = { viewModel.onIntent(HomeCustomerIntent.LoadHomeCustomer) }
                )
            }
        }
    }
}

@Composable
fun HomeScreenContent(
    uiModel: HomeCustomerUiModel,
    state: HomeCustomerState,
    onIntent: (HomeCustomerIntent) -> Unit,
    consumeEffect: () -> Unit,
    onNavigateToTopUp: () -> Unit,
    onNavigateToPayment: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToSubscription: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToLayananLain: () -> Unit,
    onNavigateToBooking: () -> Unit,
    onNavigateToPromo: () -> Unit
) {
    LaunchedEffect(state.customerHomeEffect) {
        when (state.customerHomeEffect) {
            is HomeCustomerEffect.NavigateToTopUp -> { onNavigateToTopUp(); consumeEffect() }
            is HomeCustomerEffect.NavigateToSettings -> { onNavigateToSettings(); consumeEffect() }
            is HomeCustomerEffect.NavigateToPayment -> { onNavigateToPayment(); consumeEffect() }
            is HomeCustomerEffect.NavigateToSubscription -> { onNavigateToSubscription(); consumeEffect() }
            is HomeCustomerEffect.ShowToast -> consumeEffect()
            is HomeCustomerEffect.SessionExpired -> { onNavigateToLogin(); consumeEffect() }
            is HomeCustomerEffect.NavigateToLayananLain -> { onNavigateToLayananLain(); consumeEffect() }
            is HomeCustomerEffect.NavigateToPromo -> { onNavigateToPromo(); consumeEffect() }
            is HomeCustomerEffect.NavigateToBooking -> { onNavigateToBooking(); consumeEffect() }
            null -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .systemBarsPadding()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        HomeHeader(
            uiModel = uiModel,
            onProfileClick = { onIntent(HomeCustomerIntent.clickProfile) },
            onNotificationClick = { onIntent(HomeCustomerIntent.clickNotification(0)) }
        )

        BalanceCard(
            balanceText = uiModel.balanceText,
            onTopUp = { onIntent(HomeCustomerIntent.clickTopUp) }
        )

        PremiumCard(
            title = uiModel.membershipTitle,
            expiredDate = uiModel.membershipExpiredText,
            onSubscribe = { onIntent(HomeCustomerIntent.clickSubscribe) }
        )

        QuickActions(
            items = uiModel.quickActions,
            onTopUpSaldo = { onIntent(HomeCustomerIntent.clickPayment) },
            onMembership = { onIntent(HomeCustomerIntent.clickSubscribe) },
            onParkirOnStreet = { onIntent(HomeCustomerIntent.clickPayment) },
            onBookingParkir = { onIntent(HomeCustomerIntent.clickBooking) },
            onPromo = { onIntent(HomeCustomerIntent.clickPromo) },
            onLayananLain = { onIntent(HomeCustomerIntent.clickLayananLain) }
        )

        SectionTitle(
            title = "Aktivitas Parkir Terkini",
            actionText = "Lihat semua"
        )

        ActivityCard(
            title = uiModel.latestActivity.title,
            subtitle = uiModel.latestActivity.subtitle,
            detail = uiModel.latestActivity.detail,
            actionText = uiModel.latestActivity.actionText
        )

        SectionTitle(
            title = "Promo Spesial Untukmu",
            actionText = "Lihat semua"
        )

        PromoCard(
            title = uiModel.promo.title,
            description = uiModel.promo.description,
            badge = uiModel.promo.badge
        )

        Spacer(modifier = Modifier.height(72.dp))
    }
}

@Composable
private fun HomeHeader(
    uiModel: HomeCustomerUiModel,
    onProfileClick: () -> Unit,
    onNotificationClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Hi, ${uiModel.greetingName} 👋",
                color = DarkBlue,
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Selamat datang kembali",
                color = GreyText,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 2.dp,
                modifier = Modifier.clickable { onNotificationClick() }
            ) {
                Box(
                    modifier = Modifier.size(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.NotificationsNone,
                        contentDescription = "Notifikasi",
                        tint = DarkBlue
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .clickable { onProfileClick() }
                    .background(
                        Brush.linearGradient(listOf(DarkBlue, SmartBlue))
                    ),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = uiModel.photoUrl,
                    contentDescription = "Foto Profil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                )
            }
        }
    }
}

@Composable
private fun BalanceCard(
    balanceText: String,
    onTopUp: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = DarkBlue)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF0B1D4D), Color(0xFF173A9A), SmartBlue)
                    )
                )
                .padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.14f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountBalanceWallet,
                        contentDescription = "Saldo",
                        tint = White,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Saldo Anda",
                        color = White.copy(alpha = 0.75f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = balanceText,
                        color = White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Surface(
                        color = White,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.clickable { onTopUp() }
                    ) {
                        Text(
                            text = "Top Up",
                            color = SmartBlue,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PremiumCard(
    title: String,
    expiredDate: String,
    onSubscribe: () -> Unit
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
                        listOf(Color(0xFFB87A12), Color(0xFFD49A2A), Color(0xFF8A560B))
                    )
                )
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color.White.copy(alpha = 0.16f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Badge,
                            contentDescription = "Premium",
                            tint = White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = title,
                            color = White,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "Aktif hingga $expiredDate",
                            color = White.copy(alpha = 0.8f),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                Surface(
                    color = Color(0xFF6A3E00).copy(alpha = 0.35f),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.clickable { onSubscribe() }
                ) {
                    Text(
                        text = "Daftar",
                        color = White,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickActions(
    items: List<HomeCustomerQuickActionUi>,
    onTopUpSaldo: () -> Unit,
    onMembership: () -> Unit,
    onParkirOnStreet: () -> Unit,
    onBookingParkir: () -> Unit,
    onPromo: () -> Unit,
    onLayananLain: () -> Unit
) {
    val actionItems = items.map { item ->
        QuickActionItem(
            title = item.title,
            icon = item.icon,
            iconBg = item.iconBg,
            iconTint = item.iconTint,
            onClick = when (item.key) {
                "topup" -> onTopUpSaldo
                "membership" -> onMembership
                "parkir" -> onParkirOnStreet
                "booking" -> onBookingParkir
                "promo" -> onPromo
                else -> onLayananLain
            }
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                actionItems.take(3).forEach { item -> ServiceShortcut(item = item) }
            }
        }
    }
}

@Composable
private fun ServiceShortcut(item: QuickActionItem) {
    Column(
        modifier = Modifier
            .width(82.dp)
            .clickable { item.onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Surface(
            color = item.iconBg,
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.size(54.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = item.iconTint,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
        Text(
            text = item.title,
            color = DarkBlue,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun SectionTitle(
    title: String,
    actionText: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            color = DarkBlue,
            style = MaterialTheme.typography.titleMedium
        )
        TextButton(onClick = { }) {
            Text(
                text = actionText,
                color = SmartBlue,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
private fun ActivityCard(
    title: String,
    subtitle: String,
    detail: String,
    actionText: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFFE9F4EF)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalParking,
                        contentDescription = "Aktivitas",
                        tint = Color(0xFF4CAF90)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = title, color = GreyText, style = MaterialTheme.typography.labelSmall)
                    Text(text = subtitle, color = DarkBlue, style = MaterialTheme.typography.bodyMedium)
                    Text(text = detail, color = GreyText, style = MaterialTheme.typography.labelSmall)
                }
            }
            Surface(color = SmartBlue, shape = RoundedCornerShape(12.dp)) {
                Text(
                    text = actionText,
                    color = White,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@Composable
private fun PromoCard(
    title: String,
    description: String,
    badge: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Surface(color = LightGold, shape = RoundedCornerShape(10.dp)) {
                Text(
                    text = badge,
                    color = DarkBlue,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Text(
                text = title,
                color = DarkBlue,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = description,
                color = GreyText,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun FullScreenLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = DarkBlue)
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
        Text(
            text = "Terjadi kendala",
            color = DarkBlue,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = message,
            color = GreyText,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextButton(onClick = onRetry) {
            Text("Coba Lagi", color = DarkBlue, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenContentPreview() {
    val mockHome = CustomerHomeModel()
    val mockUiModel = HomeCustomerUiModel(
        greetingName = "Andi",
        balanceText = "Rp 125.000",
        membershipTitle = "Premium Gold",
        membershipExpiredText = "20 Mei 2026",
        latestActivity = HomeCustomerActivityUi(
            title = "Sedang parkir di",
            subtitle = "Jl. Sudirman No. 45",
            detail = "Durasi 1 jam 20 menit",
            actionText = "Lihat Detail"
        ),
        promo = HomeCustomerPromoUi(
            title = "Diskon 20% Top Up Akhir Pekan",
            description = "Isi saldo sekarang dan dapatkan bonus khusus pengguna aktif.",
            badge = "Promo"
        )
    )

    HomeScreenContent(
        uiModel = mockUiModel,
        state = HomeCustomerState(
            isLoading = false,
            customerHomeModel = mockHome,
            uiModel = mockUiModel
        ),
        onIntent = {},
        consumeEffect = {},
        onNavigateToPayment = {},
        onNavigateToSettings = {},
        onNavigateToSubscription = {},
        onNavigateToTopUp = {},
        onNavigateToLogin = {},
        onNavigateToBooking = {},
        onNavigateToLayananLain = {},
        onNavigateToPromo = {}
    )
}
