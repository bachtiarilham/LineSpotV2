package com.epy.linespotv2.presentation.laporan

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FileCopy
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.North
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
import com.epy.linespotv2.core.ui.theme.Success
import com.epy.linespotv2.core.ui.theme.DarkBlue
import com.epy.linespotv2.core.ui.theme.GreyText
import com.epy.linespotv2.core.ui.theme.PageBg
import com.epy.linespotv2.core.ui.theme.SmartBlue
import com.epy.linespotv2.core.ui.theme.White
import com.epy.linespotv2.core.utils.toRupiah
import com.epy.linespotv2.domain.model.laporan.LaporanChartBar
import com.epy.linespotv2.domain.model.laporan.LaporanDateRange
import com.epy.linespotv2.domain.model.laporan.LaporanModel
import com.epy.linespotv2.domain.model.laporan.LaporanPaymentSummary
import com.epy.linespotv2.domain.model.laporan.LaporanRecentTransaction
import com.epy.linespotv2.domain.model.laporan.LaporanSummary

@Composable
fun LaporanScreen(
    onBack: () -> Unit = {},
    onOpenFilter: () -> Unit = {},
    viewModel: LaporanViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onIntent(LaporanIntent.loadPage)
    }

    LaunchedEffect(state.laporanEffect) {
        when (state.laporanEffect) {
            LaporanEffect.NavigateToFilter -> {
                onOpenFilter()
                viewModel.consumeEffect()
            }
            LaporanEffect.NavigateToLaporan -> viewModel.consumeEffect()
            null -> Unit
        }
    }

    Scaffold(
        containerColor = PageBg
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(PageBg)
        ) {
            when {
                state.isLoading -> FullScreenLoading()
                state.laporanModel != null -> {
                    LaporanScreenContent(
                        laporan = state.laporanModel!!,
                        onBack = onBack,
                        onOpenFilter = onOpenFilter
                    )
                }
                else -> ErrorScreen(
                    message = state.error ?: "Terjadi kesalahan",
                    onRetry = { viewModel.onIntent(LaporanIntent.loadPage) }
                )
            }
        }
    }
}

@Composable
private fun LaporanScreenContent(
    laporan: LaporanModel,
    onBack: () -> Unit,
    onOpenFilter: () -> Unit
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
            onBack = onBack,
            actionText = "Filter",
            onActionClick = onOpenFilter
        )
        TanggalTerpilih(
            periode = laporan.periode,
            fallbackText = laporan.tanggalTerpilih
        )
        SummaryGrid(summary = laporan.summary)
        ChartCard(
            totalPendapatan = laporan.summary.totalPendapatan,
            chartBars = laporan.chartBars
        )
        SectionHeader("Ringkasan")
        SummaryCard(paymentSummaries = laporan.paymentSummaries)
        DownloadButton()
    }
}

@Composable
private fun TanggalTerpilih(
    periode: LaporanDateRange,
    fallbackText: String,
) {
    val periodeText = when {
        periode.label.isNotBlank() -> periode.label
        periode.startDate.isNotBlank() && periode.endDate.isNotBlank() -> "${periode.startDate} - ${periode.endDate}"
        fallbackText.isNotBlank() -> fallbackText
        else -> "-"
    }

    Surface(
        color = White,
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
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
}

@Composable
private fun SummaryGrid(summary: LaporanSummary) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        SummaryMetric("Total Transaksi", summary.totalTransaksi.toString(), Icons.Default.FileCopy, modifier = Modifier.weight(1f))
        SummaryMetric("Total Pendapatan", summary.totalPendapatan.toRupiah(), Icons.Default.Description, modifier = Modifier.weight(1f))
        SummaryMetric("Rata-rata Transaksi", summary.rataRataTransaksi.toRupiah(), Icons.Default.MonetizationOn, modifier = Modifier.weight(1f))
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
            Icon(icon, null, tint = SmartBlue, modifier = Modifier.size(18.dp))
            Text(text = title, color = GreyText, style = MaterialTheme.typography.labelSmall)
            Text(text = value, color = DarkBlue, style = MaterialTheme.typography.titleSmall)
        }
    }
}

@Composable
private fun ChartCard(
    totalPendapatan: Long,
    chartBars: List<LaporanChartBar>
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

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
//                        text = "Total Pendapatan",
                        text = "Total Pendapatan",
                        color = GreyText,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = totalPendapatan.toRupiah(),
                        color = DarkBlue,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.North,
                            contentDescription = null,
                            tint = Success,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "12%",
                            color = Success,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Text(
                        text = "vs bulan lalu",
                        color = GreyText,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            LaporanBarChart(chartBars = chartBars)
        }
    }
}

