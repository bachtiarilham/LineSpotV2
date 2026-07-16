package com.epy.linespotv2.presentation.input_manual

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalAtm
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material.icons.outlined.CheckCircle
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
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
import com.epy.linespotv2.core.utils.QrCodeMaker
import com.epy.linespotv2.domain.model.parking.PostParkingRespModel
import com.epy.linespotv2.presentation.input_manual.ui_model.PembayaranOptionUiModel
import com.epy.linespotv2.presentation.input_manual.ui_model.PembayaranUiModel

@Composable
fun PembayaranScreen(
    onBack: () -> Unit = {},
    onDetailClick: () -> Unit = {},
    onPrintClick: () -> Unit = {},
    viewModel: InputManualViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pembayaran = state.postParkingRespModel ?: return
    val uiModel = state.pembayaranUiModel ?: return

    LaunchedEffect(Unit) {
        viewModel.onIntent(InputManualIntent.StartPolling)
    }

    DisposableEffect(Unit) {
        onDispose { viewModel.onIntent(InputManualIntent.StopPolling) }
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
            InputManualEffect.ShowPaymentFailed,
            InputManualEffect.NavigateToPembayaran,
            is InputManualEffect.NavigateToPaymentMethod,
            is InputManualEffect.ShowToast,
            null -> Unit
        }
    }

    PembayaranContent(
        uiModel = uiModel,
        pembayaran = pembayaran,
        onBack = { viewModel.onIntent(InputManualIntent.ClickBack) },
        onDetailClick = { viewModel.onIntent(InputManualIntent.ClickPaymentDetail) },
        onPrintClick = { viewModel.onIntent(InputManualIntent.ClickPrintReceipt) }
    )
}

@Composable
private fun PembayaranContent(
    uiModel: PembayaranUiModel,
    pembayaran: PostParkingRespModel,
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
        PembayaranTopBar(uiModel.title, onBack)
        PaymentSuccessCard(uiModel)
        TotalPaymentCard(uiModel.totalPembayaranLabel, uiModel.detailLabel, onDetailClick)
        QrisPaymentCard(uiModel = uiModel, pembayaran = pembayaran)
        Text(
            text = uiModel.paymentOptionsTitle,
            color = DarkBlue,
            style = MaterialTheme.typography.titleMedium
        )
        PaymentOptionsCard(uiModel.paymentOptions)
        PrintReceiptButton(onClick = onPrintClick)
    }
}

@Composable
private fun PembayaranTopBar(
    title: String,
    onBack: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
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
private fun PaymentSuccessCard(uiModel: PembayaranUiModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (uiModel.isSuccess) Color(0xFFF1FBF7) else Color(0xFFFFF4F4)
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
                        if (uiModel.isSuccess) Color(0xFFE0F7EC) else Color(0xFFFEE4E2),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = null,
                    tint = if (uiModel.isSuccess) Color(0xFF16A34A) else Color(0xFFD92D20),
                    modifier = Modifier.size(18.dp)
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(uiModel.statusTitle, color = DarkBlue, style = MaterialTheme.typography.labelLarge)
                Text(uiModel.statusMessage, color = GreyText, style = MaterialTheme.typography.bodySmall)
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
            modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(36.dp).background(Color(0xFFF2F6FF), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.CreditCard, null, tint = SmartBlue, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Total Pembayaran", color = GreyText, style = MaterialTheme.typography.labelMedium)
                Text(total, color = SmartBlue, style = MaterialTheme.typography.headlineSmall)
            }
            Row(
                modifier = Modifier.clickable { onDetailClick() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(detailLabel, color = SmartBlue, style = MaterialTheme.typography.labelLarge)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Default.ChevronRight, null, tint = SmartBlue, modifier = Modifier.size(12.dp))
            }
        }
    }
}

@Composable
private fun QrisPaymentCard(
    uiModel: PembayaranUiModel,
    pembayaran: PostParkingRespModel
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = uiModel.qrTitle,
                color = DarkBlue,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    bitmap = QrCodeMaker.createBitmap(pembayaran).asImageBitmap(),
                    contentDescription = "QRIS Pembayaran",
                    modifier = Modifier
                        .size(180.dp)
                        .background(White)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Default.Info, null, tint = GreyText, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = uiModel.qrExpiredLabel,
                    color = GreyText,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun PaymentOptionsCard(paymentOptions: List<PembayaranOptionUiModel>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column {
            paymentOptions.forEachIndexed { index, option ->
                PaymentOptionRow(option)
                if (index != paymentOptions.lastIndex) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color(0xFFF0F2F5))
                    )
                }
            }
        }
    }
}

@Composable
private fun PaymentOptionRow(option: PembayaranOptionUiModel) {
    val icon = when {
        option.title.contains("Tunai", true) -> Icons.Default.LocalAtm
        option.title.contains("Transfer", true) -> Icons.Default.CreditCard
        else -> Icons.Default.Wallet
    }

    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(36.dp).background(Color(0xFFF2F6FF), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = SmartBlue, modifier = Modifier.size(18.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(option.title, color = DarkBlue, style = MaterialTheme.typography.labelLarge)
            Text(option.subtitle, color = GreyText, style = MaterialTheme.typography.bodySmall)
        }
        Icon(Icons.Default.ChevronRight, null, tint = GreyText, modifier = Modifier.size(12.dp))
    }
}

@Composable
private fun PrintReceiptButton(onClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        color = White,
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.4.dp, SmartBlue)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Print, null, tint = SmartBlue, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Cetak Struk", color = SmartBlue, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PembayaranScreenPreview() {
    MaterialTheme {
        PembayaranContent(
            uiModel = PembayaranUiModel(
                totalPembayaranLabel = "Rp 8.000",
                qrExpiredLabel = "2024-05-30 14:45:00",
                paymentOptions = listOf(
                    PembayaranOptionUiModel("Tunai", "Terima pembayaran secara tunai"),
                    PembayaranOptionUiModel("Transfer Bank", "Gunakan transfer bank bila tersedia")
                )
            ),
            pembayaran = PostParkingRespModel(qrString = "SESSION-001"),
            onBack = {},
            onDetailClick = {},
            onPrintClick = {}
        )
    }
}
