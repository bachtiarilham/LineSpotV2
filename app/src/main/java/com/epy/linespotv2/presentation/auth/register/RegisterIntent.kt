package com.epy.linespotv2.presentation.auth.register

sealed class RegisterIntent {
    data class OnFullNameChanged(val fullName: String)             : RegisterIntent()
    data class OnNikChanged(val nik: String)                       : RegisterIntent()
    data class OnEmailChanged(val email: String)                   : RegisterIntent()
    data class OnPhoneChanged(val phone: String)                   : RegisterIntent()
    data class OnUsernameChanged(val username: String)             : RegisterIntent()
    data class OnPasswordChanged(val password: String)             : RegisterIntent()
    data class OnConfirmPasswordChanged(val confirmPassword: String) : RegisterIntent()
    data object OnRegisterClicked                                       : RegisterIntent()
    data object OnLoginClicked                                          : RegisterIntent()
    data object OnTogglePasswordVisibility                              : RegisterIntent()
    data object OnToggleConfirmPasswordVisibility                       : RegisterIntent()
}