package com.epy.linespotv2.presentation.topup

sealed interface TopUpIntent {
    data object LoadPage : TopUpIntent
    data object ClickBack : TopUpIntent
    data class SelectNominal(val amount: Long) : TopUpIntent
    data class ChangeCustomNominal(val value: String) : TopUpIntent
    data class SelectPaymentMethod(val paymentMethodCode: String) : TopUpIntent
    data object SubmitTopUp : TopUpIntent
    data object StartPolling : TopUpIntent
    data object StopPolling : TopUpIntent
    data object CheckTopUpStatus : TopUpIntent
}
