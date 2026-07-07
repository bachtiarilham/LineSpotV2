package com.epy.linespotv2.presentation.subscribe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epy.linespotv2.core.ui.theme.DarkBlue
import com.epy.linespotv2.core.ui.theme.GreyText
import com.epy.linespotv2.core.ui.theme.PageBg
import com.epy.linespotv2.core.ui.theme.SmartBlue
import com.epy.linespotv2.core.ui.theme.White
import com.epy.linespotv2.domain.model.subscription.PackageCard
import com.epy.linespotv2.domain.model.subscription.StatusCard
import com.epy.linespotv2.domain.model.subscription.SubscribeResponseModel

@Composable
fun BenefitScreenPopUp(
    model: SubscribeResponseModel? = null,
    onClose: () -> Unit = {},
    onAcknowledge: () -> Unit = {}
) {
    val selectedPackage = model?.packageCard?.firstOrNull()
        ?: PackageCard(
            namaPaket = "Premium Gold",
            harga = 590_000L,
            masaBerlaku = "bulan",
            jumlahDiskon = 13,
            deskripsi = "3 jam gratis / bulan",
            benefit = listOf(
                "Diskon parkir hingga 25%",
                "Gratis parkir 3 jam / bulan",
                "Promo eksklusif",
                "Prioritas customer service",
                "Akses promo eksklusif",
                "Riwayat parkir lebih lengkap"
            )
        )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF000000).copy(alpha = 0.35f))
            .padding(top = 12.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            colors = CardDefaults.cardColors(containerColor = PageBg),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier
                            .size(width = 46.dp, height = 4.dp)
                            .clip(RoundedCornerShape(999.dp))
                            .background(Color(0xFFC9CEDA))
                    )
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Surface(
                        color = White,
                        shape = CircleShape,
                        modifier = Modifier.clickable { onClose() }
                    ) {
                        Box(
                            modifier = Modifier.size(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Tutup",
                                tint = GreyText
                            )
                        }
                    }
                }

                Text(
                    text = "Benefit Membership",
                    color = DarkBlue,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )

                StatusMiniCard(
                    model = model,
                )

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    selectedPackage.benefit.forEach { item -> BenefitListRow(text = item) }
                }

                Surface(
                    color = SmartBlue,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.clickable { onAcknowledge() }
                ) {
                    Text(
                        text = "Mengerti",
                        color = White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 14.dp),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusMiniCard(
    model: SubscribeResponseModel? = null,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFC98B1E)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        listOf(Color(0xFFC98B1E), Color(0xFFD9A63A), Color(0xFF9B690F))
                    )
                )
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.14f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.size(12.dp))
                Column(modifier = Modifier.weight(1f)) {
//                    model?.packageCard?.firstOrNull()?.let {
//                        Text(
//                            text = it.namaPaket,
//                            color = White,
//                            style = MaterialTheme.typography.titleMedium
//                        )
//                        Text(
//                            text = it.masaBerlaku,
//                            color = White.copy(alpha = 0.9f),
//                            style = MaterialTheme.typography.bodySmall
//                        )
//                    }
                    model?.statusCard?.let{
                        Text(
                            text = it.paketAktif,
                            color = White,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = it.kadaluarsa,
                            color = White.copy(alpha = 0.9f),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                }
            }
        }
    }
}

@Composable
private fun BenefitListRow(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(SmartBlue.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = SmartBlue,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.size(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = text,
                color = DarkBlue,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BenefitScreenTopUpPreview() {
    val model = SubscribeResponseModel(
        statusCard = StatusCard(
            paketAktif = "Premium Gold",
            kadaluarsa = "20 Mei 2025",
            benefit = "Nikmati benefit parkir premium"
        ),
        packageCard = listOf(
            PackageCard(
                namaPaket = "Premium Gold",
                harga = 590_000L,
                masaBerlaku = "bulan",
                jumlahDiskon = 13,
                deskripsi = "3 jam gratis / bulan",
                benefit = listOf(
                    "Diskon parkir hingga 25%",
                    "Gratis parkir 3 jam / bulan",
                    "Promo eksklusif",
                    "Prioritas customer service",
                    "Akses promo eksklusif",
                    "Riwayat parkir lebih lengkap"
                )
            )
        ),
        promo = emptyList()
    )
    MaterialTheme {
        BenefitScreenPopUp(model = model)
    }
}
