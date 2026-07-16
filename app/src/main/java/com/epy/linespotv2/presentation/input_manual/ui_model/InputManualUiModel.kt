package com.epy.linespotv2.presentation.input_manual.ui_model

data class InputManualUiModel(
    val title: String = "Input Manual",
    val infoMessage: String = "Masukkan data kendaraan secara manual jika tiket tidak tersedia atau rusak.",
    val nomorPolisi: String = "",
    val nomorPolisiPlaceholder: String = "Contoh: B 1234 ABC",
    val selectedVehicle: InputManualVehicleUiFilter = InputManualVehicleUiFilter.MOTOR,
    val vehicleOptions: List<InputManualVehicleUiFilter> = InputManualVehicleUiFilter.entries,
    val waktuMasuk: String = "-",
    val areaOptions: List<String> = emptyList(),
    val areaParkir: String = "-",
    val totalTarifLabel: String = "Rp 0",
    val submitLabel: String = "Simpan & Proses Pembayaran",
    val cancelLabel: String = "Batal"
)

enum class InputManualVehicleUiFilter(
    val code: String,
    val label: String
) {
    MOTOR("MOTOR", "Motor"),
    MOBIL("MOBIL", "Mobil")
}
