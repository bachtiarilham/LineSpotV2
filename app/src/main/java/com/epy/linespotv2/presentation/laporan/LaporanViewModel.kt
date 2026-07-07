package com.epy.linespotv2.presentation.laporan

import androidx.lifecycle.viewModelScope
import com.epy.linespotv2.core.base.BaseViewModel
import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.preferences.AppPreferences
import com.epy.linespotv2.core.utils.toIndonesiaDate
import com.epy.linespotv2.domain.model.helper.LokasiModel
import com.epy.linespotv2.domain.model.laporan.LaporanRequestModel
import com.epy.linespotv2.domain.usecase.helper.GetLokasiUseCase
import com.epy.linespotv2.domain.usecase.laporan.LaporanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class LaporanViewModel @Inject constructor(
    private val doLaporanUseCase: LaporanUseCase,
    private val doGetLokasiUseCase: GetLokasiUseCase,
    private val prefs: AppPreferences
) : BaseViewModel<LaporanIntent, LaporanState>(LaporanState()) {

    override fun onIntent(intent: LaporanIntent) {
        when (intent) {
            LaporanIntent.loadFilterPage -> loadFilterPage()
            LaporanIntent.loadPage -> loadLaporanPage()
            is LaporanIntent.submitFilter -> submitFilter(
                startDate = intent.startDate,
                endDate = intent.endDate,
                lokasi = intent.lokasi
            )
        }
    }

    fun consumeEffect() {
        updateState { it.copy(laporanEffect = null) }
    }

    fun updateSelectedLokasi(lokasi: String) {
        updateState { currentState ->
            currentState.copy(selectedLokasi = lokasi)
        }
    }

    private fun loadLaporanPage() {
        updateState {
            it.copy(
                isLoading = false,
                error = null
            )
        }
    }

//    private fun loadLaporanPage(isRefresh: Boolean = false) {
//        viewModelScope.launch {
//            updateState {
//                it.copy(
//                    isLoading = !isRefresh,
//                    isRefresh = isRefresh,
//                    error = null
//                )
//            }
//
//            val reqModel = LaporanRequestModel(
//                userId = prefs.userId,
//                username = prefs.username,
//                roleId = prefs.roleId,
//                startDate = Date().toIndonesiaDate(),
//                endDate = Date().toIndonesiaDate(),
//                lokasi = state.value.selectedLokasi
//            )
//
//            when (val result = doLaporanUseCase(reqModel = reqModel)
//            ) {
//                is ApiCondition.AppSuccess -> {
//                    updateState {
//                        it.copy(
//                            isLoading = false,
//                            isRefresh = false,
//                            laporanResponseModel = result.data,
//                            error = null
//                        )
//                    }
//                }
//
//                is ApiCondition.AppFailure -> {
//                    updateState {
//                        it.copy(
//                            isLoading = false,
//                            isRefresh = false,
//                            error = result.exception.message ?: "Terjadi kesalahan"
//                        )
//                    }
//                }
//
//                is ApiCondition.AppLoading -> Unit
//            }
//        }
//    }

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

    private fun submitFilter(
        startDate: String,
        endDate: String,
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

            val reqModel = LaporanRequestModel(
                userId = prefs.userId,
                username = prefs.username,
                roleId = prefs.roleId,
                startDate = startDate,
                endDate = endDate,
                lokasi = lokasi
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

    private fun LokasiModel.withDefaultOption(): List<String> {
        val source = nama_lokasi.filter { it.isNotBlank() }
        return if (source.any { it == "Semua Area" }) {
            source
        } else {
            listOf("Semua Area") + source
        }
    }
}
