package com.epy.linespotv2.presentation.home_jukir

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.epy.linespotv2.core.ui.theme.Black
import com.epy.linespotv2.core.ui.theme.DarkBlue
import com.epy.linespotv2.core.ui.theme.GreyText
import com.epy.linespotv2.core.ui.theme.PageBg
import com.epy.linespotv2.core.ui.theme.SmartBlue
import com.epy.linespotv2.core.ui.theme.White
import com.epy.linespotv2.core.utils.toRupiah
import com.epy.linespotv2.domain.model.home.JukirHomeModel
import com.epy.linespotv2.domain.model.profile.JukirModel
import com.epy.linespotv2.presentation.home_jukir.ui_model.ChartBarData
import com.epy.linespotv2.presentation.home_jukir.ui_model.HomeJukirUiModel

@Composable
fun HomeJukirScreen(
    onNavigateToSettings: () -> Unit = {},
    onNavigateToNotification: () -> Unit = {},
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
                state.isLoading && state.homeJukirModel == null -> FullScreenLoading()
                state.homeJukirModel != null && state.uiModel != null -> {
                    HomeScreenContent(
                        state = state,
                        uiModel = state.uiModel!!,
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
                        consumeEffect = { viewModel.consumeEffect() }
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
    uiModel: HomeJukirUiModel,
    onIntent: (HomeJukirIntent) -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToNotification: () -> Unit,
    onNavigateToRiwayat: () -> Unit,
    onNavigateToScanTicket: () -> Unit,
    onNavigateToInputManual: () -> Unit,
    onNavigateToLaporan: () -> Unit,
    onNavigateToBantuan: () -> Unit,
    onNavigateToTopUp: () -> Unit,
    onNavigateToLogin: () -> Unit,
    consumeEffect: () -> Unit
) {
    LaunchedEffect(state.homeJukirEffect) {
        when (state.homeJukirEffect) {
            is HomeJukirEffect.NavigateToTopUp -> { onNavigateToTopUp(); consumeEffect() }
            is HomeJukirEffect.NavigateToSettings -> { onNavigateToSettings(); consumeEffect() }
            is HomeJukirEffect.ShowToast -> { consumeEffect() }
            is HomeJukirEffect.SessionExpired -> { onNavigateToLogin(); consumeEffect() }
            is HomeJukirEffect.NavigateToRiwayat -> { onNavigateToRiwayat(); consumeEffect() }
            is HomeJukirEffect.NavigateToBantuan -> { onNavigateToBantuan(); consumeEffect() }
            is HomeJukirEffect.NavigateToInputManual -> { onNavigateToInputManual(); consumeEffect() }
            is HomeJukirEffect.NavigateToLaporan -> { onNavigateToLaporan(); consumeEffect() }
            is HomeJukirEffect.NavigateToScanTicket -> { onNavigateToScanTicket(); consumeEffect() }
            is HomeJukirEffect.NavigateToNotification -> { onNavigateToNotification(); consumeEffect() }
            null -> Unit
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Setengah background atas Navy
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .clip(RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(DarkBlue, DarkBlue.copy(alpha = 0.9f))
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .systemBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header: Selamat pagi, Petugas Dishub 👋
            JukirHeader(
                title = uiModel.title,
                areaLabel = uiModel.areaLabel,
                zonaValue = uiModel.zonaValue,
                lokasiValue = uiModel.lokasiValue,
                onNotificationClick = { onIntent(HomeJukirIntent.clickNotification(0)) },
                onProfileClick = { onIntent(HomeJukirIntent.clickProfile) }
            )

            // AreaTaskCard dipertahankan
//            AreaTaskCard(
//                areaLabel = uiModel.areaLabel,
//                zonaValue = uiModel.zonaValue,
//                lokasiValue = uiModel.lokasiValue,
//                areaValue = uiModel.areaValue
//            )

            uiModel.financeWarning
                ?.takeIf { it.isNotBlank() }
                ?.let { financeWarning ->
                    WarningCard(message = financeWarning)
                }

            // Statistik Hari Ini
            StatistikHariIniCard(
                pendapatan = uiModel.pendapatan,
                saldo = uiModel.saldo
            )

            // Grafik Pendapatan dengan Axis dan summary performa
//            GrafikPendapatanCard(
//                periodText = uiModel.chartPeriodText,
//                yLabels = uiModel.chartYLabels,
//                bars = uiModel.chartBars
//            )

            // Aksi Cepat / Quick Actions (Dengan penambahan Top Up & Riwayat)
            QuickActionsCard(
                onScanTicketClick = { onIntent(HomeJukirIntent.clickScanTiket) },
                onInputManualClick = { onIntent(HomeJukirIntent.clickInputManual) },
                onLaporanClick = { onIntent(HomeJukirIntent.clickLaporan) },
                onBantuanClick = { onIntent(HomeJukirIntent.clickBantuan) },
                onTopUpClick = { onIntent(HomeJukirIntent.clickTopUp) },
                onRiwayatClick = { onIntent(HomeJukirIntent.clickRiwayat) }
            )

            // SupervisorCard dipertahankan
            SupervisorCard(supervisorName = uiModel.supervisorName)

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun JukirHeader(
    title: String,
    areaLabel: String,
    zonaValue: String,
    lokasiValue: String,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Selamat pagi,",
                color = White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "$title 👮",
                color = White,
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 22.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$areaLabel: $lokasiValue - $zonaValue",
                color = White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.labelSmall
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                color = Color.White.copy(alpha = 0.15f),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.clickable { onNotificationClick() }
            ) {
                Box(
                    modifier = Modifier.size(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.NotificationsNone,
                        contentDescription = "Notifikasi",
                        tint = White
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .clickable { onProfileClick() }
                    .background(Color.White.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
private fun AreaTaskCard(
    areaLabel: String,
    zonaValue: String,
    lokasiValue: String,
    areaValue: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Tugas Aktif",
                color = SmartBlue,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
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
            modifier = Modifier.width(54.dp)
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
private fun StatistikHariIniCard(
    pendapatan: Long,
    saldo: Long
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Statistik Hari Ini",
                color = DarkBlue,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Kolom Pendapatan
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF4F8FF))
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Pendapatan", color = GreyText, style = MaterialTheme.typography.labelSmall)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = pendapatan.toRupiah(),
                            color = SmartBlue,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Kolom Saldo
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF4F8FF))
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Saldo Jukir", color = GreyText, style = MaterialTheme.typography.labelSmall)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = saldo.toRupiah(),
                            color = DarkBlue,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GrafikPendapatanCard(
    periodText: String,
    yLabels: List<String>,
    bars: List<ChartBarData>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Grafik Pendapatan",
                        color = DarkBlue,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = periodText,
                        color = GreyText,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp)
                    )
                }
                Text(
                    text = "Lihat Semua",
                    color = SmartBlue,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    modifier = Modifier.clickable { }
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Chart area dengan layout Axis
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Sumbu Y-axis di sebelah kiri
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(bottom = 18.dp), // Menyisakan ruang untuk label X-axis di sebelah kanan
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.End
                ) {
                    yLabels.forEach { label ->
                        Text(
                            text = label,
                            color = GreyText,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp)
                        )
                    }
                }

                // Sumbu X-axis dan Grafik Batang
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // Area Batang
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        bars.forEach { bar ->
                            Box(
                                modifier = Modifier
                                    .width(12.dp)
                                    .fillMaxHeight(bar.value)
                                    .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                    .background(SmartBlue)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Label X-axis di bawah masing-masing batang
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        bars.forEach { bar ->
                            Text(
                                text = bar.labelX,
                                color = GreyText,
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                                modifier = Modifier.width(16.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickActionsCard(
    onScanTicketClick: () -> Unit,
    onInputManualClick: () -> Unit,
    onLaporanClick: () -> Unit,
    onBantuanClick: () -> Unit,
    onTopUpClick: () -> Unit,
    onRiwayatClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Aksi Cepat",
                color = DarkBlue,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                QuickActionItem(Icons.Default.QrCodeScanner, "Scan QR", onScanTicketClick)
                QuickActionItem(Icons.Default.Edit, "Input Manual", onInputManualClick)
                QuickActionItem(Icons.Default.Description, "Laporan", onLaporanClick)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                QuickActionItem(Icons.Default.AccountBalanceWallet, "Top Up", onTopUpClick)
                QuickActionItem(Icons.Default.History, "Riwayat", onRiwayatClick)
                QuickActionItem(Icons.Default.QuestionMark, "Bantuan", onBantuanClick)
            }
        }
    }
}

@Composable
private fun QuickActionItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.width(76.dp)
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0xFFF4F8FF))
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = SmartBlue, modifier = Modifier.size(22.dp))
        }
        Text(
            text = label,
            color = GreyText,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}

@Composable
private fun SupervisorCard(supervisorName: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F1DE)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
                    text = supervisorName,
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
    val mockModel = JukirHomeModel(
        jukirModel = JukirModel(
            userId = 1L,
            fullName = "Petugas Dishub",
            saldo = 2_750_000L,
            todayIncome = 1_250_000L,
            lokasiName = "Jl. Sudirman",
            zoneName = "Zona Biru",
            areaName = "Area Tugas"
        )
    )
    val mockUiModel = HomeJukirUiModel(
        title = "Petugas Dishub",
        areaLabel = "Area Tugas",
        zonaValue = "Zona Biru",
        lokasiValue = "Jl. Sudirman",
        areaValue = "Area Tugas",
        pendapatan = 1_250_000L,
        saldo = 2_750_000L,
        supervisorName = "Budi Santoso",
        chartPeriodText = "Bulan Ini",
        chartYLabels = listOf("1jt", "400rb", "0"),
        chartBars = listOf(
            ChartBarData(0.4f, "M1"),
            ChartBarData(0.6f, "M2"),
            ChartBarData(0.5f, "M3"),
            ChartBarData(0.7f, "M4"),
            ChartBarData(0.3f, "M5"),
            ChartBarData(0.8f, "M6")
        )
    )

    MaterialTheme {
        HomeScreenContent(
            state = HomeJukirState(
                isLoading = false,
                homeJukirModel = mockModel,
                uiModel = mockUiModel
            ),
            uiModel = mockUiModel,
            onIntent = {},
            onNavigateToSettings = {},
            onNavigateToNotification = {},
            onNavigateToRiwayat = {},
            onNavigateToScanTicket = {},
            onNavigateToInputManual = {},
            onNavigateToLaporan = {},
            onNavigateToBantuan = {},
            onNavigateToTopUp = {},
            onNavigateToLogin = {},
            consumeEffect = {}
        )
    }
}
