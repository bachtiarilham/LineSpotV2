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
            val currentState = state.value

            if (currentState.password != currentState.confirmPassword) {
                updateState {
                    it.copy(
                        error = "Konfirmasi password tidak cocok",
                        registerEffect = RegisterEffect.ShowMessage("Konfirmasi password tidak cocok")
                    )
                }
                return@launch
            }

            updateState { it.copy(isLoading = true, error = null) }

            when (
                val result = doRegisterUseCase(
                    reqModel = RegisterReqModel(
                        fullName = currentState.fullName,
                        nik = currentState.nik,
                        email = currentState.email,
                        phone = currentState.phone,
                        username = currentState.username,
                        password = currentState.password,
                        confirmPassword = currentState.password
                    )
                )
            ) {
                is ApiCondition.AppSuccess -> {
                    updateState {
                        it.copy(
                            isLoading = false,
                            error = null,
                            registerEffect = RegisterEffect.ShowMessage(
                                result.data.ifBlank { "Registrasi berhasil" }
                            )
                        )
                    }
                }

                is ApiCondition.AppFailure -> {
                    val message = result.exception.message?.ifBlank { null } ?: "Registrasi gagal"
                    updateState {
                        it.copy(
                            isLoading = false,
                            error = message,
                            registerEffect = RegisterEffect.ShowMessage(message)
                        )
                    }
                }

                is ApiCondition.AppLoading -> Unit
            }
        }
    }

    fun consumeEffect() {
        val currentEffect = state.value.registerEffect
        val shouldNavigateToLogin =
            currentEffect is RegisterEffect.ShowMessage && state.value.error.isNullOrBlank()

        updateState {
            it.copy(
                registerEffect = if (shouldNavigateToLogin) {
                    RegisterEffect.NavigateToLogin
                } else {
                    null
                }
            )
        }
    }
}
