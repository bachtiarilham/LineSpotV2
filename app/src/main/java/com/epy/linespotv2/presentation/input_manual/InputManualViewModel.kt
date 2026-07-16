package com.epy.linespotv2.presentation.input_manual

import androidx.lifecycle.viewModelScope
import com.epy.linespotv2.core.base.BaseViewModel
import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.utils.toApiDate
import com.epy.linespotv2.data.local.dao.HomeDao
import com.epy.linespotv2.domain.model.helper.LokasiItemModel
import com.epy.linespotv2.domain.model.helper.TarifModel
import com.epy.linespotv2.domain.model.parking.PostParkingReqModel
import com.epy.linespotv2.domain.model.payment.InputManualModel
import com.epy.linespotv2.domain.model.payment.InputManualTarifSummary
import com.epy.linespotv2.domain.usecase.helper.GetLokasiUseCase
import com.epy.linespotv2.domain.usecase.helper.GetTarifUseCase
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
    private val doGetLokasiUseCase: GetLokasiUseCase,
    private val doGetTarifUseCase: GetTarifUseCase,
    private val homeDao: HomeDao
) : BaseViewModel<InputManualIntent, InputManualState>(InputManualState()) {

    private var pollingJob: Job? = null

    override fun onIntent(intent: InputManualIntent) {
        when (intent) {
            InputManualIntent.LoadPage -> loadPage()
            InputManualIntent.ClickBack -> sendEffect(InputManualEffect.NavigateBack)
            InputManualIntent.ClickCancel -> sendEffect(InputManualEffect.NavigateBack)
            is InputManualIntent.ChangeNomorPolisi -> updateNomorPolisi(intent.nomorPolisi)
            is InputManualIntent.SelectJenisKendaraan -> updateJenisKendaraan(intent.jenisKendaraan)
            is InputManualIntent.SelectArea -> updateSelectedArea(intent.areaName)
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
        viewModelScope.launch {
            val homeCache = homeDao.getJukirCache()
            loadLokasi(homeCache)
            doGetTarifUseCase().collectLatest { result ->
                when (result) {
                    is ApiCondition.AppLoading -> updateState { it.copy(isLoading = true, error = null) }
                    is ApiCondition.AppSuccess -> {
                        val model = buildInputManualModel(
                            currentPlate = state.value.inputManualModel.nomorPolisi.orEmpty(),
                            selectedVehicle = state.value.inputManualModel.selectedVehicle.orEmpty()
                                .ifBlank { InputManualVehicleUiFilter.MOTOR.code },
                            selectedAreaId = state.value.inputManualModel.selectedAreaId,
                            lokasiList = state.value.lokasiList,
                            homeCache = homeCache,
                            tarifModel = result.data
                        )
                        updateState {
                            it.copy(
                                isLoading = false,
                                tarifModel = result.data,
                                inputManualModel = model,
                                inputManualUiModel = model.toUiModel(),
                                error = null
                            )
                        }
                    }
                    is ApiCondition.AppFailure -> {
                        val fallbackModel = buildInputManualModel(
                            currentPlate = state.value.inputManualModel.nomorPolisi.orEmpty(),
                            selectedVehicle = state.value.inputManualModel.selectedVehicle.orEmpty()
                                .ifBlank { InputManualVehicleUiFilter.MOTOR.code },
                            selectedAreaId = state.value.inputManualModel.selectedAreaId,
                            lokasiList = state.value.lokasiList,
                            homeCache = homeCache,
                            tarifModel = state.value.tarifModel
                        )
                        updateState {
                            it.copy(
                                isLoading = false,
                                inputManualModel = fallbackModel,
                                inputManualUiModel = fallbackModel.toUiModel(),
                                error = result.exception.message
                            )
                        }
                    }
                }
            }
        }
    }

    private fun updateNomorPolisi(nomorPolisi: String) {
        viewModelScope.launch {
            val homeCache = homeDao.getJukirCache()
            val current = state.value.inputManualModel
            updateInputManualState(
                buildInputManualModel(
                    currentPlate = nomorPolisi,
                    selectedVehicle = current.selectedVehicle.orEmpty(),
                    selectedAreaId = current.selectedAreaId,
                    lokasiList = state.value.lokasiList,
                    homeCache = homeCache,
                    tarifModel = state.value.tarifModel
                )
            )
        }
    }

    private fun updateJenisKendaraan(jenisKendaraan: String) {
        viewModelScope.launch {
            val homeCache = homeDao.getJukirCache()
            updateInputManualState(
                buildInputManualModel(
                    currentPlate = state.value.inputManualModel.nomorPolisi.orEmpty(),
                    selectedVehicle = jenisKendaraan,
                    selectedAreaId = state.value.inputManualModel.selectedAreaId,
                    lokasiList = state.value.lokasiList,
                    homeCache = homeCache,
                    tarifModel = state.value.tarifModel
                )
            )
        }
    }

    private fun updateSelectedArea(areaName: String) {
        viewModelScope.launch {
            val homeCache = homeDao.getJukirCache()
            val selectedAreaId = state.value.lokasiList
                .firstOrNull { it.namaArea.orEmpty().trim() == areaName }
                ?.areaId

            updateInputManualState(
                buildInputManualModel(
                    currentPlate = state.value.inputManualModel.nomorPolisi.orEmpty(),
                    selectedVehicle = state.value.inputManualModel.selectedVehicle.orEmpty(),
                    selectedAreaId = selectedAreaId,
                    lokasiList = state.value.lokasiList,
                    homeCache = homeCache,
                    tarifModel = state.value.tarifModel
                )
            )
        }
    }

    private fun submitInputManual() {
        val model = state.value.inputManualModel
        val selectedVehicle = model.selectedVehicle.toVehicleUiFilter()
        val selectedAreaId = model.selectedAreaId

        viewModelScope.launch {
            if (selectedAreaId == null || selectedAreaId <= 0L) {
                updateState { it.copy(error = "Area parkir wajib dipilih") }
                return@launch
            }

            updateState { it.copy(isLoading = true, error = null) }

            when (
                val result = doPostParkingUseCase(
                    reqModel = PostParkingReqModel(
                        plateNumber = model.nomorPolisi,
                        vehicleTypeCode = selectedVehicle.code,
                        selectedAreaId = selectedAreaId,
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
        selectedVehicle: String,
        selectedAreaId: Long?,
        lokasiList: List<LokasiItemModel>,
        homeCache: com.epy.linespotv2.data.local.entity.JukirEntity?,
        tarifModel: TarifModel
    ): InputManualModel {
        val vehicle = selectedVehicle.toVehicleUiFilter()
        val areaOptions = lokasiList
            .mapNotNull { it.namaArea.orEmpty().trim().takeIf(String::isNotBlank) }
            .distinct()
        val resolvedAreaId = selectedAreaId ?: lokasiList.firstOrNull()?.areaId
        return InputManualModel(
            nomorPolisi = currentPlate,
            selectedVehicle = vehicle.code,
            waktuMasuk = Date().toApiDate(),
            selectedAreaId = resolvedAreaId,
            areaParkir = buildAreaParkirText(homeCache, lokasiList, resolvedAreaId),
            areaOptions = areaOptions,
            tarifSummary = InputManualTarifSummary(
                totalTarif = resolveTarif(vehicle, tarifModel)
            )
        )
    }

    private fun buildAreaParkirText(
        homeCache: com.epy.linespotv2.data.local.entity.JukirEntity?,
        lokasiList: List<LokasiItemModel>,
        selectedAreaId: Long?
    ): String {
        val selectedArea = lokasiList.firstOrNull { it.areaId == selectedAreaId }
        if (selectedArea != null) {
            return selectedArea.namaArea.orEmpty().ifBlank { "-" }
        }

        val zona = homeCache?.zoneName.orEmpty()
        val lokasi = homeCache?.lokasiName.orEmpty()
        val area = homeCache?.areaName.orEmpty()
        return "$lokasi - $zona - $area"
    }

    private fun resolveTarif(
        vehicle: InputManualVehicleUiFilter,
        tarifModel: TarifModel
    ): Long {
        val apiTarif = tarifModel.tarifResponseItemDto.firstOrNull {
            it.kendaraanKode.equals(vehicle.code, ignoreCase = true) ||
                    it.kendaraanNama.equals(vehicle.label, ignoreCase = true)
        }?.nominal ?: 0L
        return apiTarif
    }

    private fun sendEffect(effect: InputManualEffect) {
        updateState { it.copy(inputManualEffect = effect) }
    }

    private suspend fun loadLokasi(
        homeCache: com.epy.linespotv2.data.local.entity.JukirEntity?
    ) {
        doGetLokasiUseCase().collectLatest { result ->
            when (result) {
                is ApiCondition.AppLoading -> {
                    updateState { it.copy(isLoadingLokasi = true) }
                }
                is ApiCondition.AppSuccess -> {
                    val lokasiList = result.data.lokasi.orEmpty()
                    val model = buildInputManualModel(
                        currentPlate = state.value.inputManualModel.nomorPolisi.orEmpty(),
                        selectedVehicle = state.value.inputManualModel.selectedVehicle.orEmpty()
                            .ifBlank { InputManualVehicleUiFilter.MOTOR.code },
                        selectedAreaId = state.value.inputManualModel.selectedAreaId
                            ?: lokasiList.firstOrNull()?.areaId,
                        lokasiList = lokasiList,
                        homeCache = homeCache,
                        tarifModel = state.value.tarifModel
                    )
                    updateState {
                        it.copy(
                            isLoadingLokasi = false,
                            lokasiList = lokasiList,
                            inputManualModel = model,
                            inputManualUiModel = model.toUiModel(),
                            error = null
                        )
                    }
                }
                is ApiCondition.AppFailure -> {
                    updateState {
                        it.copy(
                            isLoadingLokasi = false,
                            error = result.exception.message ?: "Gagal memuat area parkir"
                        )
                    }
                }
            }
        }
    }
}
