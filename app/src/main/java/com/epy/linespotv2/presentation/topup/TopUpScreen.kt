package com.epy.linespotv2.presentation.topup

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epy.linespotv2.core.ui.theme.Black
import com.epy.linespotv2.core.ui.theme.DarkBlue
import com.epy.linespotv2.core.ui.theme.GreyText
import com.epy.linespotv2.core.ui.theme.PageBg
import com.epy.linespotv2.core.ui.theme.SmartBlue
import com.epy.linespotv2.core.ui.theme.White

@Composable
fun TopUpScreen(
    onBack: () -> Unit = {},
    onChooseNominal: (Long) -> Unit = {},
    onChooseMethod: (String) -> Unit = {}
) {
    val nominalOptions = listOf(50_000L, 100_000L, 200_000L)
    val methods = listOf(
        PaymentMethodUi(
            title = "Virtual Account",
            subtitle = "BCA, Mandiri, BRI, BNI",
            icon = Icons.Default.CreditCard
        ),
        PaymentMethodUi(
            title = "E-Wallet",
            subtitle = "GoPay, OVO, DANA, ShopeePay",
            icon = Icons.Default.CreditCard
        ),
        PaymentMethodUi(
            title = "QRIS",
            subtitle = "Scan QR Code",
            icon = Icons.Default.Verified
        ),
        PaymentMethodUi(
            title = "Kartu Debit / Kredit",
            subtitle = "Visa, Mastercard, JCB",
            icon = Icons.Default.CreditCard
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBg)
            .systemBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF071B44), DarkBlue)
                    )
                )
                .padding(horizontal = 20.dp, vertical = 18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = "Back",
                    tint = White,
                    modifier = Modifier.clickable{onBack()}
                )

                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Top Up Saldo",
                    color = White,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(54.dp))
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PageBg)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Text(
                text = "Nominal",
                color = Black,
                style = MaterialTheme.typography.titleMedium
            )

            NominalGrid(
                options = nominalOptions,
                selected = 100_000L,
                onSelect = onChooseNominal
            )

            NominalInputPlaceholder()

            Text(
                text = "Metode Pembayaran",
                color = Black,
                style = MaterialTheme.typography.titleMedium

            )

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                methods.forEach { method ->
                    PaymentMethodCard(
                        item = method,
                        onClick = { onChooseMethod(method.title) }
                    )
                }
            }

            SecurityFooter()
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun NominalGrid(
    options: List<Long>,
    selected: Long,
    onSelect: (Long) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            NominalChip(
                amount = options[0],
                selected = selected == options[0],
                modifier = Modifier.weight(1f),
                onClick = { onSelect(options[0]) }
            )
            NominalChip(
                amount = options[1],
                selected = selected == options[1],
                modifier = Modifier.weight(1f),
                onClick = { onSelect(options[1]) }
            )
            NominalChip(
                amount = options[2],
                selected = selected == options[2],
                modifier = Modifier.weight(1f),
                onClick = { onSelect(options[2]) }
            )
        }
    }
}

@Composable
private fun NominalChip(
    amount: Long,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val background = if (selected) SmartBlue else White
    val contentColor = if (selected) White else Black
    val borderColor = if (selected) SmartBlue else Color(0xFFD9DEE8)

    Box(
        modifier = modifier
            .height(40.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(background)
            .border(1.dp, borderColor, RoundedCornerShape(18.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = amount.toRupiah(),
            color = contentColor,
            style = MaterialTheme.typography.titleMedium
//            fontSize = 18.sp,
//            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun NominalInputPlaceholder() {
    Surface(
        color = White,
        shape = RoundedCornerShape(18.dp),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .border(1.dp, Color(0xFFD9DEE8), RoundedCornerShape(18.dp))
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 24.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "Input Nominal",
                color = GreyText,
                style = MaterialTheme.typography.bodyLarge
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Medium
            )
        }
    }
}

private data class PaymentMethodUi(
    val title: String,
    val subtitle: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
private fun PaymentMethodCard(
    item: PaymentMethodUi,
    onClick: () -> Unit
) {
    Surface(
        color = White,
        shape = RoundedCornerShape(18.dp),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFD9DEE8), RoundedCornerShape(18.dp))
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color(0xFFF4F6FA)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = DarkBlue,
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    color = Black,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.subtitle,
                    color = GreyText,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Pilih",
                tint = DarkBlue,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
private fun SecurityFooter() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Top up aman & terpercaya",
            color = GreyText,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .size(26.dp)
                .clip(CircleShape)
                .background(SmartBlue.copy(alpha = 0.14f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = "Security",
                tint = SmartBlue,
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .size(26.dp)
                .clip(RoundedCornerShape(7.dp))
                .background(SmartBlue),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Verified,
                contentDescription = "Verified",
                tint = White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

private fun Long.toRupiah(): String {
    val formatter = java.text.NumberFormat.getNumberInstance(java.util.Locale.forLanguageTag("id-ID"))
    return "Rp ${formatter.format(this)}"
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TopUpScreenPreview() {
    MaterialTheme {
        TopUpScreen()
    }
}
