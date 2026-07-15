package com.epy.linespotv2.presentation.auth.login

import androidx.lifecycle.viewModelScope
import com.epy.linespotv2.core.base.BaseViewModel
import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.auth.LoginReqModel
import com.epy.linespotv2.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val doLoginUseCase: LoginUseCase
) : BaseViewModel<LoginIntent, LoginState>(LoginState()) {

    override fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.onUsernameChanged ->
                updateState { it.copy(identity = intent.username, error = null) }

            is LoginIntent.onPasswordChanged ->
                updateState { it.copy(password = intent.password, error = null) }

            is LoginIntent.clickLogin    -> performLogin()

            is LoginIntent.clickRegister ->
                updateState { it.copy(loginEffect = LoginEffect.NavigateToRegister) }

            is LoginIntent.clickForgetPassword -> { /* TODO */ }
        }
    }

    private fun performLogin() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true, error = null) }

            when (val result = doLoginUseCase(
                    reqModel = LoginReqModel(
                        state.value.identity,
                        state.value.password
                    )
                )
            ) {
                is ApiCondition.AppSuccess ->{
                    val loginData = result.data

                    val navigationEffect = when (loginData.roleId) {
                        1L -> LoginEffect.NavigateToCustomerHome
                        2L -> LoginEffect.NavigateToJukirHome
                        else -> {
                            LoginEffect.ShowToast("Role tidak dikenali")
                        }
                    }
                    updateState { it.copy(isLoading = false, loginEffect = navigationEffect) }
                }
                is ApiCondition.AppFailure ->
                    updateState { it.copy(isLoading = false, error = result.exception.message) }

                is ApiCondition.AppLoading -> {}
            }
        }
    }

    // Dipanggil UI setelah effect dikonsumsi agar tidak trigger ulang saat recompose
    fun consumeEffect() = updateState { it.copy(loginEffect = null) }
}