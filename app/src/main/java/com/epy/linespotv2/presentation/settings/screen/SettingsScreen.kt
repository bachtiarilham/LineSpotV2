package com.epy.linespotv2.presentation.settings.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.epy.linespotv2.core.ui.theme.DarkBlue
import com.epy.linespotv2.core.ui.theme.GreyText
import com.epy.linespotv2.core.ui.theme.PageBg
import com.epy.linespotv2.core.ui.theme.SmartBlue
import com.epy.linespotv2.core.ui.theme.White
import com.epy.linespotv2.domain.model.auth.UserModel
import com.epy.linespotv2.domain.model.helper.TarifModel
import com.epy.linespotv2.presentation.settings.SettingsEffect
import com.epy.linespotv2.presentation.settings.SettingsIntent
import com.epy.linespotv2.presentation.settings.SettingsState
import com.epy.linespotv2.presentation.settings.SettingsViewModel

@Composable
fun SettingsScreen(
    onNavigateToLogin: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onNavigateToMetodePembayaran: () -> Unit = {},
    onNavigateToKeamanan: () -> Unit = {},
    onNavigateToPrivasi: () -> Unit = {},
    onNavigateToBantuanFaq : () -> Unit = {},
    onNavigateToSyaratKetentuan : () -> Unit = {},
    onNavigateToTentangAplikasi : () -> Unit = {},
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.settingsEffect) {
        when (state.settingsEffect) {
            is SettingsEffect.NavigateToLogin -> { onNavigateToLogin(); viewModel.consumeEffect() }
            is SettingsEffect.NavigateBack -> { onNavigateBack(); viewModel.consumeEffect() }
            is SettingsEffect.NavigateToKeamanan -> {onNavigateToKeamanan(); viewModel.consumeEffect()}
            is SettingsEffect.NavigateToMetodePembayaran -> {onNavigateToMetodePembayaran(); viewModel.consumeEffect()}
            is SettingsEffect.NavigateToPrivasi -> {onNavigateToPrivasi(); viewModel.consumeEffect()}
            is SettingsEffect.NavigateToBantuanFaq -> {onNavigateToBantuanFaq(); viewModel.consumeEffect()}
            is SettingsEffect.NavigateToSyaratKetentuan -> {onNavigateToSyaratKetentuan(); viewModel.consumeEffect()}
            is SettingsEffect.NavigateToTentangAplikasi -> {onNavigateToTentangAplikasi(); viewModel.consumeEffect()}
            null -> Unit
        }
    }

    SettingsScreenContent(
        state = state,
        onIntent = viewModel::onIntent
    )
}

