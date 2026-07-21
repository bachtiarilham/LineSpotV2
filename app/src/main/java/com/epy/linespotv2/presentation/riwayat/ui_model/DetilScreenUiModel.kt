package com.epy.linespotv2.presentation.riwayat.ui_model

data class DetilScreenUiModel(
    val ticketCode: String,
    val statusChipLabel: String,
    val statusLabel: String,
    val statusDateTime: String,
    val vehicleInitial: String,
    val vehicleType: String,
    val totalAmountLabel: String,
    val isEntry: Boolean,
    val ticketInfoItems: List<DetilRiwayatInfoItemUiModel>,
    val paymentMethodLabel: String,
    val paymentAmountLabel: String,
    val noteLabel: String,
    val noteValue: String,
    
    // Properti Opsional untuk Opsi Unduh Bukti PDF
    val showDownloadPdfButton: Boolean = false
)

data class DetilRiwayatInfoItemUiModel(
    val label: String,
    val value: String,
    val emphasized: Boolean = false
)
