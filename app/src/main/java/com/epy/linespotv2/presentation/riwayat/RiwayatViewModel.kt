package com.epy.linespotv2.presentation.riwayat

import androidx.lifecycle.viewModelScope
import com.epy.linespotv2.core.base.BaseViewModel
import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.preferences.AppPreferences
import com.epy.linespotv2.domain.model.helper.LokasiModel
import com.epy.linespotv2.domain.model.riwayat.RiwayatPaymentFilter
import com.epy.linespotv2.domain.model.riwayat.RiwayatRequestModel
import com.epy.linespotv2.domain.model.riwayat.RiwayatVehicleFilter
import com.epy.linespotv2.domain.usecase.helper.GetLokasiUseCase
import com.epy.linespotv2.domain.usecase.riwayat.RiwayatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RiwayatViewModel @Inject constructor(
    private val doRiwayatUseCase: RiwayatUseCase,
    private val doGetLokasiUseCase: GetLokasiUseCase,
    private val prefs: AppPreferences
) : BaseViewModel<RiwayatIntent, RiwayatState>(RiwayatState()) {

    override fun onIntent(intent: RiwayatIntent) {
        when (intent) {
            RiwayatIntent.loadPage -> loadRiwayatPage()
            RiwayatIntent.loadFilterPage -> loadFilterPage()
            RiwayatIntent.clickRiwayatDetail -> sendEffect(RiwayatEffect.NavigateToDetail)
            is RiwayatIntent.selectVehicle -> updateState { it.copy(selectedVehicle = intent.vehicle, error = null) }
            is RiwayatIntent.selectPayment -> updateState { it.copy(selectedPayment = intent.payment, error = null) }
            is RiwayatIntent.selectLokasi -> updateState { it.copy(selectedLokasi = intent.lokasi, error = null) }
            is RiwayatIntent.submitFilter -> submitFilter(
                startDate = intent.startDate,
                endDate = intent.endDate,
                payment = intent.payment,
                vehicle = intent.vehicle,
                lokasi = intent.lokasi
            )
        }
    }

    fun consumeEffect() {
        updateState { it.copy(riwayatEffect = null) }
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
                        val lokasiList = result.data.withDefaultOption()
                        updateState {
                            it.copy(
                                isLoadingLokasi = false,
                                lokasiList = lokasiList,
                                selectedLokasi = it.selectedLokasi
                                    .takeIf { selected -> lokasiList.contains(selected) }
                                    ?: lokasiList.firstOrNull()
                                    ?: "Semua Area",
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
                    selectedPayment = payment.name,
                    selectedVehicle = vehicle.name
                )
            }

            when (
                val result = doRiwayatUseCase(
                    reqModel = RiwayatRequestModel(
                    userId = prefs.userId,
                    username = prefs.username,
                    roleId = prefs.roleId,
                    startDate = startDate,
                    endDate = endDate,
                    payment = payment,
                    vehicle = vehicle,
                    lokasi = lokasi
                    ),
                )
            ) {
                is ApiCondition.AppSuccess -> {
                    updateState {
                        it.copy(
                            isLoading = false,
                            isRefresh = false,
                            riwayatResponseModel = result.data,
                            error = null,
                            selectedLokasi = lokasi,
                            selectedPayment = payment.name,
                            selectedVehicle = vehicle.name
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

    private fun LokasiModel.withDefaultOption(): List<String> {
        val source = nama_lokasi.filter { it.isNotBlank() }
        return if (source.any { it == "Semua Area" }) {
            source
        } else {
            listOf("Semua Area") + source
        }
    }
}
