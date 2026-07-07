package com.epy.linespotv2.presentation.home_customer

import androidx.lifecycle.viewModelScope
import com.epy.linespotv2.core.base.BaseViewModel
import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.usecase.home.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeCustomerViewModel @Inject constructor(
    private val doHomeUseCase: HomeUseCase
) : BaseViewModel<HomeCustomerIntent, HomeCustomerState>(HomeCustomerState()) {

    override fun onIntent(intent: HomeCustomerIntent) {
        when (intent) {
            is HomeCustomerIntent.LoadHomeCustomer -> loadHome(isRefresh = false)
            is HomeCustomerIntent.clickProfile -> sendEffect(HomeCustomerEffect.NavigateToSettings)
            is HomeCustomerIntent.clickSubscribe -> sendEffect(HomeCustomerEffect.NavigateToSubscription)
            is HomeCustomerIntent.clickTopUp -> sendEffect(HomeCustomerEffect.NavigateToTopUp)
            is HomeCustomerIntent.clickPayment -> sendEffect(HomeCustomerEffect.NavigateToPayment)
            is HomeCustomerIntent.clickBooking -> sendEffect(HomeCustomerEffect.NavigateToBooking)
            is HomeCustomerIntent.clickPromo -> sendEffect(HomeCustomerEffect.NavigateToPromo)
            is HomeCustomerIntent.clickLayananLain -> sendEffect(HomeCustomerEffect.NavigateToLayananLain)
            is HomeCustomerIntent.clickNotification -> sendEffect(
                HomeCustomerEffect.ShowToast(message = "Notifikasi diklik")
            )
            is HomeCustomerIntent.dismissError -> updateState { it.copy(error = null) }
        }
    }

    fun consumeEffect() {
        updateState { it.copy(homeCustomerEffect = null) }
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
                            sendEffect(HomeCustomerEffect.SessionExpired)
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

    private fun sendEffect(effect: HomeCustomerEffect) {
        updateState { it.copy(homeCustomerEffect = effect) }
    }

    private fun String.isSessionExpiredMessage(): Boolean {
        return contains("autentikasi gagal", ignoreCase = true) ||
            contains("login kembali", ignoreCase = true)
    }
}
