package com.epy.linespotv2.presentation.riwayat

import androidx.lifecycle.viewModelScope
import com.epy.linespotv2.core.base.BaseViewModel
import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.riwayat.RiwayatRequestModel
import com.epy.linespotv2.domain.usecase.riwayat.DetilParkirUseCase
import com.epy.linespotv2.domain.usecase.riwayat.DetilTransaksiUseCase
import com.epy.linespotv2.domain.usecase.riwayat.RiwayatUseCase
import com.epy.linespotv2.presentation.riwayat.ui_model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RiwayatViewModel @Inject constructor(
    private val doRiwayatUseCase: RiwayatUseCase,
    private val doDetilParkirUseCase: DetilParkirUseCase,
    private val doDetilTransaksiUseCase: DetilTransaksiUseCase
) : BaseViewModel<RiwayatIntent, RiwayatState>(RiwayatState()) {

    override fun onIntent(intent: RiwayatIntent) {
        when (intent) {
            RiwayatIntent.loadPage -> loadRiwayatPage()
            RiwayatIntent.loadFilterPage -> loadFilterPage()
            is RiwayatIntent.changeTab -> changeActiveTab(intent.tab)
            is RiwayatIntent.clickRiwayatDetail -> loadRiwayatDetail(intent.code, intent.isParking)
            is RiwayatIntent.submitFilter -> submitFilter(
                startDate = intent.startDate,
                endDate = intent.endDate
            )
        }
    }

    fun consumeEffect() {
        updateState { it.copy(riwayatEffect = null) }
    }

    private fun changeActiveTab(tab: RiwayatTab) {
        updateState { it.copy(selectedTab = tab) }
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
        updateState {
            it.copy(
                isLoading = false,
                error = null
            )
        }
    }

    private fun loadRiwayatDetail(code: String, isParking: Boolean) {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true, error = null) }

            if (isParking) {
                // Ambil Detail Parkir
                when (val result = doDetilParkirUseCase.invoke(code)) {
                    is ApiCondition.AppSuccess -> {
                        updateState {
                            it.copy(
                                isLoading = false,
                                selectedDetail = result.data.toUiModel()
                            )
                        }
                        sendEffect(RiwayatEffect.NavigateToDetail)
                    }
                    is ApiCondition.AppFailure -> {
                        updateState {
                            it.copy(
                                isLoading = false,
                                error = result.exception.message ?: "Gagal memuat detail parkir"
                            )
                        }
                    }
                    is ApiCondition.AppLoading -> {
                        updateState { it.copy(isLoading = true) }
                    }
                }
            } else {
                // Ambil Detail Transaksi Keuangan
                when (val result = doDetilTransaksiUseCase.invoke(code)) {
                    is ApiCondition.AppSuccess -> {
                        updateState {
                            it.copy(
                                isLoading = false,
                                selectedDetail = result.data.toUiModel()
                            )
                        }
                        sendEffect(RiwayatEffect.NavigateToDetail)
                    }
                    is ApiCondition.AppFailure -> {
                        updateState {
                            it.copy(
                                isLoading = false,
                                error = result.exception.message ?: "Gagal memuat detail transaksi"
                            )
                        }
                    }
                    is ApiCondition.AppLoading -> {
                        updateState { it.copy(isLoading = true) }
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
        endDate: String
    ) {
        viewModelScope.launch {
            updateState {
                it.copy(
                    isLoading = true,
                    error = null,
                    filterStartDate = startDate,
                    filterEndDate = endDate
                )
            }

            when (
                val result = doRiwayatUseCase(
                    reqModel = RiwayatRequestModel(
                        startDate = startDate,
                        endDate = endDate,
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
                            filterStartDate = startDate,
                            filterEndDate = endDate
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
}
