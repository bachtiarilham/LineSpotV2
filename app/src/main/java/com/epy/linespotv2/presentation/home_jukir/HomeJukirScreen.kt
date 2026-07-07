package com.epy.linespotv2.presentation.home_jukir

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
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.epy.linespotv2.core.ui.theme.Black
import com.epy.linespotv2.core.ui.theme.DarkBlue
import com.epy.linespotv2.core.ui.theme.GreyText
import com.epy.linespotv2.core.ui.theme.PageBg
import com.epy.linespotv2.core.ui.theme.SmartBlue
import com.epy.linespotv2.core.ui.theme.White
import com.epy.linespotv2.core.utils.toIndonesiaDate
import com.epy.linespotv2.domain.model.home.HomeResponseModel
import com.epy.linespotv2.domain.model.home.HomeSummaryInfo
import com.epy.linespotv2.domain.model.home.HomeWarnings
import com.epy.linespotv2.domain.model.home.JukirSummaryInfo
import com.epy.linespotv2.domain.model.home.Profile
import java.util.Date

@Composable
fun HomeJukirScreen(
    onNavigateToSettings: () -> Unit = {},
    onNavigateToNotification : () -> Unit = {},
    onNavigateToRiwayat: () -> Unit = {},
    onNavigateToScanTicket: () -> Unit = {},
    onNavigateToInputManual: () -> Unit = {},
    onNavigateToLaporan: () -> Unit = {},
    onNavigateToBantuan: () -> Unit = {},
    onNavigateToTopUp: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel(),
    bottomBar: @Composable () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onIntent(HomeIntent.loadHome)
    }

    androidx.compose.material3.Scaffold(
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
                state.isLoading && state.homeResponseModel == null -> FullScreenLoading()
                state.homeResponseModel != null -> {
                    HomeScreenContent(
                        home = state.homeResponseModel!!,
                        state = state,
                        onIntent = viewModel::onIntent,
                        onNavigateToSettings = onNavigateToSettings,
                        onNavigateToNotification = onNavigateToNotification,
                        onNavigateToRiwayat = onNavigateToRiwayat,
                        onNavigateToScanTicket = onNavigateToScanTicket,
                        onNavigateToInputManual = onNavigateToInputManual,
                        onNavigateToLaporan = onNavigateToLaporan,
                        onNavigateToBantuan = onNavigateToBantuan,
                        onNavigateToTopUp = onNavigateToTopUp,
                        onNavigateToLogin = onNavigateToLogin,
                        consumeEffect = { viewModel.consumeEffect() },
                    )
                }
                else -> ErrorScreen(
                    message = state.error ?: "Terjadi kesalahan",
                    onRetry = { viewModel.onIntent(HomeIntent.loadHome) }
                )
            }
        }
    }
}

@Composable
fun HomeScreenContent(
    home: HomeResponseModel,
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,

    onNavigateToSettings: () -> Unit,
    onNavigateToNotification : () -> Unit,
    onNavigateToRiwayat: () -> Unit,
    onNavigateToScanTicket: () -> Unit,
    onNavigateToInputManual: () -> Unit,
    onNavigateToLaporan: () -> Unit,
    onNavigateToBantuan: () -> Unit,
    onNavigateToTopUp: () -> Unit,
    onNavigateToLogin: () -> Unit,

    consumeEffect: () -> Unit
) {
    LaunchedEffect(state.homeEffect) {
        when (state.homeEffect) {
            is HomeEffect.NavigateToTopUp -> { onNavigateToTopUp (); consumeEffect()}
            is HomeEffect.NavigateToSettings -> { onNavigateToSettings(); consumeEffect()}
            is HomeEffect.ShowToast -> {consumeEffect()}
            is HomeEffect.SessionExpired -> {onNavigateToLogin(); consumeEffect()}
            is HomeEffect.NavigateToRiwayat -> {onNavigateToRiwayat(); consumeEffect()}
            is HomeEffect.NavigateToBantuan -> {onNavigateToBantuan(); consumeEffect()}
            is HomeEffect.NavigateToInputManual -> {onNavigateToInputManual(); consumeEffect()}
            is HomeEffect.NavigateToLaporan -> {onNavigateToLaporan(); consumeEffect()}
            is HomeEffect.NavigateToScanTicket -> {onNavigateToScanTicket(); consumeEffect()}
            is HomeEffect.NavigateToNotification -> {onNavigateToNotification(); consumeEffect()}
            null -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .systemBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        JukirTopBar(
            onNotificationClick = { onIntent(HomeIntent.clickNotification(0)) },
            onProfileClick = { onIntent(HomeIntent.clickProfile) },
            title = "LineSpot Jukir",
//            name = home.profile.name.ifBlank { "Petugas Dishub" }
        )

//        GreetingSection(
//            title = "Selamat pagi,",
//            name = home.profile.name.ifBlank { "Petugas Dishub" }
//        )

        AreaTaskCard(
            zonaValue = home.jukirSummary.zona.ifBlank { "Error" },
            lokasiValue = home.jukirSummary.lokasi.ifBlank { "Error" },
            areaValue = home.jukirSummary.area.ifBlank { "Error" },
        )

        Text(
            text = Date().toIndonesiaDate(),
            color = DarkBlue,
            style = MaterialTheme.typography.titleSmall
        )

        IncomeBalanceCard(
            onRiwayatClick = { onIntent(HomeIntent.clickRiwayat) },
            onTopUpClick = { onIntent(HomeIntent.clickTopUp) }
        )
        QuickActionsCard(
            onScanTicketClick = { onIntent(HomeIntent.clickScanTiket) },
            onInputManualClick = { onIntent(HomeIntent.clickInputManual) },
            onLaporanClick = { onIntent(HomeIntent.clickLaporan) },
            onBantuanClick = { onIntent(HomeIntent.clickBantuan) }
        )
        SupervisorCard()

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun JukirTopBar(
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit,
    title: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.Default.NotificationsNone,
            contentDescription = "Notifikasi",
            tint = DarkBlue,
            modifier = Modifier
                .size(30.dp)
                .clickable { onNotificationClick() }
        )

        Text(text = title, color = Black, style = MaterialTheme.typography.bodyLarge)

        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(White)
                .clickable { onProfileClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = SmartBlue,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Composable
private fun GreetingSection(title: String, name: String) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(text = title, color = GreyText, style = MaterialTheme.typography.bodyLarge)
        Text(text = name, color = DarkBlue, style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
private fun AreaTaskCard(
    zonaValue: String,
    lokasiValue: String,
    areaValue: String,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Area Tugas", color = SmartBlue, style = MaterialTheme.typography.titleMedium)
        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = zonaValue, color = Black, style = MaterialTheme.typography.bodyMedium)
            Text(text = lokasiValue, color = Black, style = MaterialTheme.typography.bodyMedium)
            Text(text = areaValue, color = Black, style = MaterialTheme.typography.bodyMedium)

        }
    }
}

@Composable
private fun MetricsSummaryCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MetricBox(
                modifier = Modifier.weight(1f),
                title = "Masuk",
                value = "128",
                icon = Icons.Default.TouchApp
            )
            MetricBox(
                modifier = Modifier.weight(1f),
                title = "Keluar",
                value = "115",
                icon = Icons.Default.People
            )
            MetricBox(
                modifier = Modifier.weight(1f),
                title = "Pendapatan",
                value = "Rp 1.250.000",
                icon = Icons.Default.Star
            )
        }
    }
}

