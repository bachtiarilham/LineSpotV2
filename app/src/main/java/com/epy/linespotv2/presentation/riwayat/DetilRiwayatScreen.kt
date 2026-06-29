package com.epy.linespotv2.presentation.riwayat

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SouthWest
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epy.linespotv2.core.ui.theme.DarkBlue
import com.epy.linespotv2.core.ui.theme.GreyText
import com.epy.linespotv2.core.ui.theme.PageBg
import com.epy.linespotv2.core.ui.theme.SmartBlue
import com.epy.linespotv2.core.ui.theme.White

@Composable
fun DetilRiwayatScreen(
    onBack: () -> Unit = {},
    onPrint: () -> Unit = {},
    onShare: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBg)
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        DetailHeader(onBack = onBack)
        StatusCard()
        TicketInfoCard()
        PaymentMethodCard()
        PetugasInfoCard()
        NotesCard()
        ActionButtons(
            onPrint = onPrint,
            onShare = onShare
        )
    }
}

@Composable
private fun DetailHeader(onBack: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ChevronLeft,
            contentDescription = null,
            tint = DarkBlue
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = "Detail Riwayat",
            color = DarkBlue,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun StatusCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = Color(0xFFEAF8EF),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.SouthWest,
                        contentDescription = null,
                        tint = Color(0xFF2FA84F)
                    )
                    Text(
                        text = "Masuk",
                        color = Color(0xFF2FA84F),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.size(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "TRX-240530-00123",
                        color = DarkBlue,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Surface(
                        color = Color(0xFFEAF8EF),
                        shape = RoundedCornerShape(999.dp)
                    ) {
                        Text(
                            text = "Selesai",
                            color = Color(0xFF2FA84F),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }

                Spacer(modifier = Modifier.size(6.dp))

                Text(
                    text = "30 Mei 2024, 14:45:32",
                    color = GreyText,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.size(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = Color(0xFFEFF5FF),
                        shape = RoundedCornerShape(999.dp)
                    ) {
                        Text(
                            text = "P",
                            color = SmartBlue,
                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                    Spacer(modifier = Modifier.size(6.dp))
                    Text(
                        text = "Motor",
                        color = SmartBlue,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "Rp 5.000",
                        color = DarkBlue,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    }
}

@Composable
private fun TicketInfoCard() {
    DetailCard(title = "Informasi Tiket") {
        InfoRow("Nomor Tiket", "TRX-240530-00123")
        InfoRow("Waktu Masuk", "30 Mei 2024, 08:15")
        InfoRow("Area Parkir", "Jl. Sudirman - Zona Biru")
        InfoRow("Jenis Kendaraan", "Motor")
        InfoRow("Durasi Parkir", "6 jam 30 menit")
        InfoRow("Tarif", "Rp 2.000 / jam")
        HorizontalDivider(
            color = Color(0xFFE7EBF2),
            modifier = Modifier.padding(vertical = 10.dp)
        )
        InfoRow("Total Tarif", "Rp 5.000", emphasized = true)
    }
}

@Composable
private fun PaymentMethodCard() {
    DetailCard(title = "Metode Pembayaran") {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = Color(0xFFEAF8EF),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "T",
                        color = Color(0xFF2FA84F),
                        modifier = Modifier.padding(horizontal = 9.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "Tunai",
                    color = DarkBlue,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Rp 5.000",
                color = DarkBlue,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun PetugasInfoCard() {
    DetailCard(title = "Informasi Petugas") {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = Color(0xFFEFF5FF),
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = SmartBlue,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.size(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Budi Santoso",
                    color = DarkBlue,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Juru Parkir",
                    color = GreyText,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "ID Petugas",
                    color = GreyText,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "JP-000123",
                    color = DarkBlue,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun NotesCard() {
    DetailCard(title = "Catatan") {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = Color(0xFFF4F7FC),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Description,
                    contentDescription = null,
                    tint = SmartBlue,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = "Tidak ada catatan",
                color = GreyText,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "-",
                color = GreyText,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun DetailCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            content = content
        )
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    emphasized: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            color = GreyText,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            color = DarkBlue,
            style = if (emphasized) {
                MaterialTheme.typography.titleMedium
            } else {
                MaterialTheme.typography.bodyMedium
            },
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ActionButtons(
    onPrint: () -> Unit,
    onShare: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Surface(
            color = White,
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color(0xFFD4E2FF)),
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Print,
                    contentDescription = null,
                    tint = SmartBlue,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Cetak Struk",
                    color = SmartBlue,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }

        Surface(
            color = SmartBlue,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Bagikan",
                    color = White,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DetilRiwayatScreenPreview() {
    MaterialTheme {
        DetilRiwayatScreen()
    }
}
