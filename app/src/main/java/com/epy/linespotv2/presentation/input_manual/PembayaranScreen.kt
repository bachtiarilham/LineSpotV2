package com.epy.linespotv2.presentation.input_manual

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalAtm
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
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
import com.epy.linespotv2.core.utils.QrCodeMaker
import com.epy.linespotv2.core.utils.toCountdownText
import com.epy.linespotv2.core.utils.toRupiah
import com.epy.linespotv2.domain.model.IsiQr
import com.epy.linespotv2.domain.model.PembayaranModel
import com.epy.linespotv2.domain.model.PembayaranOption
import com.epy.linespotv2.domain.model.PembayaranOptionType
import com.epy.linespotv2.domain.model.PembayaranQrisSection
import com.epy.linespotv2.domain.model.PembayaranStatusCard
import kotlinx.coroutines.delay

@Composable
fun PembayaranScreen(
    onBack: () -> Unit = {},
    onDetailClick: () -> Unit = {},
    onPrintClick: () -> Unit = {},
    viewModel: InputManualViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pembayaran = state.pembayaranModel ?: return

    LaunchedEffect(Unit) {
        viewModel.onIntent(InputManualIntent.StartPolling)
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.onIntent(InputManualIntent.StopPolling)
        }
    }

    LaunchedEffect(state.inputManualEffect) {
        when (state.inputManualEffect) {
            InputManualEffect.NavigateBack -> {
                onBack()
                viewModel.consumeEffect()
            }
            InputManualEffect.NavigateToPaymentDetail -> {
                onDetailClick()
                viewModel.consumeEffect()
            }
            InputManualEffect.PrintReceipt -> {
                onPrintClick()
                viewModel.consumeEffect()
            }
            InputManualEffect.ShowPaymentSuccess,
            InputManualEffect.ShowPaymentFailed -> {
                viewModel.consumeEffect()
            }
            InputManualEffect.NavigateToPembayaran,
            is InputManualEffect.NavigateToPaymentMethod,
            is InputManualEffect.ShowToast,
            null -> Unit
        }
    }

    PembayaranContent(
        pembayaran = pembayaran,
        onBack = { viewModel.onIntent(InputManualIntent.ClickBack) },
        onDetailClick = { viewModel.onIntent(InputManualIntent.ClickPaymentDetail) },
        onPrintClick = { viewModel.onIntent(InputManualIntent.ClickPrintReceipt) }
    )
}

@Composable
private fun PembayaranContent(
    pembayaran: PembayaranModel,
    onBack: () -> Unit,
    onDetailClick: () -> Unit,
    onPrintClick: () -> Unit
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
        PembayaranTopBar(
            title = pembayaran.title,
            onBack = onBack
        )

        PaymentSuccessCard(statusCard = pembayaran.statusCard)
        PaymentStatusBanner(
            qrContent = pembayaran.qrisSection.qrContent
        )

        TotalPaymentCard(
            total = pembayaran.totalPembayaran.toRupiah(),
            detailLabel = pembayaran.detailLabel,
            onDetailClick = onDetailClick
        )

        QrisPaymentCard(qrisSection = pembayaran.qrisSection)

        Text(
            text = pembayaran.paymentOptionsTitle,
            color = DarkBlue,
            style = MaterialTheme.typography.titleMedium
        )

        PaymentOptionsCard(paymentOptions = pembayaran.paymentOptions)

//        Spacer(modifier = Modifier.height(8.dp))

//        PrintReceiptButton(
//            label = pembayaran.printButtonLabel,
//            onClick = onPrintClick
//        )
    }
}

