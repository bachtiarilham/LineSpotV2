package com.epy.linespotv2.presentation.riwayat

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
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.NorthEast
import androidx.compose.material.icons.filled.SouthWest
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.epy.linespotv2.core.ui.theme.DarkBlue
import com.epy.linespotv2.core.ui.theme.GreyText
import com.epy.linespotv2.core.ui.theme.PageBg
import com.epy.linespotv2.core.ui.theme.SmartBlue
import com.epy.linespotv2.core.ui.theme.White
import com.epy.linespotv2.presentation.riwayat.ui_model.RiwayatItemUiModel
import com.epy.linespotv2.presentation.riwayat.ui_model.RiwayatScreenUiModel
import com.epy.linespotv2.presentation.riwayat.ui_model.RiwayatSectionUiModel

@Composable
fun RiwayatScreen(
    onBack: () -> Unit = {},
    onNavigateToDetail: () -> Unit = {},
    viewModel: RiwayatViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.riwayatEffect) {
        when (state.riwayatEffect) {
            RiwayatEffect.NavigateToDetail -> {
                onNavigateToDetail()
                viewModel.consumeEffect()
            }
            RiwayatEffect.NavigateToFilter -> Unit
            RiwayatEffect.NavigateToRiwayat -> viewModel.consumeEffect()
            null -> Unit
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> FullScreenLoading()
            state.screenUiModel != null -> {
                RiwayatScreenContent(
                    uiModel = state.screenUiModel!!,
                    selectedTab = state.selectedTab,
                    onTabSelected = { viewModel.onIntent(RiwayatIntent.changeTab(it)) },
                    onItemClick = { code, isParking -> 
                        viewModel.onIntent(RiwayatIntent.clickRiwayatDetail(code, isParking)) 
                    },
                    onFilterClick = {
                        // Triggers transition to filter screen
                        state.riwayatEffect // Placeholder untuk navigasi
                    },
                    onBack = onBack
                )
            }
            else -> ErrorScreen(
                message = state.error ?: "Terjadi kesalahan",
                onRetry = { viewModel.onIntent(RiwayatIntent.loadPage) }
            )
        }
    }
}

@Composable
fun RiwayatScreenContent(
    uiModel: RiwayatScreenUiModel,
    selectedTab: RiwayatTab,
    onTabSelected: (RiwayatTab) -> Unit,
    onItemClick: (String, Boolean) -> Unit,
    onFilterClick: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBg)
            .systemBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        HeaderBar(
            title = "Riwayat",
            onBack = onBack,
            onFilterClick = onFilterClick
        )

        // Tab Layout (Parkir & Keuangan)
        TabRow(
            selectedTabIndex = selectedTab.ordinal,
            containerColor = Color.Transparent,
            contentColor = SmartBlue,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab.ordinal]),
                    color = SmartBlue
                )
            },
            divider = {}
        ) {
            Tab(
                selected = selectedTab == RiwayatTab.PARKIR,
                onClick = { onTabSelected(RiwayatTab.PARKIR) },
                text = {
                    Text(
                        text = "Parkir",
                        fontWeight = if (selectedTab == RiwayatTab.PARKIR) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 14.sp
                    )
                }
            )
            Tab(
                selected = selectedTab == RiwayatTab.KEUANGAN,
                onClick = { onTabSelected(RiwayatTab.KEUANGAN) },
                text = {
                    Text(
                        text = "Keuangan",
                        fontWeight = if (selectedTab == RiwayatTab.KEUANGAN) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 14.sp
                    )
                }
            )
        }

        // Penyaringan Item berdasarkan Tab
        val filteredSections = uiModel.sections.map { section ->
            val items = section.items.filter { item ->
                if (selectedTab == RiwayatTab.PARKIR) {
                    // Parkir: ditandai dengan ada data Plat Nomor
                    item.plateNumber.isNotBlank() && item.plateNumber != "-"
                } else {
                    // Keuangan: data Plat Nomor kosong / tidak relevan
                    item.plateNumber.isBlank() || item.plateNumber == "-"
                }
            }
            section.copy(items = items)
        }.filter { it.items.isNotEmpty() }

        if (filteredSections.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    Text(
                        text = "Tidak ada riwayat",
                        color = DarkBlue,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Belum ada aktivitas ${if (selectedTab == RiwayatTab.PARKIR) "parkir" else "keuangan"} untuk saat ini.",
                        color = GreyText,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                filteredSections.forEach { section ->
                    DateSection(section.date)
                    section.items.forEach { item ->
                        RiwayatHistoryCard(
                            item = item,
                            selectedTab = selectedTab,
                            onClick = { 
                                val isParking = selectedTab == RiwayatTab.PARKIR
                                onItemClick(item.code, isParking) 
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DateSection(text: String) {
    Text(
        text = text,
        color = DarkBlue,
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
private fun RiwayatHistoryCard(
    item: RiwayatItemUiModel,
    selectedTab: RiwayatTab,
    onClick: () -> Unit
) {
    val isTopUp = item.statusLabel.contains("Top Up", ignoreCase = true) || item.statusLabel.contains("Masuk", ignoreCase = true)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = if (isTopUp) Color(0xFFEAF8EF) else Color(0xFFFCEAEA),
                shape = CircleShape
            ) {
                Icon(
                    imageVector = if (isTopUp) Icons.Default.SouthWest else Icons.Default.NorthEast,
                    contentDescription = null,
                    tint = if (isTopUp) Color(0xFF2FA84F) else Color(0xFFE04F4F),
                    modifier = Modifier.padding(9.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.code,
                    color = DarkBlue,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = if (selectedTab == RiwayatTab.PARKIR) "${item.plateNumber} • ${item.vehicleType}" else item.vehicleType,
                    color = GreyText,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = item.statusLabel,
                    color = if (isTopUp) Color(0xFF2FA84F) else Color(0xFFE04F4F),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = item.time,
                    color = GreyText,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = item.amountLabel,
                    color = DarkBlue,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Composable
private fun HeaderBar(
    title: String,
    onBack: () -> Unit,
    onFilterClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = null,
                tint = DarkBlue,
                modifier = Modifier.clickable { onBack() }
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                color = DarkBlue,
                style = MaterialTheme.typography.titleLarge
            )
        }

        IconButton(
            onClick = onFilterClick,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = "Filter",
                tint = DarkBlue
            )
        }
    }
}

@Composable
private fun FullScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBg),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = DarkBlue)
    }
}

@Composable
private fun ErrorScreen(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBg)
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
            Text(
                text = "Coba Lagi",
                color = SmartBlue,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RiwayatScreenPreview() {
    MaterialTheme {
        RiwayatScreenContent(
            uiModel = RiwayatScreenUiModel(
                sections = listOf(
                    RiwayatSectionUiModel(
                        date = "30 Mei 2024",
                        items = listOf(
                            RiwayatItemUiModel(
                                code = "TRX-240530-00123",
                                plateNumber = "B 1234 ABC",
                                vehicleType = "Motor",
                                time = "14:45",
                                amountLabel = "Rp 5.000",
                                statusLabel = "Masuk"
                            ),
                            RiwayatItemUiModel(
                                code = "Top Up Saldo",
                                plateNumber = "",
                                vehicleType = "Transfer Bank",
                                time = "14:32",
                                amountLabel = "Rp 100.000",
                                statusLabel = "Sukses"
                            )
                        )
                    )
                )
            ),
            selectedTab = RiwayatTab.PARKIR,
            onTabSelected = {},
            onItemClick = { _, _ -> },
            onBack = {}
        )
    }
}
