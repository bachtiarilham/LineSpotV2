package com.epy.linespotv2.presentation.riwayat

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.NorthEast
import androidx.compose.material.icons.filled.SouthWest
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.epy.linespotv2.core.utils.parseIndonesiaDateOrNull
import com.epy.linespotv2.core.utils.toIndonesiaDate
import com.epy.linespotv2.domain.model.riwayat.RiwayatPaymentFilter
import com.epy.linespotv2.domain.model.riwayat.RiwayatTransactionFilter
import com.epy.linespotv2.domain.model.riwayat.RiwayatVehicleFilter
import java.util.Date

@Composable
fun RiwayatFilterScreen(
    onCancel: () -> Unit = {},
    onNavigateToRiwayat: () -> Unit = {},
    viewModel: RiwayatViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var startDate by rememberSaveable { mutableStateOf(Date().toIndonesiaDate()) }
    var endDate by rememberSaveable { mutableStateOf(Date().toIndonesiaDate()) }
    var selectedTransaction by rememberSaveable { mutableStateOf(RiwayatTransactionFilter.ALL) }
    var selectedPayment by rememberSaveable { mutableStateOf(RiwayatPaymentFilter.ALL) }
    var selectedVehicle by rememberSaveable { mutableStateOf(RiwayatVehicleFilter.ALL) }

    LaunchedEffect(Unit) {
        viewModel.onIntent(RiwayatIntent.loadFilterPage)
    }

    LaunchedEffect(state.riwayatEffect) {
        when (state.riwayatEffect) {
            RiwayatEffect.NavigateToRiwayat -> {
                onNavigateToRiwayat()
                viewModel.consumeEffect()
            }
            RiwayatEffect.NavigateToDetail -> Unit
            RiwayatEffect.NavigateToFilter -> Unit
            null -> Unit
        }
    }

    RiwayatFilterContent(
        state = state,
        startDate = startDate,
        endDate = endDate,
        selectedTransaction = selectedTransaction,
        selectedPayment = selectedPayment,
        selectedVehicle = selectedVehicle,
        onReset = {
            startDate = Date().toIndonesiaDate()
            endDate = Date().toIndonesiaDate()
            selectedTransaction = RiwayatTransactionFilter.ALL
            selectedPayment = RiwayatPaymentFilter.ALL
            selectedVehicle = RiwayatVehicleFilter.ALL
            viewModel.onIntent(RiwayatIntent.selectLokasi("Semua Area"))
        },
        onCancel = onCancel,
        onStartDateClick = { startDate = it },
        onEndDateClick = { endDate = it },
        onSelectLokasi = { viewModel.onIntent(RiwayatIntent.selectLokasi(it)) },
        onSelectTransaction = { selectedTransaction = it },
        onSelectPayment = { selectedPayment = it },
        onSelectVehicle = { selectedVehicle = it },
        onApply = {
            viewModel.onIntent(
                RiwayatIntent.submitFilter(
                    startDate = startDate,
                    endDate = endDate,
                    transaction = selectedTransaction,
                    payment = selectedPayment,
                    vehicle = selectedVehicle,
                    lokasi = state.selectedLokasi
                )
            )
        }
    )
}