@Composable
private fun PaymentStatusBanner(
    qrContent: IsiQr
) {
    val (background, title, message) = when {
        qrContent.isPaid -> Triple(
            Color(0xFFF1FBF7),
            "Pembayaran berhasil",
            qrContent.statusMessage.ifBlank { "Pembayaran pelanggan sudah berhasil diverifikasi." }
        )
        qrContent.isExpired -> Triple(
            Color(0xFFFFF4F4),
            "QR kedaluwarsa",
            qrContent.statusMessage.ifBlank { "QRIS telah kedaluwarsa. Silakan buat ulang sesi pembayaran." }
        )
        qrContent.paymentStatus < 0L -> Triple(
            Color(0xFFFFF4F4),
            "Pembayaran gagal",
            qrContent.statusMessage.ifBlank { "Pembayaran gagal atau dibatalkan." }
        )
        qrContent.paymentStatus > 0L -> Triple(
            Color(0xFFF3F7FF),
            "Menunggu pembayaran",
            qrContent.statusMessage.ifBlank { "Status pembayaran sedang diperiksa." }
        )
        else -> Triple(
            Color(0xFFFFFBEB),
            "QR menunggu dipindai",
            qrContent.statusMessage.ifBlank { "Silakan arahkan pelanggan untuk memindai QRIS." }
        )
    }

    Surface(
        color = background,
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                color = DarkBlue,
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = message,
                color = GreyText,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun PembayaranTopBar(
    title: String,
    onBack: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Icon(
            imageVector = Icons.Default.ChevronLeft,
            contentDescription = null,
            tint = DarkBlue,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable { onBack() }
        )
        Text(
            text = title,
            color = DarkBlue,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun PaymentSuccessCard(
    statusCard: PembayaranStatusCard
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (statusCard.isSuccess) Color(0xFFF1FBF7) else Color(0xFFFFF4F4)
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(
                        if (statusCard.isSuccess) Color(0xFFE0F7EC) else Color(0xFFFEE4E2),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = null,
                    tint = if (statusCard.isSuccess) Color(0xFF16A34A) else Color(0xFFD92D20),
                    modifier = Modifier.size(18.dp)
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = statusCard.title,
                    color = DarkBlue,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = statusCard.message,
                    color = GreyText,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun TotalPaymentCard(
    total: String,
    detailLabel: String,
    onDetailClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(Color(0xFFF2F6FF), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CreditCard,
                    contentDescription = null,
                    tint = SmartBlue,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Total Pembayaran",
                    color = GreyText,
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = total,
                    color = SmartBlue,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            Row(
                modifier = Modifier.clickable { onDetailClick() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = detailLabel,
                    color = SmartBlue,
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = SmartBlue,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    }
}

@Composable
private fun QrisPaymentCard(
    qrisSection: PembayaranQrisSection
) {
    var remainingSeconds by remember(qrisSection.countdownSeconds) {
        mutableLongStateOf(qrisSection.countdownSeconds.coerceAtLeast(0L))
    }

    LaunchedEffect(qrisSection.countdownSeconds) {
        remainingSeconds = qrisSection.countdownSeconds.coerceAtLeast(0L)
        while (remainingSeconds > 0) {
            delay(1000)
            remainingSeconds--
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = qrisSection.title,
                color = DarkBlue,
                style = MaterialTheme.typography.titleMedium
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(248.dp)
                    .wrapContentSize(Alignment.Center)
            ) {
                QrisFrame {
                    RealQrCode(
                        isiQr = qrisSection.qrContent,
                        modifier = Modifier.size(172.dp)
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = GreyText,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = qrisSection.masaBerlakuQr,
                    color = GreyText,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Surface(
                color = Color(0xFFF3F7FF),
                shape = RoundedCornerShape(999.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        tint = SmartBlue,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = remainingSeconds.toCountdownText(),
                        color = SmartBlue,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            Text(
                text = qrisSection.alternativeLabel,
                color = GreyText,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun QrisFrame(
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(236.dp)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(horizontal = 6.dp, vertical = 4.dp)
        ) {
            CornerBracket(
                modifier = Modifier.align(Alignment.TopStart),
                top = true,
                start = true
            )
            CornerBracket(
                modifier = Modifier.align(Alignment.TopEnd),
                top = true,
                start = false
            )
            CornerBracket(
                modifier = Modifier.align(Alignment.BottomStart),
                top = false,
                start = true
            )
            CornerBracket(
                modifier = Modifier.align(Alignment.BottomEnd),
                top = false,
                start = false
            )
        }

        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(18.dp),
            contentAlignment = Alignment.Center,
            content = content
        )
    }
}

@Composable
private fun CornerBracket(
    modifier: Modifier,
    top: Boolean,
    start: Boolean
) {
    Box(modifier = modifier.size(22.dp)) {
        Box(
            modifier = Modifier
                .align(if (top) Alignment.TopCenter else Alignment.BottomCenter)
                .width(22.dp)
                .height(2.dp)
                .background(SmartBlue)
        )
        Box(
            modifier = Modifier
                .align(if (start) Alignment.CenterStart else Alignment.CenterEnd)
                .width(2.dp)
                .height(22.dp)
                .background(SmartBlue)
        )
    }
}

@Composable
private fun RealQrCode(
    isiQr: IsiQr,
    modifier: Modifier = Modifier
) {
    val qrBitmap = remember(isiQr) {
        QrCodeMaker.createBitmap(isiQr)
    }

    Image(
        bitmap = qrBitmap.asImageBitmap(),
        contentDescription = "QRIS Pembayaran",
        modifier = modifier.background(White)
    )
}

@Composable
private fun PaymentOptionsCard(
    paymentOptions: List<PembayaranOption>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column {
            paymentOptions.forEachIndexed { index, option ->
                PaymentOptionRow(option = option)
                if (index != paymentOptions.lastIndex) {
                    PaymentOptionDivider()
                }
            }
        }
    }
}

@Composable
private fun PaymentOptionRow(
    option: PembayaranOption
) {
    val (icon, iconBackground) = when (option.type) {
        PembayaranOptionType.TUNAI -> Icons.Default.LocalAtm to Color(0xFFF2F6FF)
        PembayaranOptionType.E_WALLET -> Icons.Default.Wallet to Color(0xFFF5EEFF)
        PembayaranOptionType.TRANSFER_BANK -> Icons.Default.CreditCard to Color(0xFFFFF4E8)
        PembayaranOptionType.METODE_LAIN -> Icons.Outlined.MoreHoriz to Color(0xFFF2F4F7)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(iconBackground, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = SmartBlue,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = option.title,
                color = DarkBlue,
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = option.subtitle,
                color = GreyText,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = GreyText,
            modifier = Modifier.size(12.dp)
        )
    }
}

@Composable
private fun PaymentOptionDivider() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color(0xFFF0F2F5))
    )
}

@Composable
private fun PrintReceiptButton(
    label: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        color = White,
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.4.dp, SmartBlue)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Default.Print,
                contentDescription = null,
                tint = SmartBlue,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                color = SmartBlue,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PembayaranScreenPreview() {
    MaterialTheme {
        PembayaranContent(
            pembayaran = PembayaranModel(
                statusCard = PembayaranStatusCard(
                    title = "Tiket berhasil dibuat!",
                    message = "Silakan tunjukkan QRIS ini kepada pengguna untuk melakukan pembayaran.",
                    isSuccess = true
                ),
                totalPembayaran = 8_000,
                qrisSection = PembayaranQrisSection(
                    title = "Scan & Bayar dengan QRIS",
                    qrContent = IsiQr(
                        sessionId = 24053000123,
                        plat_nomor = "B 1234 ABC",
                        lokasi = "Jl. Sudirman - Zona Biru",
                        waktu_masuk = "30 Mei 2024, 08:15:00",
                        durasi = "2 jam 30 menit",
                        nominal = 8_000
                    ),
                    masaBerlakuQr = "QRIS berlaku selama 15 menit",
                    countdownSeconds = 14 * 60 + 45,
                    alternativeLabel = "atau"
                ),
                paymentOptions = listOf(
                    PembayaranOption(
                        type = PembayaranOptionType.TUNAI,
                        title = "Tunai",
                        subtitle = "Terima pembayaran secara tunai"
                    ),
                    PembayaranOption(
                        type = PembayaranOptionType.E_WALLET,
                        title = "E-Wallet",
                        subtitle = "Pembayaran melalui e-wallet"
                    ),
                    PembayaranOption(
                        type = PembayaranOptionType.TRANSFER_BANK,
                        title = "Transfer Bank",
                        subtitle = "Pembayaran melalui transfer bank"
                    ),
                    PembayaranOption(
                        type = PembayaranOptionType.METODE_LAIN,
                        title = "Metode Lain",
                        subtitle = "Lainnya"
                    )
                )
            ),
            onBack = {},
            onDetailClick = {},
            onPrintClick = {}
        )
    }
}
