package com.epy.linespotv2.presentation.topup

sealed interface TopUpEffect {
    data object NavigateBack : TopUpEffect
    data class ShowMessage(val message: String) : TopUpEffect
    data class TopUpCreated(val topupCode: String) : TopUpEffect
    data class TopUpPaid(val topupCode: String) : TopUpEffect
    data class TopUpFailed(val message: String) : TopUpEffect
}
