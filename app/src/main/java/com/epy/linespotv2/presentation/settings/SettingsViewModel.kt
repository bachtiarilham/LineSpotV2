// presentation/settings/SettingsViewModel.kt
package com.epy.linespotv2.presentation.settings

import androidx.lifecycle.viewModelScope
import com.epy.linespotv2.core.base.BaseViewModel
import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.preferences.AppPreferences
import com.epy.linespotv2.domain.model.UserModel
import com.epy.linespotv2.domain.usecase.user.GetProfileUseCase
import com.epy.linespotv2.domain.usecase.user.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val prefs: AppPreferences
) : BaseViewModel<SettingsIntent, SettingsState>(
    // Tampil data lokal dari prefs dulu sebelum API selesai
    initialState = SettingsState(
        profile = UserModel(
            userId     = prefs.userId,
            nik        = prefs.nik,
            fullName   = prefs.fullName,
            phone      = prefs.phone,
            email      = prefs.email,
            username   = prefs.username,
            role       = prefs.roleId,
            isVerified = false
        )
    )
) {
    init {
        onIntent(SettingsIntent.LoadProfile)
    }

    override fun onIntent(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.LoadProfile      -> loadProfile()
            is SettingsIntent.OnLogoutClicked  -> updateState { it.copy(showLogoutDialog = true) }
            is SettingsIntent.OnConfirmLogout  -> performLogout()
            is SettingsIntent.OnDismissLogout  -> updateState { it.copy(showLogoutDialog = false) }
            is SettingsIntent.OnBackClicked    -> sendEffect(SettingsEffect.NavigateBack)
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true, error = null) }
            when (val result = getProfileUseCase()) {
                is ApiCondition.AppSuccess ->
                    updateState { it.copy(isLoading = false, profile = result.data) }
                is ApiCondition.AppFailure ->
                    updateState { it.copy(isLoading = false, error = result.exception.message) }
                is ApiCondition.AppLoading -> {}
            }
        }
    }

    private fun performLogout() {
        viewModelScope.launch {
            logoutUseCase()
            sendEffect(SettingsEffect.NavigateToLogin)
        }
    }

    private fun sendEffect(effect: SettingsEffect) =
        updateState { it.copy(settingsEffect = effect) }

    fun consumeEffect() = updateState { it.copy(settingsEffect = null) }
}