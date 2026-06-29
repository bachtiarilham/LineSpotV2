package com.epy.linespotv2.presentation.subscribe

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Percent
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epy.linespotv2.core.ui.theme.DarkBlue
import com.epy.linespotv2.core.ui.theme.GreyText
import com.epy.linespotv2.core.ui.theme.PageBg
import com.epy.linespotv2.core.ui.theme.SmartBlue
import com.epy.linespotv2.core.ui.theme.Tangerine
import com.epy.linespotv2.core.ui.theme.White
import com.epy.linespotv2.domain.model.Promo
import com.epy.linespotv2.domain.model.PromoTerpilih
import com.epy.linespotv2.domain.model.SubscribeModel

private data class PromoOfferUi(
    val code: String,
    val description: String,
    val badge: String,
    val color: Color
)

@Composable
fun EnterPromoScreenPopUp(
    model: SubscribeModel? = null,
    onClose: () -> Unit = {},
    onApply: (String) -> Unit = {},
    onSelectPromo: (String) -> Unit = {}
) {
    val promoGroup = model?.promo?.firstOrNull()
    val promoSnk = promoGroup?.sNk.orEmpty()
    val promoList = promoGroup?.promo.orEmpty()

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
                    text = "Masukkan Kode Promo",
                    color = DarkBlue,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Gunakan kode promo untuk mendapatkan diskon",
                    color = GreyText,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth()
                )

                PromoInputField(onApply = onApply)

                SectionTitle(text = "Syarat & Ketentuan")
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    if (promoSnk.isNotEmpty()) {
                        promoSnk.forEach { BulletText(it) }
                    } else {
                        BulletText("Gagal Patch")
                    }
                }

                SectionTitle(text = "Promo tersedia")

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    if (promoList.isNotEmpty()) {
                        promoList.forEach { promo ->
                            PromoRow(
                                promo = promo.toUiOffer(),
                                onClick = { onSelectPromo(promo.namaPromo) }
                            )
                        }
                    } else {
                        PromoRow(
                            promo = PromoOfferUi("Error", "Error", "Error", Color(0xFF2FA84F)),
                            onClick = { }
                        )
                    }
                }

                InfoBox()
            }
        }
    }
}

@Composable
private fun PromoInputField(onApply: (String) -> Unit) {
    Surface(
        color = White,
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFE3E7EF), RoundedCornerShape(14.dp))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Newspaper,
                contentDescription = null,
                tint = GreyText
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "Masukkan kode promo",
                color = GreyText,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Surface(
                color = Color(0xFFF0F3F8),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.clickable { onApply("") }
            ) {
                Text(
                    text = "Terapkan",
                    color = GreyText,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
private fun PromoRow(
    promo: PromoOfferUi,
    onClick: () -> Unit
) {
    Surface(
        color = White,
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFE3E7EF), RoundedCornerShape(14.dp))
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(promo.color.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Percent,
                    contentDescription = null,
                    tint = promo.color
                )
            }
            Spacer(modifier = Modifier.size(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = promo.code,
                    color = DarkBlue,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = promo.description,
                    color = GreyText,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Surface(
                color = promo.color.copy(alpha = 0.12f),
                shape = RoundedCornerShape(999.dp)
            ) {
                Text(
                    text = promo.badge,
                    color = promo.color,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        color = DarkBlue,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
private fun BulletText(text: String) {
    Row(verticalAlignment = Alignment.Top) {
        Text(
            text = "•",
            color = DarkBlue,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = text,
            color = DarkBlue,
            style = MaterialTheme.typography.bodySmall,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun InfoBox() {
    Surface(
        color = SmartBlue.copy(alpha = 0.10f),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = SmartBlue
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "Masukkan kode promo di atas untuk mendapatkan potongan harga spesial.",
                color = SmartBlue,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

private fun PromoTerpilih.toUiOffer(): PromoOfferUi {
    val badgeColor = when {
        jumlahDiskon >= 20 -> Tangerine
        jumlahDiskon >= 15 -> SmartBlue
        else -> Color(0xFF2FA84F)
    }
    return PromoOfferUi(
        code = namaPromo,
        description = deskripsi,
        badge = "HEMAT ${jumlahDiskon}%",
        color = badgeColor
    )
}

@Preview(showBackground = true)
@Composable
private fun EnterPromoScreenPopUpPreview() {
    val model = SubscribeModel(
        statusCard = com.epy.linespotv2.domain.model.StatusCard(
            paketAktif = "Premium Gold",
            kadaluarsa = "20 Mei 2025",
            benefit = "Nikmati benefit parkir premium"
        ),
        packageCard = emptyList(),
        promo = listOf(
            Promo(
                sNk = listOf(
                    "Kode promo hanya berlaku untuk paket membership.",
                    "Satu kode promo hanya dapat digunakan 1 kali.",
                    "Kode promo tidak dapat digabung dengan promo lain.",
                    "Pastikan kode promo masih berlaku."
                ),
                promo = listOf(
                    PromoTerpilih("GOLDMONTH13", "Diskon 13% untuk paket bulanan", 13),
                    PromoTerpilih("GOLD6MONTH15", "Diskon 15% untuk paket 6 bulan", 15),
                    PromoTerpilih("GOLDYEAR20", "Diskon 20% untuk paket tahunan", 20)
                )
            )
        )
    )
    MaterialTheme {
        EnterPromoScreenPopUp(model = model)
    }
}
