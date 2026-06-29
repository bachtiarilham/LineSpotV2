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
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VerifiedUser
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
fun KeamananScreen(onBack: () -> Unit = {}) {
    val biometrics = remember { mutableStateOf(true) }

    Surface(color = PageBg, modifier = Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().systemBarsPadding()) {
            SettingsTopBar2(title = "Keamanan", onBack = onBack)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SecurityStatusCard()
                SectionTitle2("Pengaturan Keamanan")
                SecurityMenuCard(
                    items = listOf(
                        SecurityItem(
                            title = "Ubah PIN",
                            subtitle = "Ganti PIN akun Anda secara berkala",
                            icon = Icons.Default.Security
                        ),
                        SecurityItem(
                            title = "Autentikasi Biometrik",
                            subtitle = "Gunakan sidik jari atau face ID",
                            icon = Icons.Default.Fingerprint,
                            hasSwitch = true
                        ),
                        SecurityItem(
                            title = "Verifikasi 2 Langkah",
                            subtitle = "Tambahkan lapisan keamanan ekstra",
                            icon = Icons.Default.VerifiedUser
                        ),
                        SecurityItem(
                            title = "Daftar Perangkat",
                            subtitle = "Kelola perangkat yang terhubung",
                            icon = Icons.Default.DeviceHub
                        )
                    ),
                    biometricEnabled = biometrics.value,
                    onBiometricChange = { biometrics.value = it }
                )

                LoginActivityCard()
                WarningCard()
            }
        }
    }
}

@Composable
private fun SecurityStatusCard() {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FBF3)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFD7F3DF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Shield, null, tint = Color(0xFF1AA64B))
            }

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text("Akun Anda Aman", color = DarkBlue, style = MaterialTheme.typography.titleMedium)
                Text("Terakhir diperbarui 12 Mei 2025", color = GreyText, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun SecurityMenuCard(
    items: List<SecurityItem>,
    biometricEnabled: Boolean,
    onBiometricChange: (Boolean) -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            items.forEachIndexed { index, item ->
                SecurityRow(
                    item = item,
                    biometricEnabled = biometricEnabled,
                    onBiometricChange = onBiometricChange
                )
                if (index != items.lastIndex) DividerLine2()
            }
        }
    }
}

@Composable
private fun SecurityRow(
    item: SecurityItem,
    biometricEnabled: Boolean,
    onBiometricChange: (Boolean) -> Unit
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
                imageVector = item.icon,
                contentDescription = null,
                tint = SmartBlue,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(Modifier.width(12.dp))

        Column(Modifier.weight(1f)) {
            Text(item.title, color = DarkBlue, style = MaterialTheme.typography.bodyLarge)
            Text(item.subtitle, color = GreyText, style = MaterialTheme.typography.bodySmall)
        }

        if (item.hasSwitch) {
            Switch(
                checked = biometricEnabled,
                onCheckedChange = onBiometricChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = White,
                    checkedTrackColor = SmartBlue
                )
            )
        } else {
            Icon(Icons.Default.ChevronLeft, null, tint = Color.Transparent)
        }
    }
}

@Composable
private fun LoginActivityCard() {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Aktivitas Login Terbaru", color = DarkBlue, style = MaterialTheme.typography.titleMedium)
            Text("Jakarta, Indonesia • 12 Mei 2025, 10:30", color = GreyText, style = MaterialTheme.typography.bodySmall)
            Text("Bandung, Indonesia • 11 Mei 2025, 18:45", color = GreyText, style = MaterialTheme.typography.bodySmall)
            Text("Surabaya, Indonesia • 10 Mei 2025, 09:15", color = GreyText, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun WarningCard() {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF0DD)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Jika ada aktivitas mencurigakan, segera ubah PIN Anda atau hubungi kami.",
            color = Color(0xFFC97700),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
private fun SectionTitle2(text: String) {
    Text(text = text, color = DarkBlue, style = MaterialTheme.typography.titleMedium)
}

private data class SecurityItem(
    val title: String,
    val subtitle: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val hasSwitch: Boolean = false
)

@Composable
private fun SettingsTopBar2(title: String, onBack: () -> Unit) {
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
private fun DividerLine2() {
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
private fun KeamananScreenPreview() {
    MaterialTheme {
        KeamananScreen()
    }
}