@Composable
fun RiwayatFilterContent(
    state: RiwayatState,
    startDate: String,
    endDate: String,
    selectedTransaction: RiwayatTransactionFilter,
    selectedPayment: RiwayatPaymentFilter,
    selectedVehicle: RiwayatVehicleFilter,
    onReset: () -> Unit,
    onCancel: () -> Unit,
    onStartDateClick: (String) -> Unit,
    onEndDateClick: (String) -> Unit,
    onSelectLokasi: (String) -> Unit,
    onSelectTransaction: (RiwayatTransactionFilter) -> Unit,
    onSelectPayment: (RiwayatPaymentFilter) -> Unit,
    onSelectVehicle: (RiwayatVehicleFilter) -> Unit,
    onApply: () -> Unit
) {
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    Surface(
        color = White,
        shape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 42.dp, height = 4.dp)
                        .background(Color(0xFFD3D8E3), RoundedCornerShape(999.dp))
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filter Riwayat",
                    color = DarkBlue,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Reset",
                    color = SmartBlue,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.clickable { onReset() }
                )
            }

            FilterSection("Rentang Tanggal") {
                DateRangeField(
                    startDate = startDate,
                    endDate = endDate,
                    onStartDateClick = { showStartDatePicker = true },
                    onEndDateClick = { showEndDatePicker = true }
                )
            }

            FilterSection("Jenis Transaksi") {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    FilterChip("Semua", null, selectedTransaction == RiwayatTransactionFilter.ALL, Modifier.weight(1f)) {
                        onSelectTransaction(RiwayatTransactionFilter.ALL)
                    }
                    FilterChip("Masuk", Icons.Default.SouthWest, selectedTransaction == RiwayatTransactionFilter.MASUK, Modifier.weight(1f)) {
                        onSelectTransaction(RiwayatTransactionFilter.MASUK)
                    }
                    FilterChip("Keluar", Icons.Default.NorthEast, selectedTransaction == RiwayatTransactionFilter.KELUAR, Modifier.weight(1f)) {
                        onSelectTransaction(RiwayatTransactionFilter.KELUAR)
                    }
                }
            }

            FilterSection("Metode Pembayaran") {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    FilterChip("Semua", null, selectedPayment == RiwayatPaymentFilter.ALL, Modifier.weight(1f)) {
                        onSelectPayment(RiwayatPaymentFilter.ALL)
                    }
                    FilterChip("Tunai", Icons.Default.MonetizationOn, selectedPayment == RiwayatPaymentFilter.TUNAI, Modifier.weight(1f)) {
                        onSelectPayment(RiwayatPaymentFilter.TUNAI)
                    }
                    FilterChip("Non Tunai", Icons.Default.CalendarMonth, selectedPayment == RiwayatPaymentFilter.NON_TUNAI, Modifier.weight(1f)) {
                        onSelectPayment(RiwayatPaymentFilter.NON_TUNAI)
                    }
                }
            }

            FilterSection("Jenis Kendaraan") {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    FilterChip("Semua", null, selectedVehicle == RiwayatVehicleFilter.ALL, Modifier.weight(1f)) {
                        onSelectVehicle(RiwayatVehicleFilter.ALL)
                    }
                    FilterChip("Motor", Icons.Default.TwoWheeler, selectedVehicle == RiwayatVehicleFilter.MOTOR, Modifier.weight(1f)) {
                        onSelectVehicle(RiwayatVehicleFilter.MOTOR)
                    }
                    FilterChip("Mobil", Icons.Default.DirectionsCar, selectedVehicle == RiwayatVehicleFilter.MOBIL, Modifier.weight(1f)) {
                        onSelectVehicle(RiwayatVehicleFilter.MOBIL)
                    }
                }
            }

            FilterSection("Area Parkir") {
                DropdownField(
                    text = state.selectedLokasi,
                    locations = state.lokasiList,
                    isLoading = state.isLoadingLokasi,
                    error = state.errorLokasi,
                    onSelect = onSelectLokasi
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ActionButton(
                    text = "Batal",
                    selected = false,
                    modifier = Modifier.weight(1f),
                    onClick = onCancel
                )
                ActionButton(
                    text = if (state.isLoading) "Memuat..." else "Terapkan Filter",
                    selected = true,
                    modifier = Modifier.weight(1f),
                    onClick = onApply
                )
            }
        }
    }

    if (showStartDatePicker) {
        RiwayatDatePickerDialog(
            initialDate = startDate,
            onDismiss = { showStartDatePicker = false },
            onConfirm = {
                onStartDateClick(it)
                showStartDatePicker = false
            }
        )
    }

    if (showEndDatePicker) {
        RiwayatDatePickerDialog(
            initialDate = endDate,
            onDismiss = { showEndDatePicker = false },
            onConfirm = {
                onEndDateClick(it)
                showEndDatePicker = false
            }
        )
    }
}

