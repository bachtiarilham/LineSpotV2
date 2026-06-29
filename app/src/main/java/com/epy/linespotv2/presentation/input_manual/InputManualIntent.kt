package com.epy.linespotv2.presentation.input_manual

import com.epy.linespotv2.domain.model.PembayaranOptionType

sealed class InputManualIntent {
    object LoadPage : InputManualIntent()
    object ClickBack : InputManualIntent()
    object ClickCancel : InputManualIntent()

    data class ChangeNomorPolisi(val nomorPolisi: String) : InputManualIntent()
    data class SelectJenisKendaraan(val jenisKendaraan: String) : InputManualIntent()
    data class ChangeWaktuMasuk(val waktuMasuk: String) : InputManualIntent()
    data class SelectZonaParkir(val zonaParkir: String) : InputManualIntent()
    data class SelectLokasiParkir(val lokasiParkir: String) : InputManualIntent()

    object SubmitInputManual : InputManualIntent()

    object StartPolling : InputManualIntent()
    object StopPolling : InputManualIntent()
    object RefreshStatus : InputManualIntent()

    object ClickPaymentDetail : InputManualIntent()
    data class SelectPaymentOption(val optionType: PembayaranOptionType) : InputManualIntent()
    object ClickPrintReceipt : InputManualIntent()
}
