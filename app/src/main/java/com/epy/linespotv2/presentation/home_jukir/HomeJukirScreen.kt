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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.epy.linespotv2.core.utils.toRupiah
import com.epy.linespotv2.domain.model.home.HomeResponseModel
import com.epy.linespotv2.domain.model.home.HomeWarnings
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
    viewModel: HomeJukirViewModel = hiltViewModel(),
    bottomBar: @Composable () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onIntent(HomeJukirIntent.loadHomeJukir)
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
                state.isLoading && state.homeResponseModel == null -> FullScreenLoading()
                state.homeResponseModel != null -> {
                    HomeScreenContent(
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
                    onRetry = { viewModel.onIntent(HomeJukirIntent.loadHomeJukir) }
                )
            }
        }
    }
}

@Composable
fun HomeScreenContent(
    state: HomeJukirState,
    onIntent: (HomeJukirIntent) -> Unit,

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
    val home = state.homeResponseModel ?: return

    LaunchedEffect(state.homeJukirEffect) {
        when (state.homeJukirEffect) {
            is HomeJukirEffect.NavigateToTopUp -> { onNavigateToTopUp (); consumeEffect()}
            is HomeJukirEffect.NavigateToSettings -> { onNavigateToSettings(); consumeEffect()}
            is HomeJukirEffect.ShowToast -> {consumeEffect()}
            is HomeJukirEffect.SessionExpired -> {onNavigateToLogin(); consumeEffect()}
            is HomeJukirEffect.NavigateToRiwayat -> {onNavigateToRiwayat(); consumeEffect()}
            is HomeJukirEffect.NavigateToBantuan -> {onNavigateToBantuan(); consumeEffect()}
            is HomeJukirEffect.NavigateToInputManual -> {onNavigateToInputManual(); consumeEffect()}
            is HomeJukirEffect.NavigateToLaporan -> {onNavigateToLaporan(); consumeEffect()}
            is HomeJukirEffect.NavigateToScanTicket -> {onNavigateToScanTicket(); consumeEffect()}
            is HomeJukirEffect.NavigateToNotification -> {onNavigateToNotification(); consumeEffect()}
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
            onNotificationClick = { onIntent(HomeJukirIntent.clickNotification(0)) },
            onProfileClick = { onIntent(HomeJukirIntent.clickProfile) },
            title = home.profile.name.ifBlank { "Petugas Dishub" },
        )

        AreaTaskCard(
            zonaValue = home.profile.zona.ifBlank { "-" },
            lokasiValue = home.profile.lokasi.ifBlank { "-" },
            areaValue = home.profile.area.ifBlank { "-" },
        )

        Text(
            text = Date().toIndonesiaDate(),
            color = DarkBlue,
            style = MaterialTheme.typography.titleSmall
        )

        home.warnings.finance
            ?.takeIf { it.isNotBlank() }
            ?.let { financeWarning ->
                WarningCard(message = financeWarning)
            }

        IncomeBalanceCard(
            pendapatan = home.profile.pendapatan,
            saldo = home.profile.saldo,
            onRiwayatClick = { onIntent(HomeJukirIntent.clickRiwayat) },
            onTopUpClick = { onIntent(HomeJukirIntent.clickTopUp) }
        )
        QuickActionsCard(
            onScanTicketClick = { onIntent(HomeJukirIntent.clickScanTiket) },
            onInputManualClick = { onIntent(HomeJukirIntent.clickInputManual) },
            onLaporanClick = { onIntent(HomeJukirIntent.clickLaporan) },
            onBantuanClick = { onIntent(HomeJukirIntent.clickBantuan) }
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            TaskInfoRow(label = "Zona", value = zonaValue)
            TaskInfoRow(label = "Lokasi", value = lokasiValue)
            TaskInfoRow(label = "Area", value = areaValue)
        }
    }
}

@Composable
private fun TaskInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = label,
            color = GreyText,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.width(48.dp)
        )
        Text(
            text = value,
            color = Black,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun WarningCard(message: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Perhatian",
                color = DarkBlue,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = message,
                color = GreyText,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun IncomeBalanceCard(
    pendapatan: Long,
    saldo: Long,
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
                    text = pendapatan.toRupiah(),
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
                    text = saldo.toRupiah(),
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
        HorizontalDivider(
            modifier = Modifier.padding(top = 8.dp),
            color = PageBg
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
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
    icon: ImageVector,
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
        profile = Profile(
            id = 1, name = "Petugas Dishub", photoUrl = null,
            saldo = 2_750_000L,
            expiredDate = "30 Mei 2024",
            pendapatan = 1_250_000L,
            lokasi = "Jl. Sudirman",
            area = "Zona Biru",
            zona = "Area Tugas"
        ),
        events = emptyList(),
        news = emptyList(),
        warnings = HomeWarnings(profile = null, parking = null, finance = null)
    )

    MaterialTheme {
        HomeScreenContent(
            state = HomeJukirState(
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
