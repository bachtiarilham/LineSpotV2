package com.epy.linespotv2.presentation.auth.login

sealed interface LoginEffect {
    object NavigateToJukirHome : LoginEffect
    object NavigateToCustomerHome : LoginEffect
    object NavigateToRegister : LoginEffect
    data class ShowToast(val message: String) : LoginEffect

}