@Composable
private fun MetricBox(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Surface(
        modifier = modifier,
        color = Color(0xFFF7FAFF),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = SmartBlue,
                modifier = Modifier.size(18.dp)
            )
            Text(text = title, color = GreyText, style = MaterialTheme.typography.bodySmall)
            Text(
                text = value,
                color = DarkBlue,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun IncomeBalanceCard(
    onRiwayatClick: () -> Unit,
    onTopUpClick: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Pendapatan (Hari Ini)",
                    color = GreyText,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Rp 1.250.000",
                    color = DarkBlue,
                    style = MaterialTheme.typography.headlineSmall
                )
//                Text(
//                    text = "↑ 12% dari kemarin",
//                    color = Color(0xFF2FA84F),
//                    style = MaterialTheme.typography.bodyMedium
//                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Saldo",
                    color = GreyText,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Rp 2.750.000",
                    color = DarkBlue,
                    style = MaterialTheme.typography.headlineSmall
                )
//                Text(
//                    text = "Saldo siap dicairkan",
//                    color = GreyText,
//                    style = MaterialTheme.typography.bodyMedium
//                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            QuickActionItem(icon = Icons.Default.History,label = "Riwayat",onRiwayatClick)
            QuickActionItem(icon = Icons.Default.AccountBalanceWallet, label = "Top Up", onTopUpClick)
        }
    }
}

@Composable
private fun QuickActionsCard(
    onScanTicketClick : () -> Unit ,
    onInputManualClick : () -> Unit,
    onLaporanClick : () -> Unit,
    onBantuanClick : () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Aksi Cepat",
                color = DarkBlue,
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                QuickActionItem(Icons.Default.QrCodeScanner, "Scan Tiket", onScanTicketClick)
                QuickActionItem(Icons.Default.Edit, "Input Manual", onInputManualClick)
                QuickActionItem(Icons.Default.Description, "Laporan", onLaporanClick)
                QuickActionItem(Icons.Default.QuestionMark, "Bantuan", onBantuanClick)
            }
        }
    }
}

@Composable
private fun QuickActionItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0xFFF4F8FF))
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = SmartBlue)
        }
        Text(
            text = label,
            color = GreyText,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SupervisorCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F1DE)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEED8A1)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.People, null, tint = DarkBlue)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Anda terhubung dengan Pengawas",
                    color = GreyText,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Budi Santoso",
                    color = DarkBlue,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            TextButton(onClick = { }) {
                Text(text = "Hubungi", color = SmartBlue)
            }
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
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextButton(onClick = onRetry) {
            Text("Coba Lagi", color = DarkBlue, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    val mockHome = HomeResponseModel(
        profile = Profile(id = 1, name = "Petugas Dishub", photoUrl = null),
        summary = HomeSummaryInfo(saldo = 2_750_000, expiredDate = ""),
        jukirSummary = JukirSummaryInfo(pendapatan = 125000, lokasi = "Jl Purnawarman", area = "B", zona = "Bluechip"),
        events = emptyList(),
        news = emptyList(),
        warnings = HomeWarnings(profile = null, parking = null, finance = null)
    )

    MaterialTheme {
        HomeScreenContent(
            home = mockHome,
            state = HomeState(
                isLoading = false,
                homeResponseModel = mockHome
            ),
            onIntent = {},

            onNavigateToSettings = {},
            onNavigateToNotification = {},
            onNavigateToRiwayat = {},
            onNavigateToScanTicket= {},
            onNavigateToInputManual= {},
            onNavigateToLaporan= {},
            onNavigateToBantuan= {},
            onNavigateToTopUp= {},
            onNavigateToLogin= {},

            consumeEffect = {},
        )
    }
}
