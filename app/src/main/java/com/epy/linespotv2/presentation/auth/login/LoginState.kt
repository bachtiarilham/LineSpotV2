package com.epy.linespotv2.presentation.auth.login

data class LoginState (
    val identity : String = "",
    val password : String = "",
    val isLoading : Boolean = false,
    val error : String? = "",
    val loginEffect: LoginEffect? = null
)