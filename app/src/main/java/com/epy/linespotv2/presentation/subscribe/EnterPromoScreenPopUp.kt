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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.epy.linespotv2.core.ui.theme.DarkBlue
import com.epy.linespotv2.core.ui.theme.GreyText
import com.epy.linespotv2.core.ui.theme.PageBg
import com.epy.linespotv2.core.ui.theme.SmartBlue
import com.epy.linespotv2.core.ui.theme.Tangerine
import com.epy.linespotv2.core.ui.theme.White
import com.epy.linespotv2.domain.model.subscription.DetailPromo
import com.epy.linespotv2.domain.model.subscription.PromoTersedia
import com.epy.linespotv2.domain.model.subscription.SubscribeResponseModel
import com.epy.linespotv2.presentation.subscribe.ui_model.SubscribePromoColorStyle
import com.epy.linespotv2.presentation.subscribe.ui_model.SubscribePromoUiModel
import com.epy.linespotv2.presentation.subscribe.ui_model.toUiModel

@Composable
fun EnterPromoScreenPopUp(
    model: SubscribeResponseModel? = null,
    onClose: () -> Unit = {},
    onApply: (String) -> Unit = {},
    onSelectPromo: (String) -> Unit = {},
    viewModel: SubscribeViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        if (model == null) {
            viewModel.onIntent(SubscribeIntent.LoadPage)
        }
    }

    val uiModel = (model ?: state.subscribeResponseModel).toUiModel()

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
                    uiModel.promoTerms.forEach { BulletText(it) }
                }

                SectionTitle(text = "Promo tersedia")
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    uiModel.promoItems.forEach { promo ->
                        PromoRow(
                            promo = promo,
                            onClick = { onSelectPromo(promo.code) }
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
    promo: SubscribePromoUiModel,
    onClick: () -> Unit
) {
    val tintColor = when (promo.colorStyle) {
        SubscribePromoColorStyle.GREEN -> Color(0xFF2FA84F)
        SubscribePromoColorStyle.BLUE -> SmartBlue
        SubscribePromoColorStyle.ORANGE -> Tangerine
    }

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
                    .background(tintColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Percent,
                    contentDescription = null,
                    tint = tintColor
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
                color = tintColor.copy(alpha = 0.12f),
                shape = RoundedCornerShape(999.dp)
            ) {
                Text(
                    text = promo.badgeLabel,
                    color = tintColor,
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

@Preview(showBackground = true)
@Composable
private fun EnterPromoScreenPopUpPreview() {
    val model = SubscribeResponseModel(
        activePackageName = "Premium Gold",
        activePackageExpired = "2026-12-20",
        activePackageBenefits = emptyList(),
        listPaket = null,
        promoTersedia = PromoTersedia(
            syaratDanKetentuan = listOf(
                "Kode promo hanya berlaku untuk paket membership.",
                "Satu kode promo hanya dapat digunakan 1 kali.",
                "Kode promo tidak dapat digabung dengan promo lain.",
                "Pastikan kode promo masih berlaku."
            ),
            listPromo = listOf(
                DetailPromo("GOLDMONTH13", 13),
                DetailPromo("GOLD6MONTH15", 15),
                DetailPromo("GOLDYEAR20", 20)
            )
        )
    )

    MaterialTheme {
        EnterPromoScreenPopUp(model = model)
    }
}
