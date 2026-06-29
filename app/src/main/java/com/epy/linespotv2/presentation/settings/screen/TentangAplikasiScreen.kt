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
import androidx.compose.material.icons.filled.LocalParking
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
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
fun TentangAplikasiScreen(onBack: () -> Unit = {}) {
    val items = listOf(
        AboutItem("Apa yang Baru", "Lihat fitur terbaru di versi ini", Icons.Default.Info),
        AboutItem("Beri Rating", "Bantu kami dengan memberikan rating", Icons.Default.Star),
        AboutItem("Bagikan Aplikasi", "Bagikan Parkee ke teman Anda", Icons.Default.Share),
        AboutItem("Kebijakan Privasi", "Pelajari bagaimana kami melindungi data Anda", Icons.Default.Shield),
        AboutItem("Syarat & Ketentuan", "Baca syarat dan ketentuan penggunaan", Icons.Default.RateReview)
    )

    Surface(color = PageBg, modifier = Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().systemBarsPadding()) {
            SettingsTopBar7(title = "Tentang Aplikasi", onBack = onBack)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AboutHeaderCard()
                AboutMenuCard(items)
                FooterText7()
            }
        }
    }
}

@Composable
private fun AboutHeaderCard() {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(SmartBlue),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.LocalParking, null, tint = White, modifier = Modifier.size(40.dp))
            }
            Text("Parkee", color = DarkBlue, style = MaterialTheme.typography.headlineSmall)
            Text("Version 1.0.0", color = GreyText, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "Solusi parkir cerdas untuk mobilitas yang lebih mudah.",
                color = GreyText,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun AboutMenuCard(items: List<AboutItem>) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            items.forEachIndexed { index, item ->
                AboutRow7(item)
                if (index != items.lastIndex) DividerLine7()
            }
        }
    }
}

@Composable
private fun AboutRow7(item: AboutItem) {
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
            Icon(item.icon, null, tint = SmartBlue, modifier = Modifier.size(18.dp))
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(item.title, color = DarkBlue, style = MaterialTheme.typography.bodyLarge)
            Text(item.subtitle, color = GreyText, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun FooterText7() {
    Text(
        text = "© 2025 Parkee\nSemua hak dilindungi.",
        color = GreyText,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.fillMaxWidth()
    )
}

private data class AboutItem(
    val title: String,
    val subtitle: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
private fun SettingsTopBar7(title: String, onBack: () -> Unit) {
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
private fun DividerLine7() {
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
private fun TentangAplikasiScreenPreview() {
    MaterialTheme {
        TentangAplikasiScreen()
    }
}
