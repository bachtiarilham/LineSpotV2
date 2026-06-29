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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Wc
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
fun ProfilScreen(onBack: () -> Unit = {}) {
    Surface(color = PageBg, modifier = Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().systemBarsPadding()) {
            SettingsTopBar5(title = "Profil Saya", onBack = onBack)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ProfileHeaderCard()
                SectionTitle5("Informasi Pribadi")
                InfoCard5(
                    items = listOf(
                        ProfileField("Nama Lengkap", "John Doe", Icons.Default.Person),
                        ProfileField("Nomor Handphone", "0812 3456 7890", Icons.Default.Phone),
                        ProfileField("Email", "john.doe@email.com", Icons.Default.MailOutline),
                        ProfileField("Tanggal Lahir", "12 Mei 1995", Icons.Default.CalendarMonth),
                        ProfileField("Jenis Kelamin", "Laki-laki", Icons.Default.Wc)
                    )
                )
                SectionTitle5("Alamat")
                ClickableRow5("Alamat Utama", "Jl. Sudirman No. 123, Jakarta Pusat, DKI Jakarta 10220")
                SectionTitle5("Dokumen")
                DocRow5("KTP", "Verifikasi selesai")
                DocRow5("SIM", "Verifikasi selesai")
                LogoutButton5()
            }
        }
    }
}

@Composable
private fun ProfileHeaderCard() {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(SmartBlue),
                contentAlignment = Alignment.Center
            ) {
                Text("J", color = White, style = MaterialTheme.typography.titleLarge)
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text("John Doe", color = DarkBlue, style = MaterialTheme.typography.titleMedium)
                Text("john.doe@email.com", color = GreyText, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun InfoCard5(items: List<ProfileField>) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            items.forEachIndexed { index, item ->
                ProfileFieldRow(item)
                if (index != items.lastIndex) DividerLine5()
            }
        }
    }
}

@Composable
private fun ProfileFieldRow(item: ProfileField) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(item.icon, null, tint = SmartBlue)
        Spacer(Modifier.width(12.dp))
        Text(item.label, color = DarkBlue, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
        Text(item.value, color = GreyText, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun ClickableRow5(title: String, subtitle: String) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        modifier = Modifier.fillMaxWidth()
    ) {
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
                Icon(Icons.Default.Person, null, tint = SmartBlue, modifier = Modifier.size(18.dp))
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(title, color = DarkBlue, style = MaterialTheme.typography.bodyLarge)
                Text(subtitle, color = GreyText, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun DocRow5(title: String, subtitle: String) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
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
                Icon(Icons.Default.Person, null, tint = SmartBlue, modifier = Modifier.size(18.dp))
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(title, color = DarkBlue, style = MaterialTheme.typography.bodyLarge)
                Text(subtitle, color = GreyText, style = MaterialTheme.typography.bodySmall)
            }
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFD8F3E0)),
                contentAlignment = Alignment.Center
            ) {
                Text("✓", color = Color(0xFF1AA64B), style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
private fun LogoutButton5() {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Keluar Akun",
            color = Color(0xFFE84C4C),
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun SectionTitle5(text: String) {
    Text(text, color = DarkBlue, style = MaterialTheme.typography.titleMedium)
}

private data class ProfileField(
    val label: String,
    val value: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
private fun SettingsTopBar5(title: String, onBack: () -> Unit) {
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
private fun DividerLine5() {
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
private fun ProfilScreenPreview() {
    MaterialTheme {
        ProfilScreen()
    }
}
