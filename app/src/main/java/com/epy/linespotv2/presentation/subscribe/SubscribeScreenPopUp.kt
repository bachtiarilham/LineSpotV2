package com.epy.linespotv2.presentation.subscribe

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.style.TextAlign
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
import com.epy.linespotv2.domain.model.subscription.DetailPaket
import com.epy.linespotv2.domain.model.subscription.ListPaket
import com.epy.linespotv2.domain.model.subscription.PromoTersedia
import com.epy.linespotv2.domain.model.subscription.SubscribeResponseModel
import com.epy.linespotv2.presentation.subscribe.ui_model.SubscribePackageUiModel
import com.epy.linespotv2.presentation.subscribe.ui_model.toUiModel

@Composable
fun SubscribeScreenPopUpScreen(
    packageName: String = "",
    model: SubscribeResponseModel? = null,
    onClose: () -> Unit = {},
    onSubscribeNow: (String) -> Unit = {},
    viewModel: SubscribeViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        if (model == null) {
            viewModel.onIntent(SubscribeIntent.LoadPage)
        }
    }

    val uiModel = (model ?: state.subscribeResponseModel).toUiModel()
    val selectedPackage = uiModel.allPackages().firstOrNull {
        it.name.equals(packageName, ignoreCase = true)
    } ?: uiModel.activePackageOrFallback() ?: return

    Box(
        modifier = Modifier
            .fillMaxSize()
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

                PackageSummaryCard(selectedPackage = selectedPackage)

                Text(
                    text = "Benefit Anda",
                    color = DarkBlue,
                    style = MaterialTheme.typography.titleLarge
                )

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    selectedPackage.benefits.forEach { benefit ->
                        BenefitRow(text = benefit)
                    }
                }

                Divider(color = Color(0xFFE7EAF0))

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "Total Pembayaran",
                        color = GreyText,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "${selectedPackage.priceLabel} ${selectedPackage.periodLabel}",
                        color = DarkBlue,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }

                Surface(
                    color = SmartBlue,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.clickable { onSubscribeNow(selectedPackage.name) }
                ) {
                    Text(
                        text = "Langganan Sekarang",
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
private fun PackageSummaryCard(selectedPackage: SubscribePackageUiModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFDF4DF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = selectedPackage.name,
                    color = DarkBlue,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.weight(1f))
                Surface(
                    color = if (selectedPackage.name.equals("VIP", ignoreCase = true)) Tangerine else SmartBlue,
                    shape = RoundedCornerShape(999.dp)
                ) {
                    Text(
                        text = if (selectedPackage.name.equals("VIP", ignoreCase = true)) "Terbaik" else "Populer",
                        color = White,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }

            Text(
                text = "${selectedPackage.priceLabel} ${selectedPackage.periodLabel}",
                color = DarkBlue,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = selectedPackage.infoLabel,
                color = DarkBlue,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun BenefitRow(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = Color(0xFF2FA84F),
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = text,
            color = DarkBlue,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SubscribeScreenPopUpPreview() {
    val model = SubscribeResponseModel(
        activePackageName = "VIP",
        activePackageExpired = "2026-12-20",
        activePackageBenefits = listOf("Diskon 25%", "6 jam gratis / bulan"),
        listPaket = ListPaket(
            bulanan = emptyList(),
            enamBulan = emptyList(),
            tahunan = listOf(
                DetailPaket(
                    namaPaket = "VIP",
                    harga = 990_000L,
                    coverageLokasi = listOf("Semua area"),
                    benefitPackage = listOf(
                        "Diskon parkir 25%",
                        "6 jam parkir gratis / bulan",
                        "Prioritas customer service",
                        "Akses promo eksklusif",
                        "Riwayat parkir lebih lengkap"
                    )
                )
            )
        ),
        promoTersedia = PromoTersedia()
    )

    MaterialTheme {
        SubscribeScreenPopUpScreen(model = model)
    }
}
