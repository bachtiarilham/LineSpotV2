// presentation/auth/register/RegisterViewModel.kt
package com.epy.linespotv2.presentation.auth.register

import androidx.lifecycle.viewModelScope
import com.epy.linespotv2.core.base.BaseViewModel
import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.auth.RegisterReqModel
import com.epy.linespotv2.domain.usecase.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val doRegisterUseCase: RegisterUseCase
) : BaseViewModel<RegisterIntent, RegisterState>(RegisterState()) {

    override fun onIntent(intent: RegisterIntent) {
        when (intent) {
            is RegisterIntent.OnFullNameChanged ->
                updateState { it.copy(fullName = intent.fullName, error = null) }

            is RegisterIntent.OnNikChanged ->
                updateState { it.copy(nik = intent.nik, error = null) }

            is RegisterIntent.OnEmailChanged ->
                updateState { it.copy(email = intent.email, error = null) }

            is RegisterIntent.OnPhoneChanged ->
                updateState { it.copy(phone = intent.phone, error = null) }

            is RegisterIntent.OnUsernameChanged ->
                updateState { it.copy(username = intent.username, error = null) }

            is RegisterIntent.OnPasswordChanged ->
                updateState { it.copy(password = intent.password, error = null) }

            is RegisterIntent.OnConfirmPasswordChanged ->
                updateState { it.copy(confirmPassword = intent.confirmPassword, error = null) }

            is RegisterIntent.OnTogglePasswordVisibility ->
                updateState { it.copy(isPasswordVisible = !it.isPasswordVisible) }

            is RegisterIntent.OnToggleConfirmPasswordVisibility ->
                updateState { it.copy(isConfirmPasswordVisible = !it.isConfirmPasswordVisible) }

            is RegisterIntent.OnRegisterClicked -> performRegister()

            is RegisterIntent.OnLoginClicked ->
                updateState { it.copy(registerEffect = RegisterEffect.NavigateBack) }
        }
    }

    private fun performRegister() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true, error = null) }
            val s = state.value
            when (val result = doRegisterUseCase(
                    reqModel = RegisterReqModel(
                        fullName        = s.fullName,
                        nik             = s.nik,
                        email           = s.email,
                        phone           = s.phone,
                        username        = s.username,
                        password        = s.password,
                        confirmPassword = s.confirmPassword
                    )
                )
            ) {
                is ApiCondition.AppSuccess ->
                    updateState {
                        it.copy(
                            isLoading      = false,
                            registerEffect = RegisterEffect.NavigateToLogin
                        )
                    }
                is ApiCondition.AppFailure ->
                    updateState {
                        it.copy(
                            isLoading = false,
                            error     = result.exception.message
                        )
                    }
                is ApiCondition.AppLoading -> {}
            }
        }
    }

    fun consumeEffect() = updateState { it.copy(registerEffect = null) }
}