package com.epy.linespotv2.presentation.bantuan

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epy.linespotv2.core.ui.theme.DarkBlue
import com.epy.linespotv2.core.ui.theme.GreyText
import com.epy.linespotv2.core.ui.theme.PageBg
import com.epy.linespotv2.core.ui.theme.SmartBlue
import com.epy.linespotv2.core.ui.theme.White

@Composable
fun BantuanScreen(onBack: () -> Unit = {}) {
    val topics = listOf(
        "Akun & Profil" to "Kelola akun, ubah data profil",
        "Pembayaran & Transaksi" to "Metode pembayaran, gagal bayar, refund",
        "Tiket & Parkir" to "Scan tiket, input manual, tarif",
        "Laporan" to "Cara melihat dan mengunduh laporan",
        "Aplikasi" to "Penggunaan aplikasi dan fitur"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBg)
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Header("Bantuan", onBack)
        Section("Topik Bantuan")
        topics.forEach { TopicRow(it.first, it.second) }
        ChatHelpCard()
        CallHelpCard()
        SectionLine("FAQ Populer", "Lihat Semua")
        TopicRow("Bagaimana cara scan tiket?", "")
        TopicRow("Bagaimana jika tiket rusak/hilang?", "")
    }
}

@Composable
private fun TopicRow(title: String, subtitle: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = Color(0xFFF2F6FF),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = SmartBlue,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, color = DarkBlue, style = MaterialTheme.typography.bodyLarge)
                if (subtitle.isNotBlank()) {
                    Text(text = subtitle, color = GreyText, style = MaterialTheme.typography.bodySmall)
                }
            }
            Icon(Icons.Default.ChevronRight, null, tint = GreyText)
        }
    }
}

@Composable
private fun ChatHelpCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F6FF))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.ChatBubbleOutline, null, tint = SmartBlue)
                Spacer(modifier = Modifier.width(10.dp))
                Text("Chat dengan Pengawas", color = DarkBlue, style = MaterialTheme.typography.titleSmall)
            }
            Text(
                text = "Sampaikan kendala Anda kepada pengawas secara langsung.",
                color = GreyText,
                style = MaterialTheme.typography.bodySmall
            )
            Text(text = "Mulai Chat", color = SmartBlue, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun CallHelpCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF6E8))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Phone, null, tint = Color(0xFFF39B1C))
                Spacer(modifier = Modifier.width(10.dp))
                Text("Hubungi Call Center", color = DarkBlue, style = MaterialTheme.typography.titleSmall)
            }
            Text(
                text = "Layanan tersedia 24 jam setiap hari.",
                color = GreyText,
                style = MaterialTheme.typography.bodySmall
            )
            Text(text = "0800-1234-5678", color = SmartBlue, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun Section(text: String) {
    Text(text = text, color = DarkBlue, style = MaterialTheme.typography.titleMedium)
}

@Composable
private fun SectionLine(title: String, action: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = title, color = DarkBlue, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = action, color = SmartBlue, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun Header(title: String, onBack: () -> Unit) {
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BantuanScreenPreview() {
    MaterialTheme {
        BantuanScreen()
    }
}
