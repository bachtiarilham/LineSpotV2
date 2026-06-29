package com.epy.linespotv2.presentation.input_manual

import com.epy.linespotv2.core.preferences.TarifItem
import com.epy.linespotv2.domain.model.InputManualModel
import com.epy.linespotv2.domain.model.InputManualTarifSummary
import com.epy.linespotv2.domain.model.InputManualVehicleOption
import com.epy.linespotv2.domain.model.InputManualVehicleType
import com.epy.linespotv2.domain.model.PembayaranModel

data class InputManualState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val inputManualEffect: InputManualEffect? = null,
    val availableTarif: List<TarifItem> = emptyList(),
    val inputManualModel: InputManualModel = InputManualModel(
        infoMessage = "Masukkan data kendaraan secara manual jika tiket tidak tersedia atau rusak.",
        placeholderNomorPolisi = "Contoh: B 1234 ABC",
        selectedVehicle = InputManualVehicleType.MOTOR,
        vehicleOptions = listOf(
            InputManualVehicleOption(type = InputManualVehicleType.MOTOR),
            InputManualVehicleOption(type = InputManualVehicleType.MOBIL)
        ),
        tarifSummary = InputManualTarifSummary()
    ),
    val pembayaranModel: PembayaranModel? = null,
    val selectedZonaParkir: String = "",
    val selectedLokasiParkir: String = "",
    val isCheckingPaymentStatus: Boolean = true,
    val paymentStatus : Long = 0L,
    val statusMessage : String = ""
)

fun InputManualState.withSelectedVehicleTarif(
    tarifItems: List<TarifItem> = availableTarif,
    selectedVehicle: InputManualVehicleType = inputManualModel.selectedVehicle
): InputManualState {
    val totalTarif = tarifItems.firstOrNull {
        it.kendaraan.equals(selectedVehicle.label, ignoreCase = true)
    }?.nominal?.toLong() ?: 0L

    return copy(
        availableTarif = tarifItems,
        inputManualModel = inputManualModel.copy(
            selectedVehicle = selectedVehicle,
            tarifSummary = inputManualModel.tarifSummary.copy(
                totalTarif = totalTarif
            )
        )
    )
}
