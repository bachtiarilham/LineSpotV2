package com.epy.linespotv2.presentation.input_manual

import com.epy.linespotv2.domain.model.payment.PembayaranOptionType

sealed class InputManualEffect {
    object NavigateBack : InputManualEffect()
    object NavigateToPembayaran : InputManualEffect()
    object NavigateToPaymentDetail : InputManualEffect()
    data class NavigateToPaymentMethod(val optionType: PembayaranOptionType) : InputManualEffect()
    object PrintReceipt : InputManualEffect()
    data class ShowToast(val message: String) : InputManualEffect()
    object ShowPaymentSuccess : InputManualEffect()
    object ShowPaymentFailed : InputManualEffect()
}
