package com.epy.linespotv2.presentation.riwayat

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.NorthEast
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SouthWest
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.epy.linespotv2.presentation.riwayat.ui_model.DetilRiwayatInfoItemUiModel
import com.epy.linespotv2.presentation.riwayat.ui_model.DetilScreenUiModel

@Composable
fun DetilScreen(
    onBack: () -> Unit = {},
    onPrint: () -> Unit = {},
    onShare: () -> Unit = {},
    onDownloadPdf: () -> Unit = {},
    uiModel: DetilScreenUiModel = detilRiwayatPreviewModel()
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
        StatusCard(uiModel = uiModel)
        TicketInfoCard(items = uiModel.ticketInfoItems)
        PaymentMethodCard(
            methodLabel = uiModel.paymentMethodLabel,
            amountLabel = uiModel.paymentAmountLabel
        )
//        PetugasInfoCard(
//            officerName = uiModel.officerName,
//            officerRole = uiModel.officerRole,
//            officerIdLabel = uiModel.officerIdLabel,
//            officerIdValue = uiModel.officerIdValue
//        )
        NotesCard(
            noteLabel = uiModel.noteLabel,
            noteValue = uiModel.noteValue
        )

        DownloadPdfButton(onClick = onDownloadPdf)

        
        // Menampilkan tombol PDF secara dinamis jika diaktifkan di uiModel
//        if (uiModel.showDownloadPdfButton) {
//            DownloadPdfButton(onClick = onDownloadPdf)
//        }

//        ActionButtons(
//            onPrint = onPrint,
//            onShare = onShare
//        )
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
            tint = DarkBlue,
            modifier = Modifier.clickable { onBack() }
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
private fun StatusCard(uiModel: DetilScreenUiModel) {
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
                color = if (uiModel.isEntry) Color(0xFFEAF8EF) else Color(0xFFFFEFEE),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = if (uiModel.isEntry) Icons.Default.SouthWest else Icons.Default.NorthEast,
                        contentDescription = null,
                        tint = if (uiModel.isEntry) Color(0xFF2FA84F) else Color(0xFFE04F4F)
                    )
                    Text(
                        text = uiModel.statusLabel,
                        color = if (uiModel.isEntry) Color(0xFF2FA84F) else Color(0xFFE04F4F),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.size(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = uiModel.ticketCode,
                        color = DarkBlue,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Surface(
                        color = Color(0xFFEAF8EF),
                        shape = RoundedCornerShape(999.dp)
                    ) {
                        Text(
                            text = uiModel.statusChipLabel,
                            color = Color(0xFF2FA84F),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }

                Spacer(modifier = Modifier.size(6.dp))

                Text(
                    text = uiModel.statusDateTime,
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
                            text = uiModel.vehicleInitial,
                            color = SmartBlue,
                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                    Spacer(modifier = Modifier.size(6.dp))
                    Text(
                        text = uiModel.vehicleType,
                        color = SmartBlue,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = uiModel.totalAmountLabel,
                        color = DarkBlue,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    }
}

@Composable
private fun TicketInfoCard(items: List<DetilRiwayatInfoItemUiModel>) {
    DetailCard(title = "Informasi Transaksi") {
        items.forEachIndexed { index, item ->
            if (index > 0 && item.emphasized) {
                HorizontalDivider(
                    color = Color(0xFFE7EBF2),
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
            InfoRow(
                label = item.label,
                value = item.value,
                emphasized = item.emphasized
            )
        }
    }
}

@Composable
private fun PaymentMethodCard(
    methodLabel: String,
    amountLabel: String
) {
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
                        text = methodLabel.take(1).ifBlank { "-" },
                        color = Color(0xFF2FA84F),
                        modifier = Modifier.padding(horizontal = 9.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = methodLabel,
                    color = DarkBlue,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = amountLabel,
                color = DarkBlue,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun PetugasInfoCard(
    officerName: String,
    officerRole: String,
    officerIdLabel: String,
    officerIdValue: String
) {
    DetailCard(title = "Informasi Petugas / Provider") {
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
                    text = officerName,
                    color = DarkBlue,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = officerRole,
                    color = GreyText,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = officerIdLabel,
                    color = GreyText,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = officerIdValue,
                    color = DarkBlue,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun NotesCard(
    noteLabel: String,
    noteValue: String
) {
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
                text = noteLabel,
                color = GreyText,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = noteValue,
                color = GreyText,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun DownloadPdfButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF22C55E)), // Hijau sukses
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.PictureAsPdf,
                contentDescription = null,
                tint = White,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "Unduh Bukti PDF",
                color = White,
                style = MaterialTheme.typography.titleSmall
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
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = title,
                color = DarkBlue,
                style = MaterialTheme.typography.titleSmall
            )
            content()
        }
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
            style = if (emphasized) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium,
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
                    .clickable { onPrint() }
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
                    .clickable { onShare() }
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

private fun detilRiwayatPreviewModel(): DetilScreenUiModel {
    return DetilScreenUiModel(
        ticketCode = "TRX-240530-00123",
        statusChipLabel = "Selesai",
        statusLabel = "Masuk",
        statusDateTime = "30 Mei 2024, 14:45:32",
        vehicleInitial = "P",
        vehicleType = "Motor",
        totalAmountLabel = "Rp 5.000",
        isEntry = true,
        ticketInfoItems = listOf(
            DetilRiwayatInfoItemUiModel("Nomor Tiket", "TRX-240530-00123"),
            DetilRiwayatInfoItemUiModel("Waktu Masuk", "30 Mei 2024, 08:15"),
            DetilRiwayatInfoItemUiModel("Area Parkir", "Jl. Sudirman - Zona Biru"),
            DetilRiwayatInfoItemUiModel("Jenis Kendaraan", "Motor"),
            DetilRiwayatInfoItemUiModel("Durasi Parkir", "6 jam 30 menit"),
            DetilRiwayatInfoItemUiModel("Tarif", "Rp 2.000 / jam"),
            DetilRiwayatInfoItemUiModel("Total Tarif", "Rp 5.000", emphasized = true)
        ),
        paymentMethodLabel = "Tunai",
        paymentAmountLabel = "Rp 5.000",
        noteLabel = "Tidak ada catatan",
        noteValue = "-",
        showDownloadPdfButton = true
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DetilScreenPreview() {
    MaterialTheme {
        DetilScreen()
    }
}
