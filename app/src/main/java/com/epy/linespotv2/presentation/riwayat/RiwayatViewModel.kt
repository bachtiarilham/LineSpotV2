package com.epy.linespotv2.presentation.riwayat

import androidx.lifecycle.viewModelScope
import com.epy.linespotv2.core.base.BaseViewModel
import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.preferences.AppPreferences
import com.epy.linespotv2.core.utils.toIndonesiaDate
import com.epy.linespotv2.domain.model.helper.LokasiModel
import com.epy.linespotv2.domain.model.riwayat.RiwayatPaymentFilter
import com.epy.linespotv2.domain.model.riwayat.RiwayatTransactionFilter
import com.epy.linespotv2.domain.model.riwayat.RiwayatVehicleFilter
import com.epy.linespotv2.domain.usecase.GetLokasiUseCase
import com.epy.linespotv2.domain.usecase.RiwayatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class RiwayatViewModel @Inject constructor(
    private val doRiwayatUseCase: RiwayatUseCase,
    private val doGetLokasiUseCase: GetLokasiUseCase,
    private val prefs: AppPreferences
) : BaseViewModel<RiwayatIntent, RiwayatState>(RiwayatState()) {

    override fun onIntent(intent: RiwayatIntent) {
        when (intent) {
            RiwayatIntent.loadPage -> loadRiwayatPage(isRefresh = false)
            RiwayatIntent.loadFilterPage -> loadFilterPage()
            RiwayatIntent.clickRiwayatDetail -> sendEffect(RiwayatEffect.NavigateToDetail)
            is RiwayatIntent.selectLokasi -> updateSelectedLokasi(intent.lokasi)
            is RiwayatIntent.submitFilter -> submitFilter(
                startDate = intent.startDate,
                endDate = intent.endDate,
                transaction = intent.transaction,
                payment = intent.payment,
                vehicle = intent.vehicle,
                lokasi = intent.lokasi
            )
        }
    }

    fun consumeEffect() {
        updateState { it.copy(riwayatEffect = null) }
    }

    private fun loadRiwayatPage(isRefresh: Boolean = false) {
        viewModelScope.launch {
            updateState {
                it.copy(
                    isLoading = !isRefresh,
                    isRefresh = isRefresh,
                    error = null
                )
            }

            when (
                val result = doRiwayatUseCase(
                    userId = prefs.userId,
                    username = prefs.username,
                    roleId = prefs.roleId,
                    startDate = Date().toIndonesiaDate(),
                    endDate = Date().toIndonesiaDate(),
                    transaction = RiwayatTransactionFilter.ALL,
                    payment = RiwayatPaymentFilter.ALL,
                    vehicle = RiwayatVehicleFilter.ALL,
                    lokasi = state.value.selectedLokasi
                )
            ) {
                is ApiCondition.AppSuccess -> {
                    updateState {
                        it.copy(
                            isLoading = false,
                            isRefresh = false,
                            riwayatModel = result.data,
                            error = null
                        )
                    }
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

                is ApiCondition.AppLoading -> Unit
            }
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
                                selectedLokasi = lokasiList.firstOrNull() ?: "Semua Area",
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

    private fun updateSelectedLokasi(lokasi: String) {
        updateState { currentState ->
            currentState.copy(selectedLokasi = lokasi)
        }
    }

    private fun submitFilter(
        startDate: String,
        endDate: String,
        transaction: RiwayatTransactionFilter,
        payment: RiwayatPaymentFilter,
        vehicle: RiwayatVehicleFilter,
        lokasi: String
    ) {
        viewModelScope.launch {
            updateState {
                it.copy(
                    isLoading = true,
                    error = null,
                    selectedLokasi = lokasi
                )
            }

            when (
                val result = doRiwayatUseCase(
                    userId = prefs.userId,
                    username = prefs.username,
                    roleId = prefs.roleId,
                    startDate = startDate,
                    endDate = endDate,
                    transaction = transaction,
                    payment = payment,
                    vehicle = vehicle,
                    lokasi = lokasi
                )
            ) {
                is ApiCondition.AppSuccess -> {
                    updateState {
                        it.copy(
                            isLoading = false,
                            riwayatModel = result.data,
                            error = null
                        )
                    }
                    sendEffect(RiwayatEffect.NavigateToRiwayat)
                }

                is ApiCondition.AppFailure -> {
                    updateState {
                        it.copy(
                            isLoading = false,
                            error = result.exception.message ?: "Terjadi kesalahan"
                        )
                    }
                }

                is ApiCondition.AppLoading -> Unit
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