@Composable
fun SettingsScreenContent(
    state: SettingsState,
    onIntent: (SettingsIntent) -> Unit
) {
    var notifEnabled by remember { mutableStateOf(true) }
    var selectedLanguage by remember { mutableStateOf("Bahasa Indonesia") }
    var selectedTheme by remember { mutableStateOf("Terang") }

    if (state.showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { onIntent(SettingsIntent.OnDismissLogout) },
            title = { Text("Keluar") },
            text = { Text("Kamu yakin ingin keluar dari akun ini?") },
            confirmButton = {
                TextButton(onClick = { onIntent(SettingsIntent.OnConfirmLogout) }) {
                    Text("Keluar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { onIntent(SettingsIntent.OnDismissLogout) }) {
                    Text("Batal")
                }
            }
        )
    }

    Scaffold(
        containerColor = PageBg,
        topBar = {
            SettingsTopBar(onBack = { onIntent(SettingsIntent.OnBackClicked) })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PageBg)
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (state.isLoading && state.userModel == null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = DarkBlue)
                }
            } else {
                state.userModel?.let { SettingsProfileCard(it) }
            }

            if (!state.error.isNullOrBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            SettingsGroup(
                title = "Akun",
                items = listOf(
                    SettingsItemModel(Icons.Default.Lock, "Keamanan"),
                    SettingsItemModel(Icons.Default.Payment, "Metode Pembayaran"),
                    SettingsItemModel(Icons.Default.NotificationsNone, "Notifikasi", hasSwitch = true),
                    SettingsItemModel(Icons.Default.PrivacyTip, "Privasi")
                ),
                onItemClick = { item ->
                    if (item.hasSwitch) notifEnabled = !notifEnabled
                },
                trailingContent = { item ->
                    if (item.hasSwitch) {
                        Switch(
                            checked = notifEnabled,
                            onCheckedChange = { notifEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = White,
                                checkedTrackColor = SmartBlue,
                                uncheckedThumbColor = White,
                                uncheckedTrackColor = Color(0xFFD4DBE7)
                            )
                        )
                    }
                }
            )

            SettingsGroup(
                title = "Preferensi",
                items = listOf(
                    SettingsItemModel(Icons.Default.Language, "Bahasa", subtitle = selectedLanguage),
                    SettingsItemModel(Icons.Default.WbSunny, "Mode Tampilan", subtitle = selectedTheme)
                ),
                onItemClick = { item ->
                    when (item.title) {
                        "Bahasa" -> selectedLanguage = if (selectedLanguage == "Bahasa Indonesia") "English" else "Bahasa Indonesia"
                        "Mode Tampilan" -> selectedTheme = if (selectedTheme == "Terang") "Gelap" else "Terang"
                    }
                },
                trailingContent = { item ->
                    if (item.title == "Bahasa") {
                        SegmentedTextPill(selectedLanguage)
                    } else if (item.title == "Mode Tampilan") {
                        SegmentedTextPill(selectedTheme)
                    }
                }
            )

            SettingsGroup(
                title = "Lainnya",
                items = listOf(
                    SettingsItemModel(Icons.Default.Info, "Bantuan & FAQ"),
                    SettingsItemModel(Icons.Default.StarOutline, "Syarat & Ketentuan"),
                    SettingsItemModel(Icons.Default.Info, "Tentang Aplikasi", subtitle = "Version 1.0.0"),
                    SettingsItemModel(
                        icon = Icons.AutoMirrored.Filled.ExitToApp,
                        title = "Keluar",
                        titleColor = Color(0xFFE84C4C)
                    )
                ),
                onItemClick = { item ->
                    if (item.title == "Keluar") onIntent(SettingsIntent.OnLogoutClicked)
                }
            )

            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Composable
private fun SettingsTopBar(onBack: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = DarkBlue,
        shadowElevation = 2.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .systemBarsPadding()
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = "Kembali",
                    tint = White
                )
            }
            Text(
                text = "Pengaturan",
                color = White,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun SettingsProfileCard(profile: UserModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Brush.linearGradient(listOf(SmartBlue, Color(0xFF4FA0FF)))),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = profile.fullName.take(1).uppercase(),
                    color = White,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = profile.fullName,
                    color = DarkBlue,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = profile.email,
                    color = GreyText,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = GreyText
            )
        }
    }
}

@Composable
private fun SettingsGroup(
    title: String,
    items: List<SettingsItemModel>,
    onItemClick: (SettingsItemModel) -> Unit = {},
    trailingContent: @Composable ((SettingsItemModel) -> Unit)? = null
) {
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(
            text = title,
            color = DarkBlue,
            style = MaterialTheme.typography.titleMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column {
                items.forEachIndexed { index, item ->
                    SettingsRow(
                        item = item,
                        onClick = { onItemClick(item) },
                        trailingContent = trailingContent?.let { { it(item) } }
                    )
                    if (index != items.lastIndex) {
                        HorizontalDivider(color = Color(0xFFE9EDF3), thickness = 1.dp)
                    }
                }
            }
        }
    }
}

private data class SettingsItemModel(
    val icon: ImageVector,
    val title: String,
    val subtitle: String? = null,
    val titleColor: Color = DarkBlue,
    val hasSwitch: Boolean = false
)

@Composable
private fun SettingsRow(
    item: SettingsItemModel,
    onClick: () -> Unit,
    trailingContent: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFF3F6FB)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = SmartBlue,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                color = item.titleColor,
                style = MaterialTheme.typography.bodyLarge
            )
            if (item.subtitle != null) {
                Text(
                    text = item.subtitle,
                    color = GreyText,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        if (trailingContent != null) {
            trailingContent()
        } else {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = GreyText
            )
        }
    }
}

@Composable
private fun SegmentedTextPill(text: String) {
    Surface(
        color = Color(0xFFF5F8FE),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFFD7E1F3))
    ) {
        Text(
            text = text,
            color = SmartBlue,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun SettingsScreenPreview() {
    MaterialTheme {
        SettingsScreenContent(
            state = SettingsState(
                userModel = UserModel(
                    userId = 1L,
                    nik = "3201234567890001",
                    fullName = "John Doe",
                    phone = "08123456789",
                    email = "john.doe@email.com",
                    username = "johndoe",
                    role = 1,
                    isVerified = true,
                    avatar_url = "",
                    lokasi = "",
                    zona = "",
                    tarif = listOf(
                        TarifModel(kendaraan = "Motor", nominal = 2_000L),
                        TarifModel(kendaraan = "Mobil", nominal = 5_000L)
                    ),
                    registeredAt = "",
                    createdAt = "",
                    updatedAt = ""
                )
            ),
            onIntent = {}
        )
    }
}
