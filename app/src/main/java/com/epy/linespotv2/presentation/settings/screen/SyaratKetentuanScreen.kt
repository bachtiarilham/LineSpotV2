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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Shield
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
fun SyaratKetentuanScreen(onBack: () -> Unit = {}) {
    val items = listOf(
        "1. Ketentuan Umum",
        "2. Layanan Aplikasi",
        "3. Akun Pengguna",
        "4. Pembayaran & Transaksi",
        "5. Membership",
        "6. Kode Promo",
        "7. Privasi & Data",
        "8. Batasan Tanggung Jawab",
        "9. Perubahan Ketentuan",
        "10. Hukum yang Berlaku"
    )

    Surface(color = PageBg, modifier = Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().systemBarsPadding()) {
            SettingsTopBar6(title = "Syarat & Ketentuan", onBack = onBack)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                RingkasanCard6()
                TermsCard6(items)
                AgreeBox6()
            }
        }
    }
}

@Composable
private fun RingkasanCard6() {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Ringkasan", color = DarkBlue, style = MaterialTheme.typography.titleMedium)
            Text("Terakhir diperbarui: 1 Mei 2025", color = GreyText, style = MaterialTheme.typography.bodySmall)
            Text(
                "Dengan menggunakan aplikasi ini, Anda menyetujui syarat dan ketentuan berikut.",
                color = GreyText,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun TermsCard6(items: List<String>) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            items.forEachIndexed { index, item ->
                TermRow6(item)
                if (index != items.lastIndex) DividerLine6()
            }
        }
    }
}

@Composable
private fun TermRow6(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 14.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFF4F7FC)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Info, null, tint = SmartBlue, modifier = Modifier.size(18.dp))
        }
        Spacer(Modifier.width(12.dp))
        Text(text, color = DarkBlue, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun AgreeBox6() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Shield, null, tint = SmartBlue)
        Spacer(Modifier.width(10.dp))
        Text(
            text = "Saya telah membaca dan menyetujui Syarat & Ketentuan",
            color = GreyText,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun SettingsTopBar6(title: String, onBack: () -> Unit) {
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
private fun DividerLine6() {
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
private fun SyaratKetentuanScreenPreview() {
    MaterialTheme {
        SyaratKetentuanScreen()
    }
}
