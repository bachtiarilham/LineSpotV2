package com.epy.linespotv2.presentation.input_manual

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.SyncAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.epy.linespotv2.core.ui.theme.White
import com.epy.linespotv2.presentation.input_manual.ui_model.InputManualUiModel
import com.epy.linespotv2.presentation.input_manual.ui_model.InputManualVehicleUiFilter

@Composable
fun InputManualScreen(
    onBack: () -> Unit = {},
    onNavigateToPembayaran: () -> Unit = {},
    viewModel: InputManualViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onIntent(InputManualIntent.LoadPage)
    }

    LaunchedEffect(state.inputManualEffect) {
        when (state.inputManualEffect) {
            InputManualEffect.NavigateBack -> {
                onBack()
                viewModel.consumeEffect()
            }
            InputManualEffect.NavigateToPembayaran -> {
                onNavigateToPembayaran()
                viewModel.consumeEffect()
            }
            is InputManualEffect.NavigateToPaymentDetail,
            is InputManualEffect.NavigateToPaymentMethod,
            InputManualEffect.PrintReceipt,
            is InputManualEffect.ShowToast,
            InputManualEffect.ShowPaymentSuccess,
            InputManualEffect.ShowPaymentFailed,
            null -> Unit
        }
    }

    InputManualContent(
        uiModel = state.inputManualUiModel,
        state = state,
        onIntent = viewModel::onIntent
    )
}

@Composable
private fun InputManualContent(
    uiModel: InputManualUiModel,
    state: InputManualState,
    onIntent: (InputManualIntent) -> Unit
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
        SimpleTopBar(
            title = uiModel.title,
            onBack = { onIntent(InputManualIntent.ClickBack) }
        )
        InfoCard(uiModel.infoMessage)
        SectionLabel("Data Kendaraan")
        NomorPolisiField(
            value = uiModel.nomorPolisi,
            placeholder = uiModel.nomorPolisiPlaceholder,
            onValueChange = { onIntent(InputManualIntent.ChangeNomorPolisi(it)) }
        )
        SectionLabel("Jenis Kendaraan")
        VehicleSelector(
            vehicleOptions = uiModel.vehicleOptions,
            selectedVehicle = uiModel.selectedVehicle,
            onVehicleSelected = {
                onIntent(InputManualIntent.SelectJenisKendaraan(it.code))
            }
        )
        ReadOnlyField("Waktu Masuk", uiModel.waktuMasuk, Icons.Default.CalendarMonth)
        AreaDropdownField(
            label = "Area Parkir",
            value = uiModel.areaParkir,
            options = uiModel.areaOptions,
            onSelect = { onIntent(InputManualIntent.SelectArea(it)) }
        )
        TariffCard(totalTarifLabel = uiModel.totalTarifLabel)

        state.error?.takeIf { it.isNotBlank() }?.let { message ->
            Text(
                text = message,
                color = Color(0xFFD92D20),
                style = MaterialTheme.typography.bodySmall
            )
        }

        Surface(
            color = if (state.isLoading) SmartBlue.copy(alpha = 0.7f) else SmartBlue,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = !state.isLoading) {
                    onIntent(InputManualIntent.SubmitInputManual)
                }
        ) {
            Text(
                text = if (state.isLoading) "Memproses..." else uiModel.submitLabel,
                color = White,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp)
            )
        }

        Text(
            text = uiModel.cancelLabel,
            color = SmartBlue,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable { onIntent(InputManualIntent.ClickCancel) }
        )
    }
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
private fun AreaDropdownField(
    label: String,
    value: String,
    options: List<String>,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val hasOptions = options.isNotEmpty()

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = label, color = DarkBlue, style = MaterialTheme.typography.titleSmall)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { if (hasOptions) expanded = !expanded }
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = {},
                readOnly = true,
                enabled = hasOptions,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = DarkBlue),
                shape = RoundedCornerShape(12.dp),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDownward,
                        contentDescription = null,
                        tint = if (hasOptions) SmartBlue else GreyText
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .clickable(enabled = hasOptions) { expanded = true },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onSelect(option)
                            expanded = false
                        }
                    )
                }
            }
        }
        if (!hasOptions) {
            Text(
                text = "Area parkir belum tersedia",
                color = GreyText,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun VehicleSelector(
    vehicleOptions: List<InputManualVehicleUiFilter>,
    selectedVehicle: InputManualVehicleUiFilter,
    onVehicleSelected: (InputManualVehicleUiFilter) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        vehicleOptions.forEach { option ->
            VehicleChip(
                text = option.label,
                selected = option == selectedVehicle,
                modifier = Modifier.weight(1f),
                onClick = { onVehicleSelected(option) }
            )
        }
    }
}

@Composable
private fun VehicleChip(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        color = if (selected) Color(0xFFEFF5FF) else White,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, if (selected) SmartBlue else Color(0xFFE3E8F0)),
        modifier = modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.SyncAlt,
                contentDescription = null,
                tint = if (selected) SmartBlue else DarkBlue,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                color = if (selected) SmartBlue else DarkBlue,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun TariffCard(totalTarifLabel: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            SectionLabel("Tarif")
            SummaryRow("Total Tarif", totalTarifLabel)
        }
    }
}

@Composable
private fun SummaryRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = label, color = GreyText, style = MaterialTheme.typography.bodySmall)
        Text(text = value, color = DarkBlue, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun NomorPolisiField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = "No. Polisi", color = DarkBlue, style = MaterialTheme.typography.titleSmall)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(text = placeholder, color = GreyText, style = MaterialTheme.typography.bodyMedium)
            },
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = DarkBlue),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun ReadOnlyField(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = label, color = DarkBlue, style = MaterialTheme.typography.titleSmall)
        Surface(
            color = White,
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color(0xFFE4EAF3))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = value,
                    color = GreyText,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )
                Icon(imageVector = icon, contentDescription = null, tint = SmartBlue)
            }
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(text = text, color = DarkBlue, style = MaterialTheme.typography.titleMedium)
}

@Composable
private fun InfoCard(text: String) {
    Surface(color = Color(0xFFF1F6FF), shape = RoundedCornerShape(14.dp)) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Info, null, tint = SmartBlue, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = text, color = DarkBlue, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun SimpleTopBar(title: String, onBack: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.ChevronLeft,
            contentDescription = null,
            tint = DarkBlue,
            modifier = Modifier.clickable { onBack() }
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = title, color = DarkBlue, style = MaterialTheme.typography.titleLarge)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun InputManualScreenPreview() {
    MaterialTheme {
        InputManualContent(
            uiModel = InputManualUiModel(
                nomorPolisi = "B 1234 ABC",
                selectedVehicle = InputManualVehicleUiFilter.MOTOR,
                waktuMasuk = "30 Mei 2024, 08:15:00",
                areaParkir = "Jl. Sudirman - Zona Biru",
                totalTarifLabel = "Rp 2.000"
            ),
            state = InputManualState(),
            onIntent = {}
        )
    }
}
