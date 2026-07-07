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
import androidx.compose.material.icons.filled.NorthEast
import androidx.compose.material.icons.filled.SouthWest
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.epy.linespotv2.core.ui.theme.DarkBlue
import com.epy.linespotv2.core.ui.theme.GreyText
import com.epy.linespotv2.core.ui.theme.PageBg
import com.epy.linespotv2.core.ui.theme.SmartBlue
import com.epy.linespotv2.core.ui.theme.White
import com.epy.linespotv2.core.utils.toRupiah
import com.epy.linespotv2.domain.model.riwayat.RiwayatItem
import com.epy.linespotv2.domain.model.riwayat.RiwayatResponseModel
import com.epy.linespotv2.domain.model.riwayat.RiwayatSection

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
            RiwayatEffect.NavigateToRiwayat -> Unit
            null -> Unit
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> FullScreenLoading()
            state.riwayatResponseModel != null -> {
                val riwayat = state.riwayatResponseModel!!
                if (riwayat.sections.isEmpty()) {
                    EmptyRiwayatScreen(onBack = onBack)
                } else {
                    RiwayatScreenContent(
                        riwayat = riwayat,
                        onItemClick = { viewModel.onIntent(RiwayatIntent.clickRiwayatDetail) },
                        onBack = onBack
                    )
                }
            }

            else -> ErrorScreen(
                message = state.error ?: "Terjadi kesalahan",
                onRetry = { viewModel.onIntent(RiwayatIntent.loadPage) }
            )
        }
    }
}

@Composable
private fun EmptyRiwayatScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBg)
            .systemBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        HeaderBar(title = "Riwayat", onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Tidak ada riwayat transaksi",
                color = DarkBlue,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Riwayat transaksi akan muncul setelah ada aktivitas parkir.",
                color = GreyText,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun RiwayatScreenContent(
    riwayat: RiwayatResponseModel,
    onItemClick: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBg)
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        HeaderBar(title = "Riwayat", onBack = onBack)

        riwayat.sections.forEach { section ->
            DateSection(section.date)
            section.items.forEach { item ->
                RiwayatHistoryCard(
                    item = item,
                    onClick = onItemClick
                )
            }
        }
    }
}

@Composable
private fun DateSection(text: String) {
    Text(text = text, color = DarkBlue, style = MaterialTheme.typography.titleMedium)
}

@Composable
private fun RiwayatHistoryCard(
    item: RiwayatItem,
    onClick: () -> Unit
) {
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
                color = if (item.isEntry) Color(0xFFEAF8EF) else Color(0xFFFFEFEE),
                shape = CircleShape
            ) {
                Icon(
                    imageVector = if (item.isEntry) Icons.Default.SouthWest else Icons.Default.NorthEast,
                    contentDescription = null,
                    tint = if (item.isEntry) Color(0xFF2FA84F) else Color(0xFFE04F4F),
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
                    text = "${item.plateNumber} • ${item.vehicleType}",
                    color = GreyText,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = if (item.isEntry) "Masuk" else "Keluar",
                    color = if (item.isEntry) Color(0xFF2FA84F) else Color(0xFFE04F4F),
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
                    text = item.amount.toRupiah(),
                    color = DarkBlue,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Composable
private fun HeaderBar(title: String, onBack: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.ChevronLeft,
            contentDescription = null,
            tint = DarkBlue,
            modifier = Modifier.clickable { onBack() }
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = title, color = DarkBlue, style = MaterialTheme.typography.titleLarge)
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
            riwayat = RiwayatResponseModel(
                sections = listOf(
                    RiwayatSection(
                        date = "30 Mei 2024",
                        items = listOf(
                            RiwayatItem(
                                code = "TRX-240530-00123",
                                plateNumber = "B 1234 ABC",
                                vehicleType = "Motor",
                                time = "14:45",
                                amount = 5000,
                                isEntry = true
                            ),
                            RiwayatItem(
                                code = "TRX-240530-00122",
                                plateNumber = "B 5678 DEF",
                                vehicleType = "Mobil",
                                time = "14:32",
                                amount = 10000,
                                isEntry = false
                            )
                        )
                    ),
                    RiwayatSection(
                        date = "29 Mei 2024",
                        items = listOf(
                            RiwayatItem(
                                code = "TRX-240529-00119",
                                plateNumber = "B 3344 UVW",
                                vehicleType = "Motor",
                                time = "21:02",
                                amount = 5000,
                                isEntry = true
                            )
                        )
                    )
                )
            ),
            onItemClick = {},
            onBack = {}
        )
    }
}
