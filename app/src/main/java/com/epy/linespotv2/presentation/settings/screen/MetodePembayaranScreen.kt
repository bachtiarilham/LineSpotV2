package com.epy.linespotv2.presentation.settings.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epy.linespotv2.core.ui.theme.DarkBlue
import com.epy.linespotv2.core.ui.theme.GreyText
import com.epy.linespotv2.core.ui.theme.PageBg
import com.epy.linespotv2.core.ui.theme.SmartBlue
import com.epy.linespotv2.core.ui.theme.White

@Composable
fun MetodePembayaranScreen(onBack: () -> Unit = {}) {
    val methods = listOf(
        PaymentRowData("Visa **** 4242", "Exp 12/26", Icons.Default.CreditCard, "Utama"),
        PaymentRowData("Mastercard **** 8888", "Exp 10/25", Icons.Default.CreditCard),
        PaymentRowData("GoPay", "0812 3456 7890", Icons.Default.Payments),
        PaymentRowData("OVO", "0812 3456 7890", Icons.Default.Payments),
        PaymentRowData("DANA", "0812 3456 7890", Icons.Default.Payments)
    )

    Surface(color = PageBg, modifier = Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().systemBarsPadding()) {
            SettingsTopBar3(title = "Metode Pembayaran", onBack = onBack)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SectionTitle3("Metode Tersimpan")

                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        methods.forEachIndexed { index, item ->
                            PaymentRow(item)
                            if (index != methods.lastIndex) DividerLine3()
                        }
                    }
                }

                AddMethodCard()
                InfoCard3("Transaksi aman dengan enkripsi 256-bit. Data kartu Anda terlindungi.")
            }
        }
    }
}

@Composable
private fun PaymentRow(item: PaymentRowData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFF4F7FC)),
            contentAlignment = Alignment.Center
        ) {
            Icon(item.icon, null, tint = SmartBlue, modifier = Modifier.size(18.dp))
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(item.title, color = DarkBlue, style = MaterialTheme.typography.bodyLarge)
                if (item.badge != null) {
                    Spacer(Modifier.width(8.dp))
                    Text(item.badge, color = SmartBlue, style = MaterialTheme.typography.labelSmall)
                }
            }
            Text(item.subtitle, color = GreyText, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun AddMethodCard() {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "+ Tambah Metode Pembayaran",
            color = SmartBlue,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun SectionTitle3(text: String) {
    Text(text, color = DarkBlue, style = MaterialTheme.typography.titleMedium)
}

@Composable
private fun InfoCard3(text: String) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F6FF)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            color = GreyText,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(16.dp)
        )
    }
}

private data class PaymentRowData(
    val title: String,
    val subtitle: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val badge: String? = null
)

@Composable
private fun SettingsTopBar3(title: String, onBack: () -> Unit) {
    Surface(color = DarkBlue, modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = null,
                tint = White,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable { onBack() }
            )
            Text(
                text = title,
                color = White,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun DividerLine3() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp)
            .size(1.dp)
            .background(Color(0xFFE9EDF3))
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MetodePembayaranScreenPreview() {
    MaterialTheme {
        MetodePembayaranScreen()
    }
}
