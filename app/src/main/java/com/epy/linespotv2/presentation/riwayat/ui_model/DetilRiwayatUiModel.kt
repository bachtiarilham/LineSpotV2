package com.epy.linespotv2.presentation.riwayat.ui_model

data class DetilRiwayatUiModel(
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
    val officerName: String,
    val officerRole: String,
    val officerIdLabel: String,
    val officerIdValue: String,
    val noteLabel: String,
    val noteValue: String
)

data class DetilRiwayatInfoItemUiModel(
    val label: String,
    val value: String,
    val emphasized: Boolean = false
)
