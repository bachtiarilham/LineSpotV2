package com.epy.linespotv2.presentation.home_customer

import androidx.lifecycle.viewModelScope
import com.epy.linespotv2.core.base.BaseViewModel
import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.usecase.home.CustomerHomeUseCase
import com.epy.linespotv2.presentation.home_customer.ui_model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeCustomerViewModel @Inject constructor(
    private val doHomeUseCase: CustomerHomeUseCase
) : BaseViewModel<HomeCustomerIntent, HomeCustomerState>(HomeCustomerState()) {

    override fun onIntent(intent: HomeCustomerIntent) {
        when (intent) {
            is HomeCustomerIntent.LoadHomeCustomer -> loadHome()
            is HomeCustomerIntent.clickProfile -> sendEffect(HomeCustomerEffect.NavigateToSettings)
            is HomeCustomerIntent.clickSubscribe -> sendEffect(HomeCustomerEffect.NavigateToSubscription)
            is HomeCustomerIntent.clickTopUp -> sendEffect(HomeCustomerEffect.NavigateToTopUp)
            is HomeCustomerIntent.clickPayment -> sendEffect(HomeCustomerEffect.NavigateToPayment)
            
            // Klik Booking & Promo akan menampilkan dialog Fitur Belum Tersedia
            is HomeCustomerIntent.clickBooking -> {
                updateState { it.copy(showFeatureUnavailableDialog = true, unavailableFeatureName = "Booking Parkir") }
            }
            is HomeCustomerIntent.clickPromo -> {
                updateState { it.copy(showFeatureUnavailableDialog = true, unavailableFeatureName = "Promo") }
            }
            
            is HomeCustomerIntent.clickLayananLain -> sendEffect(HomeCustomerEffect.NavigateToLayananLain)
            is HomeCustomerIntent.clickNotification -> {
                sendEffect(HomeCustomerEffect.ShowToast(message = "Notifikasi diklik"))
            }
            is HomeCustomerIntent.dismissError -> updateState { it.copy(error = null) }
            
            // Handle dialog toggle
            is HomeCustomerIntent.showFeatureUnavailable -> {
                updateState { it.copy(showFeatureUnavailableDialog = true, unavailableFeatureName = intent.featureName) }
            }
            is HomeCustomerIntent.dismissFeatureUnavailable -> {
                updateState { it.copy(showFeatureUnavailableDialog = false, unavailableFeatureName = "") }
            }
        }
    }

    fun consumeEffect() {
        updateState { it.copy(customerHomeEffect = null) }
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
                                customerHomeModel = result.data,
                                uiModel = result.data.toUiModel(),
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
        updateState { it.copy(customerHomeEffect = effect) }
    }

    private fun String.isSessionExpiredMessage(): Boolean {
        return contains("autentikasi gagal", ignoreCase = true) ||
            contains("login kembali", ignoreCase = true)
    }
}
