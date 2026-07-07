package com.epy.linespotv2.presentation.home_jukir

import androidx.lifecycle.viewModelScope
import com.epy.linespotv2.core.base.BaseViewModel
import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.usecase.home.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeJukirViewModel @Inject constructor(
    private val doHomeUseCase: HomeUseCase
) : BaseViewModel<HomeJukirIntent, HomeJukirState>(HomeJukirState()) {

    override fun onIntent(intent: HomeJukirIntent) {
        when (intent) {
            is HomeJukirIntent.loadHomeJukir -> loadHome()
            is HomeJukirIntent.clickProfile -> sendEffect(HomeJukirEffect.NavigateToSettings)
            is HomeJukirIntent.clickBantuan -> sendEffect(HomeJukirEffect.NavigateToBantuan)
            is HomeJukirIntent.clickTopUp -> sendEffect(HomeJukirEffect.NavigateToTopUp)
            is HomeJukirIntent.clickInputManual -> sendEffect(HomeJukirEffect.NavigateToInputManual)
            is HomeJukirIntent.clickLaporan -> sendEffect(HomeJukirEffect.NavigateToLaporan)
            is HomeJukirIntent.clickRiwayat -> sendEffect(HomeJukirEffect.NavigateToRiwayat)
            is HomeJukirIntent.clickScanTiket -> sendEffect(HomeJukirEffect.NavigateToScanTicket)
            is HomeJukirIntent.clickNotification -> sendEffect(HomeJukirEffect.NavigateToNotification)
            is HomeJukirIntent.dismissError -> updateState { it.copy(error = null) }
        }
    }

    fun consumeEffect() {
        updateState { it.copy(homeJukirEffect = null) }
    }

    private fun loadHome() {
        viewModelScope.launch {
            updateState {
                it.copy(
                    isLoading = true,
                    isRefresh = false,
                    error = null
                )
            }

            doHomeUseCase().collect { result ->
                when (result) {
                    is ApiCondition.AppLoading -> {
                        updateState {
                            it.copy(
                                isLoading = true,
                                isRefresh = false,
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
                            sendEffect(HomeJukirEffect.SessionExpired)
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

    private fun sendEffect(effect: HomeJukirEffect) {
        updateState { it.copy(homeJukirEffect = effect) }
    }

    private fun String.isSessionExpiredMessage(): Boolean {
        return contains("autentikasi gagal", ignoreCase = true) ||
            contains("login kembali", ignoreCase = true)
    }
}
