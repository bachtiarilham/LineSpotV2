package com.epy.linespotv2.presentation.riwayat

import androidx.lifecycle.viewModelScope
import com.epy.linespotv2.core.base.BaseViewModel
import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.helper.LokasiItemModel
import com.epy.linespotv2.domain.model.helper.LokasiModel
import com.epy.linespotv2.domain.model.riwayat.RiwayatRequestModel
import com.epy.linespotv2.domain.usecase.helper.GetLokasiUseCase
import com.epy.linespotv2.domain.usecase.riwayat.RiwayatUseCase
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
            RiwayatIntent.clickRiwayatDetail -> (Unit)
            is RiwayatIntent.updateVehicleCode -> updateSelectedVehicle(intent.vehicleCode)
            is RiwayatIntent.updatePaymentCode -> updateSelectedPayment(intent.paymentCode)
            is RiwayatIntent.updateLokasi -> updateSelectedLokasi(intent.lokasi)
            is RiwayatIntent.submitFilter -> submitFilter(
                startDate = intent.startDate,
                endDate = intent.endDate,
                paymentCode = intent.paymentCode,
                vehicleCode = intent.vehicleCode,
                lokasiCode = intent.lokasiCode
            )
        }
    }

    fun consumeEffect() {
        updateState { it.copy(riwayatEffect = null) }
    }

    private fun updateSelectedVehicle(vehicle: String) {
        updateState { currentState ->
            currentState.copy(
                selectedVehicleCode = vehicle.ifBlank { "SEMUA" },
                error = null
            )
        }
    }

    private fun updateSelectedPayment(payment: String) {
        updateState { currentState ->
            currentState.copy(
                selectedPaymentCode = payment.ifBlank { "SEMUA" },
                error = null
            )
        }
    }

    private fun updateSelectedLokasi(lokasi: String) {
        val selectedLokasiCode = if (lokasi == "SEMUA") {
            "SEMUA"
        } else {
            state.value.lokasiList
                .firstOrNull { it.namaLokasi.orEmpty().trim() == lokasi }
                ?.lokasiId
                ?.toString()
                ?: "SEMUA"
        }

        updateState { currentState ->
            currentState.copy(
                selectedLokasi = lokasi,
                selectedLokasiCode = selectedLokasiCode,
                error = null
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
                        val lokasiList = result.data.toLokasiItems()
                        val selectedLokasi = currentState.selectedLokasi
                            .takeIf { selected ->
                                selected == "SEMUA" || lokasiList.any {
                                    it.namaLokasi.orEmpty().trim() == selected
                                }
                            }
                            ?: "SEMUA"
                        val selectedLokasiCode = lokasiList
                            .firstOrNull { it.namaLokasi.orEmpty().trim() == selectedLokasi }
                            ?.lokasiId
                            ?.toString()
                            ?: "SEMUA"

                        updateState {
                            it.copy(
                                isLoadingLokasi = false,
                                lokasiList = lokasiList,
                                selectedVehicleCode = currentState.selectedVehicleCode,
                                selectedPaymentCode = currentState.selectedPaymentCode,
                                selectedLokasi = selectedLokasi,
                                selectedLokasiCode = selectedLokasiCode,
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
        paymentCode: String?,
        vehicleCode: String?,
        lokasiCode: String?
    ) {
        viewModelScope.launch {
            val currentState = state.value
            val selectedLokasiItem = currentState.lokasiList.firstOrNull {
                it.lokasiId?.toString() == lokasiCode
            }
            val selectedLokasi = selectedLokasiItem?.namaLokasi.orEmpty()
                .trim()
                .ifBlank { "SEMUA" }

            updateState {
                it.copy(
                    isLoading = true,
                    error = null,
                    selectedLokasi = selectedLokasi,
                    selectedLokasiCode = lokasiCode,
                    selectedPaymentCode = paymentCode ?: "SEMUA",
                    selectedVehicleCode = vehicleCode ?: "SEMUA"
                )
            }

            when (
                val result = doRiwayatUseCase(
                    reqModel = RiwayatRequestModel(
                        startDate = startDate,
                        endDate = endDate,
                        paymentCode = paymentCode ?: "SEMUA",
                        vehicleCode = vehicleCode ?: "SEMUA",
                        lokasiCode = lokasiCode ?: "SEMUA"
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
                            selectedLokasi = selectedLokasi,
                            selectedLokasiCode = lokasiCode,
                            selectedPaymentCode = paymentCode ?: "SEMUA",
                            selectedVehicleCode = vehicleCode ?: "SEMUA"
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

    private fun LokasiModel.toLokasiItems(): List<LokasiItemModel> {
        return lokasi.orEmpty()
            .filter { !it.namaLokasi.isNullOrBlank() }
            .distinctBy { it.lokasiId ?: it.namaLokasi.orEmpty().trim() }
    }
}
