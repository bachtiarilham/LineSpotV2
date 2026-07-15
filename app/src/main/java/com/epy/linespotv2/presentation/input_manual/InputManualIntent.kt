package com.epy.linespotv2.presentation.input_manual

sealed class InputManualIntent {
    object LoadPage : InputManualIntent()
    object ClickBack : InputManualIntent()
    object ClickCancel : InputManualIntent()

    data class ChangeNomorPolisi(val nomorPolisi: String) : InputManualIntent()
    data class SelectJenisKendaraan(val jenisKendaraan: String) : InputManualIntent()

    object SubmitInputManual : InputManualIntent()

    object StartPolling : InputManualIntent()
    object StopPolling : InputManualIntent()
    object RefreshStatus : InputManualIntent()

    object ClickPaymentDetail : InputManualIntent()
    data class SelectPaymentOption(val optionType: String) : InputManualIntent()
    object ClickPrintReceipt : InputManualIntent()
}
