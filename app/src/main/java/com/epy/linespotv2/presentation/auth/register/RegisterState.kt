package com.epy.linespotv2.presentation.auth.register

data class RegisterState(
    val fullName: String        = "",
    val nik: String             = "",
    val email: String           = "",
    val phone: String           = "",
    val username: String        = "",
    val password: String        = "",
    val confirmPassword: String = "",
    val isPasswordVisible: Boolean        = false,
    val isConfirmPasswordVisible: Boolean = false,
    val isLoading: Boolean      = false,
    val error: String?          = null,
    val registerEffect: RegisterEffect? = null
)