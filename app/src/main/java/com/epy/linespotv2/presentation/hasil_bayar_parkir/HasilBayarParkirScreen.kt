package com.epy.linespotv2.presentation.hasil_bayar_parkir

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDownward
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.QrCode2
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epy.linespotv2.core.ui.theme.CardBorder
import com.epy.linespotv2.core.ui.theme.DarkBlue
import com.epy.linespotv2.core.ui.theme.GreyText
import com.epy.linespotv2.core.ui.theme.PageBg
import com.epy.linespotv2.core.ui.theme.SmartBlue
import com.epy.linespotv2.core.ui.theme.SoftBlue
import com.epy.linespotv2.core.ui.theme.SoftBlueText
import com.epy.linespotv2.core.ui.theme.SoftDivider
import com.epy.linespotv2.core.ui.theme.SoftGreen
import com.epy.linespotv2.core.ui.theme.SoftGreenText
import com.epy.linespotv2.core.ui.theme.Success
import com.epy.linespotv2.core.ui.theme.White
import com.epy.linespotv2.domain.model.payment.PostPaymentParkingRespModel


@Composable
fun HasilBayarParkirScreen(
    onBack: () -> Unit = {},
    onShare: () -> Unit = {},
    onDownloadReceipt: () -> Unit = {},
    onBackToHome: () -> Unit = {},
    model: PostPaymentParkingRespModel = PostPaymentParkingRespModel()
) {
    val receipt = remember { model }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBg)
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        ReceiptTopBar(
            title = receipt.title,
            onBack = onBack,
            onShare = onShare
        )

        SuccessHeroSection(
            title = receipt.successTitle,
            description = receipt.successDescription
        )

        ReceiptDetailCard(receipt = receipt)

        ThankYouCard(model = receipt)

        SecondaryActionButton(
            label = receipt.downloadLabel,
            icon = Icons.Outlined.Download,
            onClick = onDownloadReceipt
        )

        PrimaryActionButton(
            label = receipt.backToHomeLabel,
            icon = Icons.Outlined.Home,
            onClick = onBackToHome
        )
    }
}

@Composable
private fun ReceiptTopBar(
    title: String,
    onBack: () -> Unit,
    onShare: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.ArrowDownward,
            contentDescription = null,
            tint = DarkBlue,
            modifier = Modifier
                .size(24.dp)
                .clickable { onBack() }
        )
        Text(
            text = title,
            color = DarkBlue,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Icon(
            imageVector = Icons.Outlined.Share,
            contentDescription = null,
            tint = DarkBlue,
            modifier = Modifier
                .size(24.dp)
                .clickable { onShare() }
        )
    }
}

@Composable
private fun SuccessHeroSection(
    title: String,
    description: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        SuccessBadge()
        Text(
            text = title,
            color = Success,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = description,
            color = GreyText,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SuccessBadge() {
    Box(
        modifier = Modifier.size(152.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(110.dp)
                .background(Color(0x1A4CAF90), CircleShape)
        )
        Box(
            modifier = Modifier
                .size(84.dp)
                .background(Color(0x334CAF90), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "✓",
                color = Success,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun ReceiptDetailCard(receipt: PostPaymentParkingRespModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        border = BorderStroke(1.dp, CardBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(SoftBlue, RoundedCornerShape(14.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.QrCode2,
                        contentDescription = null,
                        tint = SmartBlue,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Total Pembayaran",
                        color = GreyText,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = receipt.totalAmount,
                        color = DarkBlue,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                StatusChip(label = receipt.paymentStatus)
            }

            HorizontalDivider(color = SoftDivider)

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                receipt.details.forEach { item ->
                    ReceiptDetailRow(
                        label = item.label,
                        value = item.value
                    )
                }
            }

            HorizontalDivider(color = SoftDivider)

//            Text(
//                text = "Tunjukkan struk ini kepada petugas jika diperlukan.",
//                color = GreyText,
//                style = MaterialTheme.typography.bodyMedium,
//                modifier = Modifier.fillMaxWidth(),
//                textAlign = TextAlign.Center
//            )

//            QrReceiptBlock(content = receipt.referenceNumber)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.VerifiedUser,
                    contentDescription = null,
                    tint = SmartBlue,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = receipt.verificationMessage,
                    color = SoftBlueText,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun StatusChip(label: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SoftGreen)
            .padding(horizontal = 14.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = SoftGreenText,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun ReceiptDetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            color = GreyText,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = value,
            color = DarkBlue,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ThankYouCard(model: PostPaymentParkingRespModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        border = BorderStroke(1.dp, CardBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(SoftBlue, RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "✿",
                    color = SmartBlue,
                    fontSize = 22.sp
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = model.thankYouTitle,
                    color = DarkBlue,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = model.thankYouDescription,
                    color = GreyText,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Icon(
                imageVector = Icons.Outlined.Share,
                contentDescription = null,
                tint = GreyText,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun SecondaryActionButton(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(White)
            .border(1.5.dp, SmartBlue, RoundedCornerShape(18.dp))
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 18.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = SmartBlue,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            color = SmartBlue,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun PrimaryActionButton(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(SmartBlue)
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 18.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = White,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            color = White,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HasilBayarParkirScreenPreview() {
    MaterialTheme {
        HasilBayarParkirScreen()
    }
}
