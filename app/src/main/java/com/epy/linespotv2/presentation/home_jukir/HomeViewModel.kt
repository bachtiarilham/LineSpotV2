package com.epy.linespotv2.presentation.home_jukir

import androidx.lifecycle.viewModelScope
import com.epy.linespotv2.core.base.BaseViewModel
import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.usecase.home.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val doHomeUseCase: HomeUseCase
) : BaseViewModel<HomeIntent, HomeState>(HomeState()) {

    override fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.loadHome -> loadHome(isRefresh = false)
            is HomeIntent.clickProfile -> sendEffect(HomeEffect.NavigateToSettings)
            is HomeIntent.clickBantuan -> sendEffect(HomeEffect.NavigateToBantuan)
            is HomeIntent.clickTopUp -> sendEffect(HomeEffect.NavigateToTopUp)
            is HomeIntent.clickInputManual -> sendEffect(HomeEffect.NavigateToInputManual)
            is HomeIntent.clickLaporan -> sendEffect(HomeEffect.NavigateToLaporan)
            is HomeIntent.clickRiwayat -> sendEffect(HomeEffect.NavigateToRiwayat)
            is HomeIntent.clickScanTiket -> sendEffect(HomeEffect.NavigateToScanTicket)
            is HomeIntent.clickNotification -> sendEffect(
                HomeEffect.ShowToast(message = "Notifikasi diklik")
            )
            is HomeIntent.dismissError -> updateState { it.copy(error = null) }
        }
    }

    fun consumeEffect() {
        updateState { it.copy(homeEffect = null) }
    }

    private fun loadHome(isRefresh: Boolean = false) {
        viewModelScope.launch {
            updateState {
                it.copy(
                    isLoading = !isRefresh,
                    isRefresh = isRefresh,
                    error = null
                )
            }

            doHomeUseCase().collect { result ->
                when (result) {
                    is ApiCondition.AppLoading -> {
                        updateState {
                            it.copy(
                                isLoading = !isRefresh,
                                isRefresh = isRefresh,
                                error = null
                            )
                        }
                    }

                    is ApiCondition.AppSuccess -> {
                        updateState {
                            it.copy(
                                isLoading = false,
                                isRefresh = false,
                                homeResponseModel = result.data,
                                error = null
                            )
                        }
                    }

                    is ApiCondition.AppFailure -> {
                        val errorMessage = result.exception.message ?: "Terjadi kesalahan"

                        if (errorMessage.isSessionExpiredMessage()) {
                            sendEffect(HomeEffect.SessionExpired)
                        }

                        updateState {
                            it.copy(
                                isLoading = false,
                                isRefresh = false,
                                error = errorMessage
                            )
                        }
                    }
                }
            }
        }
    }

    private fun sendEffect(effect: HomeEffect) {
        updateState { it.copy(homeEffect = effect) }
    }

    private fun String.isSessionExpiredMessage(): Boolean {
        return contains("autentikasi gagal", ignoreCase = true) ||
            contains("login kembali", ignoreCase = true)
    }
}
