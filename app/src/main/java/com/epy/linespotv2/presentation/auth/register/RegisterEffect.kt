package com.epy.linespotv2.presentation.auth.register

sealed interface RegisterEffect {
    data object NavigateToLogin : RegisterEffect
    data object NavigateBack : RegisterEffect
    data class ShowMessage(val message: String) : RegisterEffect
}
