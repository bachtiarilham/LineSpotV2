package com.epy.linespotv2.presentation.laporan

import androidx.lifecycle.viewModelScope
import com.epy.linespotv2.core.base.BaseViewModel
import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.laporan.LaporanRequestModel
import com.epy.linespotv2.domain.usecase.laporan.LaporanUseCase
import com.epy.linespotv2.presentation.laporan.ui_model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaporanViewModel @Inject constructor(
    private val doLaporanUseCase: LaporanUseCase
) : BaseViewModel<LaporanIntent, LaporanState>(LaporanState()) {

    override fun onIntent(intent: LaporanIntent) {
        when (intent) {
            LaporanIntent.loadFilterPage -> loadFilterPage()
            LaporanIntent.loadPage -> loadLaporanPage()
            is LaporanIntent.submitFilter -> submitFilter(
                startDate = intent.startDate,
                endDate = intent.endDate,
            )
        }
    }

    fun consumeEffect() {
        updateState { it.copy(laporanEffect = null) }
    }

    private fun loadLaporanPage() {
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
                isRefresh = false,
                error = null
            )
        }
    }

    private fun submitFilter(
        startDate: String,
        endDate: String,
    ) {
        viewModelScope.launch {
            updateState {
                it.copy(
                    isLoading = true,
                    error = null,
                )
            }

            val reqModel = LaporanRequestModel(
                startDate = startDate,
                endDate = endDate,
            )

            when (
                val result = doLaporanUseCase(
                    reqModel = reqModel
                )
            ) {
                is ApiCondition.AppSuccess -> {
                    updateState {
                        it.copy(
                            isLoading = false,
                            laporanResponseModel = result.data,
                            screenUiModel = result.data.toUiModel(),
                            error = null
                        )
                    }
                    sendEffect(LaporanEffect.NavigateToLaporan)
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

    private fun sendEffect(effect: LaporanEffect) {
        updateState { it.copy(laporanEffect = effect) }
    }
}
