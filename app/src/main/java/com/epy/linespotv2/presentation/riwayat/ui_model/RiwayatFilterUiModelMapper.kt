package com.epy.linespotv2.presentation.riwayat.ui_model

fun List<String>.toRiwayatFilterUiModel(
    selectedLokasi: String,
    selectedVehicle: RiwayatVehicleFilter,
    selectedPayment: RiwayatPaymentFilter
): RiwayatFilterUiModel {
    val options = if (isEmpty()) listOf("Semua Area") else this
    return RiwayatFilterUiModel(
        lokasiOptions = options,
        selectedLokasi = selectedLokasi.takeIf { options.contains(it) } ?: options.first(),
        selectedVehicle = selectedVehicle,
        selectedPayment = selectedPayment
    )
}
