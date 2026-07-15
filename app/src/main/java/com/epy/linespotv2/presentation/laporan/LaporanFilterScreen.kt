package com.epy.linespotv2.presentation.laporan

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.epy.linespotv2.core.ui.theme.DarkBlue
import com.epy.linespotv2.core.ui.theme.GreyText
import com.epy.linespotv2.core.ui.theme.PageBg
import com.epy.linespotv2.core.ui.theme.SmartBlue
import com.epy.linespotv2.core.ui.theme.White
import com.epy.linespotv2.core.utils.parseIndonesiaDateOrNull
import com.epy.linespotv2.core.utils.toIndonesiaDate
import java.util.Date

@Composable
fun LaporanFilterScreen(
    onBack: () -> Unit = {},
    onSubmit: () -> Unit = {},
    viewModel: LaporanViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onIntent(LaporanIntent.loadFilterPage)
    }

    LaunchedEffect(state.laporanEffect) {
        when (state.laporanEffect) {
            LaporanEffect.NavigateToLaporan -> {
                onSubmit()
                viewModel.consumeEffect()
            }
            LaporanEffect.NavigateToFilter -> viewModel.consumeEffect()
            null -> Unit
        }
    }

    LaporanFilterContent(
        state = state,
        onBack = onBack,
        onIntent = viewModel::onIntent,
    )
}

@Composable
private fun LaporanFilterContent(
    state: LaporanState,
    onBack: () -> Unit,
    onIntent: (LaporanIntent) -> Unit,
) {
    var startDate by rememberSaveable { mutableStateOf(Date().toIndonesiaDate()) }
    var endDate by rememberSaveable { mutableStateOf(Date().toIndonesiaDate()) }
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBg)
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        FilterHeader(onBack = onBack)
        InfoBanner()

        state.error?.takeIf { it.isNotBlank() }?.let { message ->
            ErrorBanner(message = message)
        }

        PeriodSection(
            startDate = startDate,
            endDate = endDate,
            onStartDateClick = { showStartDatePicker = true },
            onEndDateClick = { showEndDatePicker = true }
        )

        Spacer(modifier = Modifier.height(10.dp))

        SubmitButton(
            isLoading = state.isLoading,
            onClick = {
                onIntent(
                    LaporanIntent.submitFilter(
                        startDate = startDate,
                        endDate = endDate,
                    )
                )
            }
        )
    }

    if (showStartDatePicker) {
        LaporanDatePickerDialog(
            initialDate = startDate,
            onDismiss = { showStartDatePicker = false },
            onConfirm = {
                startDate = it
                showStartDatePicker = false
            }
        )
    }

    if (showEndDatePicker) {
        LaporanDatePickerDialog(
            initialDate = endDate,
            onDismiss = { showEndDatePicker = false },
            onConfirm = {
                endDate = it
                showEndDatePicker = false
            }
        )
    }
}

@Composable
private fun FilterHeader(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = DarkBlue,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable { onBack() }
        )
        Text(
            text = "Filter Laporan",
            color = DarkBlue,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun InfoBanner() {
    Surface(
        color = Color(0xFFF2F6FF),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = SmartBlue,
            )
            Text(
                text = "Maksimal rentang periode adalah 1 minggu.",
                color = GreyText,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun ErrorBanner(message: String) {
    Surface(
        color = Color(0xFFFFF4F4),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = message,
            color = Color(0xFFB42318),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
        )
    }
}

@Composable
private fun PeriodSection(
    startDate: String,
    endDate: String,
    onStartDateClick: () -> Unit,
    onEndDateClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Periode Laporan",
                color = DarkBlue,
                style = MaterialTheme.typography.titleMedium
            )
            DateRangeField(
                startDate = startDate,
                endDate = endDate,
                onStartDateClick = onStartDateClick,
                onEndDateClick = onEndDateClick
            )
        }
    }
}

@Composable
private fun DateRangeField(
    startDate: String,
    endDate: String,
    onStartDateClick: () -> Unit,
    onEndDateClick: () -> Unit
) {
    Surface(
        color = White,
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, Color(0xFFE3E8F0)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                DateItem(
                    label = "Tanggal Awal",
                    value = startDate,
                    onClick = onStartDateClick
                )
                DateItem(
                    label = "Tanggal Akhir",
                    value = endDate,
                    onClick = onEndDateClick
                )
            }
        }
    }
}

@Composable
private fun DateItem(
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = label,
            color = GreyText,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = value,
            color = DarkBlue,
            style = MaterialTheme.typography.bodyMedium
        )
        Icon(
            imageVector = Icons.Outlined.CalendarMonth,
            contentDescription = null,
            tint = SmartBlue,
            modifier = Modifier.size(20.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LokasiDropdown(
    lokasiList: List<String>,
    selectedLokasi: String,
    isLoading: Boolean,
    onSelectLokasi: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = if (isLoading) "Memuat area parkir..." else selectedLokasi,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = GreyText,
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                focusedBorderColor = SmartBlue,
                unfocusedBorderColor = Color(0xFFE3E8F0),
                focusedTextColor = DarkBlue,
                unfocusedTextColor = DarkBlue
            )
        )

        ExposedDropdownMenu(
            expanded = expanded && !isLoading,
            onDismissRequest = { expanded = false }
        ) {
            lokasiList.forEach { lokasi ->
                DropdownMenuItem(
                    text = { Text(text = lokasi) },
                    onClick = {
                        onSelectLokasi(lokasi)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun LaporanDatePickerDialog(
    initialDate: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    val initialMillis = remember(initialDate) { initialDate.toMillis() }
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialMillis)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    val selectedDate = datePickerState.selectedDateMillis
                    if (selectedDate != null) {
                        onConfirm(Date(selectedDate).toIndonesiaDate())
                    } else {
                        onDismiss()
                    }
                }
            ) {
                Text(text = "Pilih", color = SmartBlue)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Batal", color = GreyText)
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

private fun String.toMillis(): Long? {
    return parseIndonesiaDateOrNull()?.time
}

@Composable
private fun SubmitButton(
    isLoading: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = !isLoading,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = SmartBlue)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = White,
                strokeWidth = 2.dp,
                modifier = Modifier.size(20.dp)
            )
        } else {
            Text(
                text = "Tampilkan Laporan",
                color = White,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LaporanFilterScreenPreview() {
    MaterialTheme {
        LaporanFilterContent(
            state = LaporanState(),
            onBack = {},
            onIntent = {},
        )
    }
}
