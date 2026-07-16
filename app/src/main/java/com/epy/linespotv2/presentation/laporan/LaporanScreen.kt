package com.epy.linespotv2.presentation.laporan

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.ReceiptLong
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.epy.linespotv2.core.ui.theme.DarkBlue
import com.epy.linespotv2.core.ui.theme.GreyText
import com.epy.linespotv2.core.ui.theme.PageBg
import com.epy.linespotv2.core.ui.theme.SmartBlue
import com.epy.linespotv2.core.ui.theme.White
import com.epy.linespotv2.presentation.laporan.ui_model.LaporanChartItemUiModel
import com.epy.linespotv2.presentation.laporan.ui_model.LaporanScreenUiModel
import com.epy.linespotv2.presentation.laporan.ui_model.LaporanSummaryItemUiModel

@Composable
fun LaporanScreen(
    onBack: () -> Unit = {},
    viewModel: LaporanViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onIntent(LaporanIntent.loadPage)
    }

    LaunchedEffect(state.laporanEffect) {
        when (state.laporanEffect) {
            LaporanEffect.NavigateToFilter -> viewModel.consumeEffect()
            LaporanEffect.NavigateToLaporan -> viewModel.consumeEffect()
            null -> Unit
        }
    }

    Scaffold(containerColor = PageBg) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(PageBg)
        ) {
            when {
                state.isLoading -> FullScreenLoading()
                state.screenUiModel != null -> {
                    LaporanScreenContent(
                        uiModel = state.screenUiModel!!,
                        onBack = onBack
                    )
                }
                else -> {
                    ErrorScreen(
                        message = state.error ?: "Belum ada data laporan",
                        onRetry = { viewModel.onIntent(LaporanIntent.loadPage) }
                    )
                }
            }
        }
    }
}

@Composable
private fun LaporanScreenContent(
    uiModel: LaporanScreenUiModel,
    onBack: () -> Unit
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
        SimpleHeader(
            title = "Laporan",
            onBack = onBack
        )
        TanggalTerpilih(periodeText = uiModel.selectedDateLabel)
        SummaryGrid(
            totalTransaksi = uiModel.totalTransaksiLabel,
            totalPendapatan = uiModel.totalPendapatanLabel,
            rataRataTransaksi = uiModel.rataRataTransaksiLabel
        )
        ChartCard(
            totalPendapatan = uiModel.totalPendapatanLabel,
            chartItems = uiModel.chartItems
        )
        SectionHeader("Ringkasan")
        SummaryCard(summaryItems = uiModel.summaryItems)
        DownloadButton()
    }
}

@Composable
private fun TanggalTerpilih(periodeText: String) {
    Surface(
        color = White,
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Tanggal Terpilih",
                color = GreyText,
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = periodeText,
                color = DarkBlue,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Composable
private fun SummaryGrid(
    totalTransaksi: String,
    totalPendapatan: String,
    rataRataTransaksi: String
) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        SummaryMetric(
            title = "Total Transaksi",
            value = totalTransaksi,
            icon = Icons.Default.ReceiptLong,
            modifier = Modifier.weight(1f)
        )
        SummaryMetric(
            title = "Total Pendapatan",
            value = totalPendapatan,
            icon = Icons.Default.Description,
            modifier = Modifier.weight(1f)
        )
        SummaryMetric(
            title = "Rata-rata",
            value = rataRataTransaksi,
            icon = Icons.Default.MonetizationOn,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun SummaryMetric(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = SmartBlue,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = title,
                color = GreyText,
                style = MaterialTheme.typography.labelSmall
            )
            Text(
                text = value,
                color = DarkBlue,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Composable
private fun ChartCard(
    totalPendapatan: String,
    chartItems: List<LaporanChartItemUiModel>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Pendapatan",
                    color = DarkBlue,
                    style = MaterialTheme.typography.titleLarge
                )
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = GreyText,
                    modifier = Modifier.size(18.dp)
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Total Pendapatan",
                    color = GreyText,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = totalPendapatan,
                    color = DarkBlue,
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            LaporanBarChart(chartItems = chartItems)
        }
    }
}

@Composable
private fun LaporanBarChart(chartItems: List<LaporanChartItemUiModel>) {
    if (chartItems.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Belum ada data grafik",
                color = GreyText,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        return
    }

    val maxAmount = chartItems.maxOf { it.amount }.coerceAtLeast(1L)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        chartItems.forEach { item ->
            VerticalBarItem(
                label = item.tanggal,
                valueLabel = item.valueLabel,
                ratio = (item.amount.toFloat() / maxAmount.toFloat()).coerceIn(0f, 1f),
                isHighlighted = item.isHighlighted,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun VerticalBarItem(
    label: String,
    valueLabel: String,
    ratio: Float,
    isHighlighted: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = valueLabel,
            color = if (isHighlighted) SmartBlue else DarkBlue,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .width(24.dp)
                .height(160.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((160f * ratio.coerceAtLeast(0.08f)).dp)
                    .background(
                        color = if (isHighlighted) SmartBlue else Color(0xFFBFD9FF),
                        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                    )
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            color = GreyText,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
private fun SummaryCard(summaryItems: List<LaporanSummaryItemUiModel>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (summaryItems.isEmpty()) {
                Text(
                    text = "Belum ada ringkasan transaksi",
                    color = GreyText,
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                summaryItems.forEach { summary ->
                    SummaryLine(
                        label = summary.label,
                        value = summary.value
                    )
                }
            }
        }
    }
}

@Composable
private fun SummaryLine(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = DarkBlue,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = value,
            color = DarkBlue,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun DownloadButton() {
    Surface(
        color = White,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFFD4E2FF))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Download,
                contentDescription = null,
                tint = SmartBlue,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Unduh Laporan",
                color = SmartBlue,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        color = DarkBlue,
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
private fun SimpleHeader(
    title: String,
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ChevronLeft,
            contentDescription = null,
            tint = DarkBlue,
            modifier = Modifier
                .size(24.dp)
                .clickable { onBack() }
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            color = DarkBlue,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
    }
}

@Composable
private fun FullScreenLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = SmartBlue)
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
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Terjadi kendala",
            color = DarkBlue,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            color = GreyText,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
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
private fun LaporanScreenPreview() {
    MaterialTheme {
        LaporanScreenContent(
            uiModel = LaporanScreenUiModel(
                selectedDateLabel = "01 Mei 2024 - 07 Mei 2024",
                totalTransaksiLabel = "243",
                totalPendapatanLabel = "Rp 1.250.000",
                rataRataTransaksiLabel = "Rp 5.144",
                summaryItems = listOf(
                    LaporanSummaryItemUiModel("Tunai", "150"),
                    LaporanSummaryItemUiModel("QRIS", "93"),
                    LaporanSummaryItemUiModel("Motor", "180"),
                    LaporanSummaryItemUiModel("Mobil", "63")
                ),
                chartItems = listOf(
                    LaporanChartItemUiModel("01", "350 rb", 350_000),
                    LaporanChartItemUiModel("02", "480 rb", 480_000),
                    LaporanChartItemUiModel("03", "760 rb", 760_000),
                    LaporanChartItemUiModel("04", "620 rb", 620_000),
                    LaporanChartItemUiModel("05", "980 rb", 980_000, true),
                    LaporanChartItemUiModel("06", "680 rb", 680_000),
                    LaporanChartItemUiModel("07", "440 rb", 440_000)
                )
            ),
            onBack = {}
        )
    }
}
