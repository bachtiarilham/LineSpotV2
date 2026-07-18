package com.epy.linespotv2.presentation.topup

import androidx.lifecycle.viewModelScope
import com.epy.linespotv2.core.base.BaseViewModel
import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.preferences.AppPreferences
import com.epy.linespotv2.data.local.dao.HomeDao
import com.epy.linespotv2.domain.model.topup.TopupCreateRequestModel
import com.epy.linespotv2.domain.usecase.helper.NominalOptionUseCase
import com.epy.linespotv2.domain.usecase.home.CustomerHomeUseCase
import com.epy.linespotv2.domain.usecase.home.JukirHomeUseCase
import com.epy.linespotv2.domain.usecase.topup.GetTopUpStatusUseCase
import com.epy.linespotv2.domain.usecase.topup.TopUpUseCase
import com.epy.linespotv2.presentation.topup.ui_model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopUpViewModel @Inject constructor(
    private val nominalOptionUseCase: NominalOptionUseCase,
    private val topUpUseCase: TopUpUseCase,
    private val getTopUpStatusUseCase: GetTopUpStatusUseCase,
    private val customerHomeUseCase: CustomerHomeUseCase,
    private val jukirHomeUseCase: JukirHomeUseCase,
    private val homeDao: HomeDao,
    private val prefs: AppPreferences
) : BaseViewModel<TopUpIntent, TopUpState>(TopUpState()) {

    private var pollingJob: Job? = null

    override fun onIntent(intent: TopUpIntent) {
        when (intent) {
            TopUpIntent.LoadPage -> loadPage()
            TopUpIntent.ClickBack -> sendEffect(TopUpEffect.NavigateBack)
            is TopUpIntent.SelectNominal -> selectNominal(intent.amount)
            is TopUpIntent.ChangeCustomNominal -> changeCustomNominal(intent.value)
            is TopUpIntent.SelectPaymentMethod -> selectPaymentMethod(intent.paymentMethodCode)
            TopUpIntent.SubmitTopUp -> submitTopUp()
            TopUpIntent.StartPolling -> startPolling()
            TopUpIntent.StopPolling -> stopPolling()
            TopUpIntent.CheckTopUpStatus -> {
                viewModelScope.launch {
                    fetchTopUpStatus()
                }
            }
        }
    }

    fun consumeEffect() {
        updateState { it.copy(topUpEffect = null) }
    }

    private fun loadPage() {
        viewModelScope.launch {
            updateState {
                it.copy(
                    isLoadingPage = true,
                    error = null
                )
            }

            when (val result = nominalOptionUseCase()) {
                is ApiCondition.AppSuccess -> {
                    val defaultAmount = state.value.selectedNominalAmount
                        ?: result.data.nominal.orEmpty().firstOrNull()?.nominalAmount
                    val defaultMethodCode = state.value.selectedPaymentMethodCode
                        ?: result.data.metodePayment.orEmpty().firstOrNull()?.codePayment

                    updateState {
                        it.copy(
                            isLoadingPage = false,
                            topupModel = result.data,
                            selectedNominalAmount = defaultAmount,
                            selectedPaymentMethodCode = defaultMethodCode,
                            topUpUiModel = result.data.toUiModel(
                                selectedNominalAmount = defaultAmount,
                                selectedPaymentMethodCode = defaultMethodCode,
                                customNominalInput = it.customNominalInput
                            ),
                            error = null
                        )
                    }
                }
                is ApiCondition.AppFailure -> {
                    updateState {
                        it.copy(
                            isLoadingPage = false,
                            error = result.exception.message ?: "Gagal memuat data top up"
                        )
                    }
                }
                is ApiCondition.AppLoading -> {
                    updateState { it.copy(isLoadingPage = true) }
                }
            }
        }
    }

    private fun selectNominal(amount: Long) {
        updateState { current ->
            current.copy(
                selectedNominalAmount = amount,
                customNominalInput = "",
                topUpUiModel = current.topupModel.toUiModel(
                    selectedNominalAmount = amount,
                    selectedPaymentMethodCode = current.selectedPaymentMethodCode,
                    customNominalInput = ""
                ),
                error = null
            )
        }
    }

    private fun changeCustomNominal(value: String) {
        val filtered = value.filter(Char::isDigit)
        updateState { current ->
            current.copy(
                selectedNominalAmount = filtered.toLongOrNull(),
                customNominalInput = filtered,
                topUpUiModel = current.topupModel.toUiModel(
                    selectedNominalAmount = filtered.toLongOrNull(),
                    selectedPaymentMethodCode = current.selectedPaymentMethodCode,
                    customNominalInput = filtered
                ),
                error = null
            )
        }
    }

    private fun selectPaymentMethod(paymentMethodCode: String) {
        updateState { current ->
            current.copy(
                selectedPaymentMethodCode = paymentMethodCode,
                topUpUiModel = current.topupModel.toUiModel(
                    selectedNominalAmount = current.selectedNominalAmount,
                    selectedPaymentMethodCode = paymentMethodCode,
                    customNominalInput = current.customNominalInput
                ),
                error = null
            )
        }
    }

    private fun submitTopUp() {
        viewModelScope.launch {
            val currentState = state.value
            val amount = currentState.selectedNominalAmount ?: currentState.customNominalInput.toLongOrNull()
            val paymentMethodCode = currentState.selectedPaymentMethodCode
            val userId = resolveUserId()

            when {
                userId == null || userId <= 0L -> {
                    sendEffect(TopUpEffect.ShowMessage("User tidak ditemukan"))
                    updateState { it.copy(error = "User tidak ditemukan") }
                    return@launch
                }
                amount == null || amount <= 0L -> {
                    sendEffect(TopUpEffect.ShowMessage("Nominal top up wajib dipilih"))
                    updateState { it.copy(error = "Nominal top up wajib dipilih") }
                    return@launch
                }
                paymentMethodCode.isNullOrBlank() -> {
                    sendEffect(TopUpEffect.ShowMessage("Metode pembayaran wajib dipilih"))
                    updateState { it.copy(error = "Metode pembayaran wajib dipilih") }
                    return@launch
                }
            }

            updateState { it.copy(isSubmitting = true, error = null) }

            when (
                val result = topUpUseCase(
                    TopupCreateRequestModel(
                        userId = userId,
                        amount = amount,
                        paymentMethodCode = paymentMethodCode
                    )
                )
            ) {
                is ApiCondition.AppSuccess -> {
                    val topupCode = result.data.topupCode.orEmpty()
                    updateState {
                        it.copy(
                            isSubmitting = false,
                            topupCreateResponseModel = result.data,
                            activeTopupCode = topupCode,
                            error = null
                        )
                    }
                    sendEffect(TopUpEffect.TopUpCreated(topupCode))
                    startPolling()
                }
                is ApiCondition.AppFailure -> {
                    val message = result.exception.message ?: "Gagal membuat transaksi top up"
                    updateState { it.copy(isSubmitting = false, error = message) }
                    sendEffect(TopUpEffect.ShowMessage(message))
                }
                is ApiCondition.AppLoading -> {
                    updateState { it.copy(isSubmitting = true) }
                }
                null -> {
                    updateState { it.copy(isSubmitting = false, error = "Gagal membuat transaksi top up") }
                    sendEffect(TopUpEffect.ShowMessage("Gagal membuat transaksi top up"))
                }
            }
        }
    }

    private fun startPolling() {
        val topupCode = state.value.activeTopupCode.orEmpty()
        if (topupCode.isBlank()) return
        if (pollingJob?.isActive == true) return

        pollingJob = viewModelScope.launch {
            while (isActive) {
                val statusCode = fetchTopUpStatus()
                if (statusCode.isFinalTopupStatus()) {
                    break
                }
                delay(3_000)
            }
        }
    }

    private fun stopPolling() {
        pollingJob?.cancel()
        pollingJob = null
        updateState { it.copy(isCheckingStatus = false) }
    }

    private suspend fun fetchTopUpStatus(): String {
        val topupCode = state.value.activeTopupCode.orEmpty()
        if (topupCode.isBlank()) return ""

        updateState { it.copy(isCheckingStatus = true) }

        return when (val result = getTopUpStatusUseCase(topupCode)) {
            is ApiCondition.AppSuccess -> {
                val statusCode = result.data.paymentStatusCode.orEmpty()
                updateState {
                    it.copy(
                        isCheckingStatus = false,
                        topupStatusResponseModel = result.data,
                        error = null
                    )
                }

                when {
                    statusCode.equals("PAID", ignoreCase = true) ||
                        statusCode.equals("SUCCESS", ignoreCase = true) -> {
                        stopPolling()
                        refreshHomeAfterTopUpSuccess()
                        sendEffect(TopUpEffect.TopUpPaid(topupCode))
                    }
                    statusCode.equals("FAILED", ignoreCase = true) ||
                        statusCode.equals("EXPIRED", ignoreCase = true) -> {
                        stopPolling()
                        sendEffect(
                            TopUpEffect.TopUpFailed(
                                result.data.failedReason ?: "Transaksi top up tidak berhasil"
                            )
                        )
                    }
                }
                statusCode
            }
            is ApiCondition.AppFailure -> {
                updateState {
                    it.copy(
                        isCheckingStatus = false,
                        error = result.exception.message ?: "Gagal memeriksa status top up"
                    )
                }
                ""
            }
            is ApiCondition.AppLoading -> {
                updateState { it.copy(isCheckingStatus = true) }
                ""
            }
        }
    }

    private suspend fun refreshHomeAfterTopUpSuccess() {
        when (prefs.roleId) {
            1L -> refreshCustomerHome()
            2L -> refreshJukirHome()
            else -> {
                if (homeDao.getCustomerCache() != null) {
                    refreshCustomerHome()
                } else if (homeDao.getJukirCache() != null) {
                    refreshJukirHome()
                }
            }
        }
    }

    private suspend fun refreshCustomerHome() {
        customerHomeUseCase().collect { result ->
            when (result) {
                is ApiCondition.AppSuccess,
                is ApiCondition.AppFailure,
                is ApiCondition.AppLoading -> Unit
            }
        }
    }

    private suspend fun refreshJukirHome() {
        jukirHomeUseCase().collect { result ->
            when (result) {
                is ApiCondition.AppSuccess,
                is ApiCondition.AppFailure,
                is ApiCondition.AppLoading -> Unit
            }
        }
    }

    private suspend fun resolveUserId(): Long? {
        return homeDao.getCustomerCache()?.userId
            ?: homeDao.getJukirCache()?.userId
    }

    private fun sendEffect(effect: TopUpEffect) {
        updateState { it.copy(topUpEffect = effect) }
    }

    override fun onCleared() {
        stopPolling()
        super.onCleared()
    }
}

private fun String.isFinalTopupStatus(): Boolean {
    return equals("PAID", ignoreCase = true) ||
        equals("SUCCESS", ignoreCase = true) ||
        equals("FAILED", ignoreCase = true) ||
        equals("EXPIRED", ignoreCase = true)
}
