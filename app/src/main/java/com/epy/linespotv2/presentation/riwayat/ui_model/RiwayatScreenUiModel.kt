package com.epy.linespotv2.presentation.riwayat.ui_model

data class RiwayatScreenUiModel(
    val sections: List<RiwayatSectionUiModel> = emptyList()
)

data class RiwayatSectionUiModel(
    val date: String,
    val items: List<RiwayatItemUiModel> = emptyList()
)

data class RiwayatItemUiModel(
    val code: String,
    val plateNumber: String,
    val vehicleType: String,
    val time: String,
    val amountLabel: String,
    val isEntry: Boolean,
    val statusLabel: String
)
