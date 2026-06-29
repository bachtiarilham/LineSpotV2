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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun PrivasiScreen(onBack: () -> Unit = {}) {
    val toggles = remember { mutableStateOf(true) }

    Surface(color = PageBg, modifier = Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().systemBarsPadding()) {
            SettingsTopBar4(title = "Privasi", onBack = onBack)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PrivacyCommitmentCard()
                PrivacySettingCard(
                    items = listOf(
                        PrivacyItem("Bagikan Data untuk Promo", "Izinkan kami mengirimkan promo yang relevan untuk Anda."),
                        PrivacyItem("Personalisasi Layanan", "Tingkatkan pengalaman dengan rekomendasi yang dipersonalisasi."),
                        PrivacyItem("Analitik Penggunaan", "Bantu kami meningkatkan aplikasi melalui analitik penggunaan."),
                        PrivacyItem("Lokasi", "Gunakan lokasi untuk menemukan tempat parkir terdekat."),
                        PrivacyItem("Riwayat Transaksi", "Simpan riwayat transaksi Anda di aplikasi.")
                    ),
                    checked = toggles.value,
                    onCheckedChange = { toggles.value = it }
                )
                DownloadDataRow()
                Text(
                    text = "Pelajari lebih lanjut di Kebijakan Privasi kami.",
                    color = GreyText,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun PrivacyCommitmentCard() {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF4F8FF)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFDDEBFF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Lock, null, tint = SmartBlue)
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text("Komitmen Privasi", color = DarkBlue, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Kami berkomitmen untuk melindungi data pribadi Anda sesuai dengan kebijakan privasi kami.",
                    color = GreyText,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun PrivacySettingCard(
    items: List<PrivacyItem>,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            items.forEachIndexed { index, item ->
                PrivacyRow(item, checked, onCheckedChange)
                if (index != items.lastIndex) DividerLine4()
            }
        }
    }
}

@Composable
private fun PrivacyRow(
    item: PrivacyItem,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
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
            Icon(Icons.Default.PrivacyTip, null, tint = SmartBlue, modifier = Modifier.size(18.dp))
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(item.title, color = DarkBlue, style = MaterialTheme.typography.bodyLarge)
            Text(item.subtitle, color = GreyText, style = MaterialTheme.typography.bodySmall)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = White,
                checkedTrackColor = SmartBlue
            )
        )
    }
}

@Composable
private fun DownloadDataRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFF4F7FC)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.ToggleOn, null, tint = SmartBlue, modifier = Modifier.size(18.dp))
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text("Unduh Data Saya", color = DarkBlue, style = MaterialTheme.typography.bodyLarge)
            Text("Dapatkan salinan data pribadi Anda", color = GreyText, style = MaterialTheme.typography.bodySmall)
        }
    }
}

private data class PrivacyItem(val title: String, val subtitle: String)

@Composable
private fun SettingsTopBar4(title: String, onBack: () -> Unit) {
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
private fun DividerLine4() {
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
private fun PrivasiScreenPreview() {
    MaterialTheme {
        PrivasiScreen()
    }
}
