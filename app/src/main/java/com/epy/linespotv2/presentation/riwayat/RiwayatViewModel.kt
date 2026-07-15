package com.epy.linespotv2.presentation.riwayat

import androidx.lifecycle.viewModelScope
import com.epy.linespotv2.core.base.BaseViewModel
import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.helper.LokasiModel
import com.epy.linespotv2.domain.model.riwayat.RiwayatRequestModel
import com.epy.linespotv2.domain.usecase.helper.GetLokasiUseCase
import com.epy.linespotv2.domain.usecase.riwayat.RiwayatUseCase
import com.epy.linespotv2.presentation.riwayat.ui_model.RiwayatPaymentFilter
import com.epy.linespotv2.presentation.riwayat.ui_model.RiwayatVehicleFilter
import com.epy.linespotv2.presentation.riwayat.ui_model.toRiwayatFilterUiModel
import com.epy.linespotv2.presentation.riwayat.ui_model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RiwayatViewModel @Inject constructor(
    private val doRiwayatUseCase: RiwayatUseCase,
    private val doGetLokasiUseCase: GetLokasiUseCase
) : BaseViewModel<RiwayatIntent, RiwayatState>(RiwayatState()) {

    override fun onIntent(intent: RiwayatIntent) {
        when (intent) {
            RiwayatIntent.loadPage -> loadRiwayatPage()
            RiwayatIntent.loadFilterPage -> loadFilterPage()
            RiwayatIntent.clickRiwayatDetail -> sendEffect(RiwayatEffect.NavigateToDetail)
            is RiwayatIntent.selectVehicle -> updateSelectedVehicle(intent.vehicle)
            is RiwayatIntent.selectPayment -> updateSelectedPayment(intent.payment)
            is RiwayatIntent.selectLokasi -> updateSelectedLokasi(intent.lokasi)
            is RiwayatIntent.submitFilter -> submitFilter(
                startDate = intent.startDate,
                endDate = intent.endDate,
                payment = intent.payment.toRiwayatPaymentFilter(),
                vehicle = intent.vehicle.toRiwayatVehicleFilter(),
                lokasi = intent.lokasi
            )
        }
    }

    fun consumeEffect() {
        updateState { it.copy(riwayatEffect = null) }
    }

    private fun updateSelectedVehicle(vehicle: String) {
        val selectedVehicle = vehicle.toRiwayatVehicleFilter()
        updateState { currentState ->
            currentState.copy(
                selectedVehicle = selectedVehicle,
                error = null,
                filterUiModel = currentState.lokasiList.toRiwayatFilterUiModel(
                    selectedLokasi = currentState.selectedLokasi,
                    selectedVehicle = selectedVehicle,
                    selectedPayment = currentState.selectedPayment
                )
            )
        }
    }

    private fun updateSelectedPayment(payment: String) {
        val selectedPayment = payment.toRiwayatPaymentFilter()
        updateState { currentState ->
            currentState.copy(
                selectedPayment = selectedPayment,
                error = null,
                filterUiModel = currentState.lokasiList.toRiwayatFilterUiModel(
                    selectedLokasi = currentState.selectedLokasi,
                    selectedVehicle = currentState.selectedVehicle,
                    selectedPayment = selectedPayment
                )
            )
        }
    }

    private fun updateSelectedLokasi(lokasi: String) {
        updateState { currentState ->
            currentState.copy(
                selectedLokasi = lokasi,
                error = null,
                filterUiModel = currentState.lokasiList.toRiwayatFilterUiModel(
                    selectedLokasi = lokasi,
                    selectedVehicle = currentState.selectedVehicle,
                    selectedPayment = currentState.selectedPayment
                )
            )
        }
    }

    private fun loadRiwayatPage() {
        updateState {
            it.copy(
                isLoading = false,
                isRefresh = false,
                error = null
            )
        }
    }

    private fun loadFilterPage() {
        viewModelScope.launch {
            doGetLokasiUseCase().collectLatest { result ->
                when (result) {
                    is ApiCondition.AppLoading -> {
                        updateState {
                            it.copy(
                                isLoadingLokasi = true,
                                errorLokasi = null
                            )
                        }

                    }

                    is ApiCondition.AppSuccess -> {
                        val currentState = state.value

                        updateState {
                            it.copy(
                                isLoadingLokasi = false,
                                selectedVehicle = currentState.selectedVehicle,
                                selectedPayment = currentState.selectedPayment,
                                errorLokasi = null
                            )
                        }
                    }

                    is ApiCondition.AppFailure -> {
                        updateState {
                            it.copy(
                                isLoadingLokasi = false,
                                errorLokasi = result.exception.message ?: "Gagal memuat lokasi"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun sendEffect(effect: RiwayatEffect) {
        updateState { it.copy(riwayatEffect = effect) }
    }

    private fun submitFilter(
        startDate: String,
        endDate: String,
        payment: RiwayatPaymentFilter,
        vehicle: RiwayatVehicleFilter,
        lokasi: String
    ) {
        viewModelScope.launch {
            updateState {
                it.copy(
                    isLoading = true,
                    error = null,
                    selectedLokasi = lokasi,
                    selectedPayment = payment,
                    selectedVehicle = vehicle
                )
            }

            when (
                val result = doRiwayatUseCase(
                    reqModel = RiwayatRequestModel(
                        startDate = startDate,
                        endDate = endDate,
                        paymentCode = payment.code.takeUnless { it == RiwayatPaymentFilter.ALL.code },
                        vehicleCode = vehicle.code.takeUnless { it == RiwayatVehicleFilter.ALL.code },
                        lokasiCode = lokasi.takeUnless { it == "Semua Area" }
                    ),
                )
            ) {
                is ApiCondition.AppSuccess -> {
                    updateState {
                        it.copy(
                            isLoading = false,
                            isRefresh = false,
                            riwayatResponseModel = result.data,
                            screenUiModel = result.data.toUiModel(),
                            error = null,
                            selectedLokasi = lokasi,
                            selectedPayment = payment,
                            selectedVehicle = vehicle,
                            filterUiModel = it.lokasiList.toRiwayatFilterUiModel(
                                selectedLokasi = lokasi,
                                selectedVehicle = vehicle,
                                selectedPayment = payment
                            )
                        )
                    }
                    sendEffect(RiwayatEffect.NavigateToRiwayat)
                }

                is ApiCondition.AppFailure -> {
                    updateState {
                        it.copy(
                            isLoading = false,
                            isRefresh = false,
                            error = result.exception.message ?: "Terjadi kesalahan"
                        )
                    }
                }

                is ApiCondition.AppLoading -> {
                    updateState {
                        it.copy(
                            isLoading = true,
                            error = null
                        )
                    }
                }
            }
        }
    }

    private fun String.toRiwayatPaymentFilter(): RiwayatPaymentFilter {
        return RiwayatPaymentFilter.entries.firstOrNull { it.name == this || it.code == this }
            ?: RiwayatPaymentFilter.ALL
    }

    private fun String.toRiwayatVehicleFilter(): RiwayatVehicleFilter {
        return RiwayatVehicleFilter.entries.firstOrNull { it.name == this || it.code == this }
            ?: RiwayatVehicleFilter.ALL
    }
}
