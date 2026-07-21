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
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.KeyboardArrowDown
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.epy.linespotv2.core.ui.theme.DarkBlue
import com.epy.linespotv2.core.ui.theme.GreyText
import com.epy.linespotv2.core.ui.theme.PageBg
import com.epy.linespotv2.core.ui.theme.SmartBlue
import com.epy.linespotv2.core.ui.theme.White
import com.epy.linespotv2.core.utils.parseApiDateOrNull
import com.epy.linespotv2.core.utils.toApiDate
import java.util.Calendar
import java.util.Date

private enum class RiwayatDateField {
    START,
    END
}

@Composable
fun RiwayatFilterScreen(
    onCancel: () -> Unit = {},
    onNavigateToRiwayat: () -> Unit = {},
    viewModel: RiwayatViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var startDate by rememberSaveable { mutableStateOf(Date().toApiDate()) }
    var endDate by rememberSaveable { mutableStateOf(Date().toApiDate()) }

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
        onReset = {
            startDate = Date().toApiDate()
            endDate = Date().toApiDate()
        },
        onCancel = onCancel,
        onStartDateClick = { startDate = it },
        onEndDateClick = { endDate = it },
        onQuickRangeSelected = { start, end ->
            startDate = start
            endDate = end
        },
        onApply = {
            viewModel.onIntent(
                RiwayatIntent.submitFilter(
                    startDate = startDate,
                    endDate = endDate
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
    onReset: () -> Unit,
    onCancel: () -> Unit,
    onStartDateClick: (String) -> Unit,
    onEndDateClick: (String) -> Unit,
    onQuickRangeSelected: (String, String) -> Unit,
    onApply: () -> Unit
) {
    var activeDateField by remember { mutableStateOf<RiwayatDateField?>(null) }

    // Logic untuk menghitung Quick Range tanggal
    val today = Date()
    val todayStr = today.toApiDate()

    val yesterdayStr = remember {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -1)
        cal.time.toApiDate()
    }

    val last7DaysStr = remember {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -6) // Maksimal 7 hari terakhir (hari ini + 6 hari ke belakang)
        cal.time.toApiDate()
    }

    Surface(
        color = White,
        shape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
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
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = "Kembali",
                    tint = DarkBlue,
                    modifier = Modifier
                        .size(22.dp)
                        .clickable { onCancel() }
                )
                Spacer(modifier = Modifier.size(12.dp))
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

            // SECTION 1: Pintasan Cepat (Quick Range Chips)
            FilterSection("Pilih Cepat") {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    val isToday = startDate == todayStr && endDate == todayStr
                    val isYesterday = startDate == yesterdayStr && endDate == yesterdayStr
                    val is7Days = startDate == last7DaysStr && endDate == todayStr

                    FilterChip("Hari Ini", isToday, Modifier.weight(1f)) {
                        onQuickRangeSelected(todayStr, todayStr)
                    }
                    FilterChip("Kemarin", isYesterday, Modifier.weight(1f)) {
                        onQuickRangeSelected(yesterdayStr, yesterdayStr)
                    }
                    FilterChip("7 Hari Terakhir", is7Days, Modifier.weight(1.2f)) {
                        onQuickRangeSelected(last7DaysStr, todayStr)
                    }
                }
            }

            // SECTION 2: Kalender Manual Tanggal Awal dan Akhir (Dipertahankan)
            FilterSection("Rentang Tanggal") {
                DateRangeField(
                    startDate = startDate,
                    endDate = endDate,
                    onStartDateClick = { activeDateField = RiwayatDateField.START },
                    onEndDateClick = { activeDateField = RiwayatDateField.END }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ActionButton(
                    text = "Batal",
                    selected = false,
                    modifier = Modifier.weight(1f),
                    onClick = onCancel
                )
                ActionButton(
                    text = "Terapkan Filter",
                    selected = true,
                    modifier = Modifier.weight(1f),
                    onClick = onApply
                )
            }
        }
    }

    if (activeDateField != null) {
        RiwayatDatePickerDialog(
            initialDate = if (activeDateField == RiwayatDateField.START) startDate else endDate,
            title = if (activeDateField == RiwayatDateField.START) "Pilih tanggal mulai" else "Pilih tanggal akhir",
            onDismiss = { activeDateField = null },
            onConfirm = {
                if (activeDateField == RiwayatDateField.START) {
                    onStartDateClick(it)
                    activeDateField = null
                } else {
                    onEndDateClick(it)
                    activeDateField = null
                }
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
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
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
                    text = "Pilih rentang tanggal kustom",
                    color = DarkBlue,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                DateSelectionChip(
                    label = "Mulai",
                    value = startDate,
                    modifier = Modifier.weight(1f),
                    onClick = onStartDateClick
                )
                DateSelectionChip(
                    label = "Selesai",
                    value = endDate,
                    modifier = Modifier.weight(1f),
                    onClick = onEndDateClick
                )
            }
        }
    }
}

@Composable
private fun RiwayatDatePickerDialog(
    initialDate: String,
    title: String,
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
                        onConfirm(selected.toApiDateString())
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
        Column {
            Text(
                text = title,
                color = DarkBlue,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
            )
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
private fun DateSelectionChip(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        color = Color(0xFFF8FAFE),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFFE3E8F0)),
        modifier = modifier
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = label,
                    color = GreyText,
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = value,
                    color = DarkBlue,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = GreyText,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

private fun String.toMillis(): Long? {
    return parseApiDateOrNull()?.time
}

private fun Long.toApiDateString(): String {
    return Date(this).toApiDate()
}

@Composable
private fun FilterChip(
    text: String,
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
                .padding(horizontal = 8.dp, vertical = 11.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                color = if (selected) SmartBlue else DarkBlue,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp)
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
                state = RiwayatState(),
                startDate = Date().toApiDate(),
                endDate = Date().toApiDate(),
                onReset = {},
                onCancel = {},
                onStartDateClick = {},
                onEndDateClick = {},
                onQuickRangeSelected = { _, _ -> },
                onApply = {}
            )
        }
    }
}
