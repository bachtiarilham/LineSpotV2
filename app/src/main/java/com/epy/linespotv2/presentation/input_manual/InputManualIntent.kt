package com.epy.linespotv2.presentation.input_manual

sealed class InputManualIntent {
    data object LoadPage : InputManualIntent()
    data object ClickBack : InputManualIntent()
    data object ClickCancel : InputManualIntent()
    data class ChangeNomorPolisi(val nomorPolisi: String) : InputManualIntent()
    data class SelectJenisKendaraan(val jenisKendaraan: String) : InputManualIntent()
    data class SelectArea(val areaName: String) : InputManualIntent()
    data object SubmitInputManual : InputManualIntent()
    data object StartPolling : InputManualIntent()
    data object StopPolling : InputManualIntent()
    data object RefreshStatus : InputManualIntent()
    data object ClickPaymentDetail : InputManualIntent()
    data class SelectPaymentOption(val optionType: String) : InputManualIntent()
    data object ClickPrintReceipt : InputManualIntent()
}