@Composable
private fun FilterSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = title,
            color = DarkBlue,
            style = MaterialTheme.typography.titleSmall
        )
        content()
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
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFFE3E8F0)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = null,
                tint = SmartBlue,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = startDate,
                color = DarkBlue,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.clickable { onStartDateClick() }
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = " - ",
                color = GreyText,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = endDate,
                color = DarkBlue,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onEndDateClick() }
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = GreyText,
                modifier = Modifier.clickable { onEndDateClick() }
            )
        }
    }
}

@Composable
private fun RiwayatDatePickerDialog(
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
                    val selected = datePickerState.selectedDateMillis
                    if (selected != null) {
                        onConfirm(selected.toIndonesiaDateString())
                    } else {
                        onDismiss()
                    }
                }
            ) {
                Text("Pilih", color = SmartBlue)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal", color = GreyText)
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

private fun String.toMillis(): Long? {
    return parseIndonesiaDateOrNull()?.time
}

private fun Long.toIndonesiaDateString(): String {
    return Date(this).toIndonesiaDate()
}

@Composable
private fun DropdownField(
    text: String,
    locations: List<String>,
    isLoading: Boolean,
    error: String?,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Surface(
        color = White,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFFE3E8F0)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                color = GreyText,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .weight(1f)
                    .clickable { expanded = !expanded }
            )
            if (isLoading) {
                CircularProgressIndicator(
                    color = SmartBlue,
                    modifier = Modifier.size(18.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Icon(
                    imageVector = Icons.Default.ArrowDownward,
                    contentDescription = null,
                    tint = GreyText,
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            }
        }
    }

    if (expanded) {
        Column(
            modifier = Modifier.padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            locations.forEach { location ->
                Surface(
                    color = if (location == text) Color(0xFFEFF5FF) else White,
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(
                        1.dp,
                        if (location == text) SmartBlue else Color(0xFFE3E8F0)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onSelect(location)
                            expanded = false
                        }
                ) {
                    Text(
                        text = location,
                        color = if (location == text) SmartBlue else DarkBlue,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp)
                    )
                }
            }

            if (!error.isNullOrBlank()) {
                Text(
                    text = error,
                    color = Color(0xFFE04F4F),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun FilterChip(
    text: String,
    icon: ImageVector?,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Surface(
        color = if (selected) Color(0xFFEFF5FF) else White,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            1.dp,
            if (selected) SmartBlue else Color(0xFFE3E8F0)
        ),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(horizontal = 10.dp, vertical = 11.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (selected) SmartBlue else GreyText,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.size(6.dp))
            }
            Text(
                text = text,
                color = if (selected) SmartBlue else DarkBlue,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun ActionButton(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Surface(
        color = if (selected) SmartBlue else White,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            1.dp,
            if (selected) SmartBlue else Color(0xFFD6E2FF)
        ),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(vertical = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = if (selected) White else SmartBlue,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF4F6FA)
@Composable
private fun RiwayatFilterScreenPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .background(PageBg)
                .padding(top = 24.dp)
        ) {
            RiwayatFilterContent(
                state = RiwayatState(
                    lokasiList = listOf("Semua Area", "Zona Biru", "Zona Merah"),
                    selectedLokasi = "Zona Biru"
                ),
                startDate = Date().toIndonesiaDate(),
                endDate = Date().toIndonesiaDate(),
                selectedTransaction = RiwayatTransactionFilter.ALL,
                selectedPayment = RiwayatPaymentFilter.ALL,
                selectedVehicle = RiwayatVehicleFilter.ALL,
                onReset = {},
                onCancel = {},
                onStartDateClick = {},
                onEndDateClick = {},
                onSelectLokasi = {},
                onSelectTransaction = {},
                onSelectPayment = {},
                onSelectVehicle = {},
                onApply = {}
            )
        }
    }
}
