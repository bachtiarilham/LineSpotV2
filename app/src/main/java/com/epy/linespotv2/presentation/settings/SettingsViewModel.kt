// presentation/settings/SettingsViewModel.kt
package com.epy.linespotv2.presentation.settings

import androidx.lifecycle.viewModelScope
import com.epy.linespotv2.core.base.BaseViewModel
import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.preferences.AppPreferences
import com.epy.linespotv2.domain.model.auth.UserModel
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
        userModel = UserModel(
            userId = prefs.userId,
            nik = prefs.nik,
            fullName = prefs.fullName,
            phone = prefs.phone,
            avatar_url = prefs.avatarUrl,
            email = prefs.email,
            username = prefs.username,
            role = prefs.roleId,
            isVerified = false,
            lokasi = prefs.lokasi,
            zona = prefs.zona,
            tarif = TODO(),
            registeredAt = TODO(),
            createdAt = TODO(),
            updatedAt = TODO()
        )
    )
) {
    init {
        onIntent(SettingsIntent.LoadSettingsPage)
    }

    override fun onIntent(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.LoadSettingsPage -> updateState { it.copy(isLoading = true, error = null) }
            is SettingsIntent.OnLogoutClicked  -> updateState { it.copy(showLogoutDialog = true) }
            is SettingsIntent.OnConfirmLogout  -> performLogout()
            is SettingsIntent.OnDismissLogout  -> updateState { it.copy(showLogoutDialog = false) }
            is SettingsIntent.OnBackClicked    -> sendEffect(SettingsEffect.NavigateBack)
            is SettingsIntent.NavigateToProfil -> loadProfilPage()
            is SettingsIntent.NavigateToBantuanFaq -> sendEffect(SettingsEffect.NavigateToBantuanFaq)
            is SettingsIntent.NavigateToPrivasi -> sendEffect(SettingsEffect.NavigateToPrivasi)
            is SettingsIntent.NavigateToMetodePembayaran -> sendEffect(SettingsEffect.NavigateToMetodePembayaran)
            is SettingsIntent.NavigateToSyaratKetentuan -> sendEffect(SettingsEffect.NavigateToSyaratKetentuan)
            is SettingsIntent.NavigateToTentangAplikasi -> sendEffect(SettingsEffect.NavigateToSyaratKetentuan)
            is SettingsIntent.NavigateToKeamanan -> sendEffect(SettingsEffect.NavigateToKeamanan)
        }
    }

    private fun loadProfilPage(){
        updateState { it.copy(isLoading = true, error = null) }
        sendEffect(SettingsEffect.NavigateToProfil)
//        viewModelScope.launch {
//
//            when (val result = getProfileUseCase()) {
//                is ApiCondition.AppSuccess -> {
//                    updateState { it.copy(isLoading = false, userModel = result.data) }
//
//                }
//                is ApiCondition.AppFailure ->
//                    updateState { it.copy(isLoading = false, error = result.exception.message) }
//                is ApiCondition.AppLoading -> {}
//            }
//        }
    }

    private fun loadSettingsPage() {
        updateState { it.copy(isLoading = true, error = null) }
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