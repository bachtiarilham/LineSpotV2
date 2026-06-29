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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
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
fun BantuanFaqScreen(onBack: () -> Unit = {}) {
    val topics = listOf(
        "Akun & Profil" to "Kelola akun, ubah data profil",
        "Pembayaran & Transaksi" to "Metode pembayaran, gagal bayar, refund",
        "Membership" to "Paket, benefit, perpanjangan",
        "Parkir" to "Tarif, lokasi, dan durasi parkir",
        "Promo & Kode Promo" to "Cara menggunakan promo",
        "Lainnya" to "Topik bantuan lainnya"
    )
    val faqs = listOf(
        "Bagaimana cara berlangganan membership?",
        "Bagaimana cara membatalkan membership?",
        "Metode pembayaran apa saja yang tersedia?",
        "Bagaimana cara mendapatkan kode promo?"
    )

    Surface(color = PageBg, modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            SettingsTopBar(title = "Bantuan & FAQ", onBack = onBack)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SearchBarPlaceholder("Cari pertanyaan...")
                SectionTitle("Topik Bantuan")
                TopicsCard(topics)
                SectionTitle("FAQ Populer")
                FaqCard(faqs)
                HelpBanner()
            }
        }
    }
}

@Composable
private fun SearchBarPlaceholder(text: String) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = GreyText
            )
            Spacer(Modifier.width(10.dp))
            Text(text, color = GreyText, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        color = DarkBlue,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(horizontal = 4.dp)
    )
}

@Composable
private fun TopicsCard(items: List<Pair<String, String>>) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            items.forEachIndexed { index, item ->
                SettingsListRow(
                    title = item.first,
                    subtitle = item.second,
                    iconTint = SmartBlue
                )
                if (index != items.lastIndex) DividerLine()
            }
        }
    }
}

@Composable
private fun FaqCard(items: List<String>) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            items.forEachIndexed { index, item ->
                SettingsListRow(
                    title = item,
                    subtitle = "",
                    showSubtitle = false,
                    iconTint = SmartBlue
                )
                if (index != items.lastIndex) DividerLine()
            }
        }
    }
}

@Composable
private fun HelpBanner() {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F6FF)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFDDEBFF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = SmartBlue
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    text = "Masih butuh bantuan?",
                    color = DarkBlue,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Hubungi Customer Service kami",
                    color = GreyText,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun SettingsListRow(
    title: String,
    subtitle: String,
    showSubtitle: Boolean = true,
    iconTint: Color = SmartBlue
) {
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
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(Modifier.width(12.dp))

        Column(Modifier.weight(1f)) {
            Text(title, color = DarkBlue, style = MaterialTheme.typography.bodyLarge)
            if (showSubtitle && subtitle.isNotBlank()) {
                Text(subtitle, color = GreyText, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun DividerLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color(0xFFE9EDF3))
    )
}

@Composable
private fun SettingsTopBar(title: String, onBack: () -> Unit) {
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BantuanFaqScreenPreview() {
    MaterialTheme {
        BantuanFaqScreen()
    }
}
