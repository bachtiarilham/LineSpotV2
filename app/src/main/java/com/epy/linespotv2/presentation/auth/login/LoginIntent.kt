package com.epy.linespotv2.presentation.auth.login

sealed interface LoginIntent{
    data class onUsernameChanged (val username : String ) : LoginIntent
    data class onPasswordChanged (val password : String)  : LoginIntent
    object clickLogin : LoginIntent
    object clickRegister : LoginIntent
    object clickForgetPassword: LoginIntent
}