package com.epy.linespotv2.presentation.home_customer

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
            is HomeIntent.LoadHome -> loadHome(isRefresh = false)
            is HomeIntent.clickProfile -> sendEffect(HomeEffect.NavigateToSettings)
            is HomeIntent.clickSubscribe -> sendEffect(HomeEffect.NavigateToSubscription)
            is HomeIntent.clickTopUp -> sendEffect(HomeEffect.NavigateToTopUp)
            is HomeIntent.clickPayment -> sendEffect(HomeEffect.NavigateToPayment)
            is HomeIntent.clickBooking -> sendEffect(HomeEffect.NavigateToBooking)
            is HomeIntent.clickPromo -> sendEffect(HomeEffect.NavigateToPromo)
            is HomeIntent.clickLayananLain -> sendEffect(HomeEffect.NavigateToLayananLain)
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
