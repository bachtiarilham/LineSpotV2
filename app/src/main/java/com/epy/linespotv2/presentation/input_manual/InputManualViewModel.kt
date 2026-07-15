package com.epy.linespotv2.presentation.input_manual

import androidx.lifecycle.viewModelScope
import com.epy.linespotv2.core.base.BaseViewModel
import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.preferences.AppPreferences
import com.epy.linespotv2.core.utils.toIndonesiaDateTime
import com.epy.linespotv2.domain.model.parking.PostParkingReqModel
import com.epy.linespotv2.domain.model.payment.InputManualModel
import com.epy.linespotv2.domain.model.payment.InputManualTarifSummary
import com.epy.linespotv2.domain.usecase.payment.GetPembayaranStatusUseCase
import com.epy.linespotv2.domain.usecase.payment.PostParkingUseCase
import com.epy.linespotv2.presentation.input_manual.ui_model.InputManualVehicleUiFilter
import com.epy.linespotv2.presentation.input_manual.ui_model.toUiModel
import com.epy.linespotv2.presentation.input_manual.ui_model.toVehicleUiFilter
import com.epy.linespotv2.presentation.input_manual.ui_model.toUiModel as toPembayaranUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class InputManualViewModel @Inject constructor(
    private val doPostParkingUseCase: PostParkingUseCase,
    private val doGetPembayaranStatusUseCase: GetPembayaranStatusUseCase,
    private val prefs: AppPreferences
) : BaseViewModel<InputManualIntent, InputManualState>(InputManualState()) {

    private var pollingJob: Job? = null

    override fun onIntent(intent: InputManualIntent) {
        when (intent) {
            InputManualIntent.LoadPage -> loadPage()
            InputManualIntent.ClickBack -> sendEffect(InputManualEffect.NavigateBack)
            InputManualIntent.ClickCancel -> sendEffect(InputManualEffect.NavigateBack)
            is InputManualIntent.ChangeNomorPolisi -> updateNomorPolisi(intent.nomorPolisi)
            is InputManualIntent.SelectJenisKendaraan -> updateJenisKendaraan(intent.jenisKendaraan)
            InputManualIntent.SubmitInputManual -> submitInputManual()
            InputManualIntent.StartPolling -> startPolling()
            InputManualIntent.StopPolling -> stopPolling()
            InputManualIntent.RefreshStatus -> refreshStatus()
            InputManualIntent.ClickPaymentDetail -> sendEffect(InputManualEffect.NavigateToPaymentDetail)
            is InputManualIntent.SelectPaymentOption -> {
                sendEffect(InputManualEffect.NavigateToPaymentMethod(intent.optionType))
            }
            InputManualIntent.ClickPrintReceipt -> sendEffect(InputManualEffect.PrintReceipt)
        }
    }

    fun consumeEffect() {
        updateState { it.copy(inputManualEffect = null) }
    }

    private fun loadPage() {
        val model = buildInputManualModel(
            currentPlate = state.value.inputManualModel.nomorPolisi.orEmpty(),
            selectedVehicle = state.value.inputManualModel.selectedVehicle.orEmpty().ifBlank { InputManualVehicleUiFilter.MOTOR.code }
        )
        updateInputManualState(model)
    }

    private fun updateNomorPolisi(nomorPolisi: String) {
        val current = state.value.inputManualModel
        updateInputManualState(
            buildInputManualModel(
                currentPlate = nomorPolisi,
                selectedVehicle = current.selectedVehicle.orEmpty()
            )
        )
    }

    private fun updateJenisKendaraan(jenisKendaraan: String) {
        updateInputManualState(
            buildInputManualModel(
                currentPlate = state.value.inputManualModel.nomorPolisi.orEmpty(),
                selectedVehicle = jenisKendaraan
            )
        )
    }

    private fun submitInputManual() {
        val model = state.value.inputManualModel
        val selectedVehicle = model.selectedVehicle.toVehicleUiFilter()

        viewModelScope.launch {
            updateState { it.copy(isLoading = true, error = null) }

            when (
                val result = doPostParkingUseCase(
                    reqModel = PostParkingReqModel(
                        plateNumber = model.nomorPolisi,
                        vehicleTypeCode = selectedVehicle.code,
                        selectedAreaId = null
                    )
                )
            ) {
                is ApiCondition.AppSuccess -> {
                    val pembayaran = result.data.mergeWithDefaults(state.value.postParkingRespModel)
                    updateState {
                        it.copy(
                            isLoading = false,
                            postParkingRespModel = pembayaran,
                            pembayaranUiModel = pembayaran.toPembayaranUiModel()
                        )
                    }
                    sendEffect(InputManualEffect.NavigateToPembayaran)
                }
                is ApiCondition.AppFailure -> {
                    updateState {
                        it.copy(
                            isLoading = false,
                            error = result.exception.message ?: "Terjadi kesalahan"
                        )
                    }
                    sendEffect(InputManualEffect.ShowToast(result.exception.message ?: "Terjadi kesalahan"))
                }
                is ApiCondition.AppLoading -> Unit
            }
        }
    }

    private fun startPolling() {
        if (pollingJob?.isActive == true) return
        val sessionId = state.value.postParkingRespModel?.sessionId ?: 0L
        if (sessionId <= 0L) return

        pollingJob = viewModelScope.launch {
            while (true) {
                refreshStatus()
                val status = state.value.postParkingRespModel?.paymentStatusCode.orEmpty()
                if (status.equals("PAID", true) || status.equals("SUCCESS", true) || status.equals("FAILED", true) || status.equals("EXPIRED", true)) {
                    break
                }
                delay(2_000)
            }
        }
    }

    private fun stopPolling() {
        pollingJob?.cancel()
        pollingJob = null
        updateState { it.copy(isCheckingPaymentStatus = false) }
    }

    private fun refreshStatus() {
        val sessionId = state.value.postParkingRespModel?.sessionId ?: 0L
        if (sessionId <= 0L) return

        viewModelScope.launch {
            doGetPembayaranStatusUseCase(sessionId).collectLatest { result ->
                when (result) {
                    is ApiCondition.AppLoading -> updateState { it.copy(isCheckingPaymentStatus = true) }
                    is ApiCondition.AppSuccess -> {
                        val merged = result.data.mergeWithStatus(state.value.postParkingRespModel)
                        updateState {
                            it.copy(
                                isCheckingPaymentStatus = false,
                                postParkingRespModel = merged,
                                pembayaranUiModel = merged.toPembayaranUiModel(),
                                error = null
                            )
                        }
                    }
                    is ApiCondition.AppFailure -> {
                        updateState {
                            it.copy(
                                isCheckingPaymentStatus = false,
                                error = result.exception.message ?: "Gagal memeriksa status pembayaran"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun updateInputManualState(model: InputManualModel) {
        updateState {
            it.copy(
                inputManualModel = model,
                inputManualUiModel = model.toUiModel(),
                error = null
            )
        }
    }

    private fun buildInputManualModel(
        currentPlate: String,
        selectedVehicle: String
    ): InputManualModel {
        val vehicle = selectedVehicle.toVehicleUiFilter()
        return InputManualModel(
            nomorPolisi = currentPlate,
            selectedVehicle = vehicle.code,
            waktuMasuk = Date().toIndonesiaDateTime(),
            areaParkir = buildAreaParkirText(),
            tarifSummary = InputManualTarifSummary(
                totalTarif = resolveTarif(vehicle)
            )
        )
    }

    private fun buildAreaParkirText(): String {
        val lokasi = prefs.lokasi.ifBlank { "-" }
        val zona = prefs.zona.ifBlank { "-" }
        return "$lokasi - $zona"
    }

    private fun resolveTarif(
        vehicle: InputManualVehicleUiFilter
    ): Long {
        return prefs.tarif.firstOrNull {
            it.kendaraan.equals(vehicle.label, ignoreCase = true) ||
                it.kendaraan.equals(vehicle.code, ignoreCase = true)
        }?.nominal?.toLong() ?: defaultTarif(vehicle)
    }

    private fun defaultTarif(vehicle: InputManualVehicleUiFilter): Long {
        return when (vehicle) {
            InputManualVehicleUiFilter.MOTOR -> 2_000L
            InputManualVehicleUiFilter.MOBIL -> 5_000L
        }
    }

    private fun sendEffect(effect: InputManualEffect) {
        updateState { it.copy(inputManualEffect = effect) }
    }
}
