package com.epy.linespotv2.presentation.input_manual

import com.epy.linespotv2.domain.model.payment.PembayaranOptionType

sealed class InputManualIntent {
    object LoadPage : InputManualIntent()
    object ClickBack : InputManualIntent()
    object ClickCancel : InputManualIntent()

    data class ChangeNomorPolisi(val nomorPolisi: String) : InputManualIntent()
    data class SelectJenisKendaraan(val jenisKendaraan: String) : InputManualIntent()
    data class ChangeWaktuMasuk(val waktuMasuk: String) : InputManualIntent()

    object SubmitInputManual : InputManualIntent()

    object StartPolling : InputManualIntent()
    object StopPolling : InputManualIntent()
    object RefreshStatus : InputManualIntent()

    object ClickPaymentDetail : InputManualIntent()
    data class SelectPaymentOption(val optionType: PembayaranOptionType) : InputManualIntent()
    object ClickPrintReceipt : InputManualIntent()
}