@Composable
private fun LaporanBarChart(chartBars: List<LaporanChartBar>) {
    if (chartBars.isEmpty()) {
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

    val maxAmount = chartBars.maxOf { it.amount }.coerceAtLeast(1L)

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .border(width = 1.dp, color = Color(0xFFE7EDF6), shape = RoundedCornerShape(18.dp))
                    .padding(horizontal = 12.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                chartBars.forEach { bar ->
                    VerticalBarItem(
                        label = bar.periodLabel.ifBlank { bar.tanggal },
                        valueLabel = bar.amount.toChartAmountLabel(),
                        ratio = (bar.amount.toFloat() / maxAmount.toFloat()).coerceIn(0f, 1f),
                        isHighlighted = bar.amount == maxAmount,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
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
                .height(160.dp)
                .width(22.dp),
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
private fun SummaryCard(paymentSummaries: List<LaporanPaymentSummary>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            paymentSummaries.forEach { summary ->
                SummaryLine(
                    label = summary.label,
                    value = summary.amount.toRupiah(),
                    percent = "${summary.percentage}%"
                )
            }
        }
    }
}

@Composable
private fun SummaryLine(label: String, value: String, percent: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = label, color = DarkBlue, style = MaterialTheme.typography.bodyMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(text = value, color = DarkBlue, style = MaterialTheme.typography.bodyMedium)
            Text(text = percent, color = GreyText, style = MaterialTheme.typography.bodySmall)
        }
    }
}

private fun Int.toCountdownText(): String {
    val minutes = this / 60
    val seconds = this % 60
    return String.format("%02d:%02d", minutes, seconds)
}

@Composable
private fun RecentTransactions(
    transactions: List<LaporanRecentTransaction>
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        transactions.forEach { transaction ->
            TransactionRow(
                code = transaction.code,
                time = transaction.time,
                total = transaction.total.toRupiah(),
                tag = transaction.paymentTag
            )
        }
    }
}

@Composable
private fun TransactionRow(code: String, time: String, total: String, tag: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = code, color = DarkBlue, style = MaterialTheme.typography.bodyMedium)
                Text(text = time, color = GreyText, style = MaterialTheme.typography.bodySmall)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = total, color = DarkBlue, style = MaterialTheme.typography.bodyMedium)
                Text(text = tag, color = SmartBlue, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun DownloadButton() {
    Surface(
        color = White,
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD4E2FF))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Download, null, tint = SmartBlue, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Unduh Laporan", color = SmartBlue, style = MaterialTheme.typography.titleSmall)
        }
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(text = text, color = DarkBlue, style = MaterialTheme.typography.titleMedium)
}

@Composable
private fun SectionTitleLine(title: String, action: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = title, color = DarkBlue, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = action, color = SmartBlue, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun SimpleHeader(
    title: String,
    onBack: () -> Unit,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
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
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f)
        )
        if (actionText != null && onActionClick != null) {
            TextButton(onClick = onActionClick) {
                Text(
                    text = actionText,
                    color = SmartBlue,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
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

private fun Long.toChartAmountLabel(): String {
    if (this <= 0L) return "0"
    return when {
        this >= 1_000_000L -> "${this / 1_000_000} jt"
        this >= 1_000L -> "${this / 1_000} rb"
        else -> this.toString()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LaporanScreenPreview() {
    MaterialTheme {
        LaporanScreenContent(
            laporan = LaporanModel(
                tanggalTerpilih = "30 Mei 2024",
                periode = LaporanDateRange(
                    startDate = "01 Mei 2024",
                    endDate = "07 Mei 2024",
                    label = "01 Mei 2024 - 07 Mei 2024"
                ),
                summary = LaporanSummary(
                    totalTransaksi = 243,
                    totalPendapatan = 1_250_000,
                    rataRataTransaksi = 5_150
                ),
                chartBars = listOf(
                    LaporanChartBar(tanggal = "01", amount = 350_000, periodLabel = "01"),
                    LaporanChartBar(tanggal = "02", amount = 480_000, periodLabel = "02"),
                    LaporanChartBar(tanggal = "03", amount = 760_000, periodLabel = "03"),
                    LaporanChartBar(tanggal = "04", amount = 620_000, periodLabel = "04"),
                    LaporanChartBar(tanggal = "05", amount = 980_000, periodLabel = "05"),
                    LaporanChartBar(tanggal = "06", amount = 680_000, periodLabel = "06"),
                    LaporanChartBar(tanggal = "07", amount = 440_000, periodLabel = "07"),
                ),
                paymentSummaries = listOf(
                    LaporanPaymentSummary(label = "Tunai", amount = 850_000, percentage = 68),
                    LaporanPaymentSummary(label = "QRIS / E-Wallet", amount = 350_000, percentage = 28),
                    LaporanPaymentSummary(label = "Lainnya", amount = 50_000, percentage = 4)
                ),
                recentTransactions = listOf(
                    LaporanRecentTransaction(code = "TRX-240530-00123", time = "14:45", total = 5_000, paymentTag = "Tunai"),
                    LaporanRecentTransaction(code = "TRX-240530-00122", time = "14:32", total = 3_000, paymentTag = "QRIS")
                )
            ),
            onBack = {},
            onOpenFilter = {}
        )
    }
}
