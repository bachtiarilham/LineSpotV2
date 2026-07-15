package com.epy.linespotv2.presentation.riwayat.ui_model

data class RiwayatFilterUiModel(
    val lokasiOptions: List<String> = listOf("Semua Area"),
    val selectedLokasi: String = "Semua Area",
    val selectedVehicle: RiwayatVehicleFilter = RiwayatVehicleFilter.ALL,
    val selectedPayment: RiwayatPaymentFilter = RiwayatPaymentFilter.ALL
)

enum class RiwayatPaymentFilter(
    val code: String,
    val label: String
) {
    ALL("ALL", "Semua"),
    TUNAI("TUNAI", "Tunai"),
    NON_TUNAI("NON_TUNAI", "Non Tunai")
}

enum class RiwayatVehicleFilter(
    val code: String,
    val label: String
) {
    ALL("ALL", "Semua"),
    MOTOR("MOTOR", "Motor"),
    MOBIL("MOBIL", "Mobil")
}

