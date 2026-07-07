package com.epy.linespotv2.presentation.post_qr

import androidx.lifecycle.viewModelScope
import com.epy.linespotv2.core.base.BaseViewModel
import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.payment.PostPaymentParkingReqModel
import com.epy.linespotv2.domain.usecase.payment.PostPaymentParkingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class PostQrViewModel @Inject constructor(
    private val postPaymentParkingUseCase: PostPaymentParkingUseCase,
) : BaseViewModel<PostQrIntent, PostQrState>(PostQrState()) {

    override fun onIntent(intent: PostQrIntent) {
        when (intent) {
            PostQrIntent.LoadPage -> loadPage()
            PostQrIntent.ClickBack -> sendEffect(PostQrEffect.NavigateBack)
            is PostQrIntent.QrDetected -> submitQr(intent.rawQr)
            is PostQrIntent.CameraPermissionChanged -> updateCameraPermission(intent.isGranted)
            is PostQrIntent.CameraError -> handleCameraError(intent.message)
            PostQrIntent.RescanQr -> resetToScanning()
            PostQrIntent.DismissError -> resetToScanning()
            PostQrIntent.ClickFlash -> toggleFlash()
            PostQrIntent.ClickHelp -> showHelpMessage()
        }
    }

    fun consumeEffect() {
        updateState { it.copy(postQrEffect = null) }
    }

    private fun loadPage() {
        updateState {
            it.copy(
                isLoading = false,
                error = null,
                postQrEffect = null,
                stage = PostQrStage.Scanning,
                canProcessScan = true,
                rawQr = ""
            )
        }
    }

    private fun submitQr(rawQr: String) {
        val current = state.value
        if (!current.canProcessScan || current.stage !is PostQrStage.Scanning) return

        viewModelScope.launch {
            updateState {
                it.copy(
                    isLoading = true,
                    error = null,
                    rawQr = rawQr,
                    canProcessScan = false,
                    stage = PostQrStage.LoadingDetail
                )
            }

            val reqModel = runCatching { rawQr.toPostPaymentParkingReqModel() }.getOrElse { error ->
                val message = error.message ?: "Format QRIS tidak valid"
                updateState {
                    it.copy(
                        isLoading = false,
                        error = message,
                        canProcessScan = true,
                        stage = PostQrStage.Error(message)
                    )
                }
                return@launch
            }

            when (val result = postPaymentParkingUseCase(reqModel = reqModel)) {
                is ApiCondition.AppSuccess -> {
                    updateState {
                        it.copy(
                            isLoading = false,
                            error = null,
                            stage = PostQrStage.Scanning
                        )
                    }
                    sendEffect(PostQrEffect.NavigateToHasilBayarParkir(rawQr))
                }

                is ApiCondition.AppFailure -> {
                    val message = result.exception.message ?: "QR tidak dapat diproses"
                    updateState {
                        it.copy(
                            isLoading = false,
                            error = message,
                            canProcessScan = true,
                            stage = PostQrStage.Error(message)
                        )
                    }
                }

                is ApiCondition.AppLoading -> {
                    updateState {
                        it.copy(
                            isLoading = true,
                            stage = PostQrStage.LoadingDetail
                        )
                    }
                }
            }
        }
    }

    private fun updateCameraPermission(isGranted: Boolean) {
        updateState {
            it.copy(
                isCameraPermissionGranted = isGranted,
                error = if (isGranted) null else "Izin kamera diperlukan untuk memindai QRIS."
            )
        }
    }

    private fun handleCameraError(message: String) {
        updateState {
            it.copy(
                isLoading = false,
                error = message,
                canProcessScan = true,
                stage = PostQrStage.Error(message)
            )
        }
        sendEffect(PostQrEffect.ShowToast(message))
    }

    private fun resetToScanning() {
        updateState {
            it.copy(
                isLoading = false,
                error = null,
                postQrEffect = null,
                stage = PostQrStage.Scanning,
                canProcessScan = true,
                rawQr = ""
            )
        }
    }

    private fun toggleFlash() {
        updateState { it.copy(isTorchEnabled = !it.isTorchEnabled) }
    }

    private fun showHelpMessage() {
        sendEffect(
            PostQrEffect.ShowToast(
                "Arahkan kamera ke QRIS hingga seluruh kode terlihat jelas."
            )
        )
    }

    private fun sendEffect(effect: PostQrEffect) {
        updateState { it.copy(postQrEffect = effect) }
    }

    private fun String.toPostPaymentParkingReqModel(): PostPaymentParkingReqModel {
        val json = JSONObject(this)

        return PostPaymentParkingReqModel(
            sessionID = json.optLong("session_id", 0L),
            platNomor = json.optString("plat_nomor"),
            lokasi = json.optString("lokasi"),
            waktuMasuk = json.optString("waktu_masuk"),
            durasi = json.optString("durasi"),
            nominal = json.optLong("nominal", 0L),
            isPaid = json.optBoolean("sudah_bayar", false),
            paymentStatus = json.optLong("payment_status", 0L),
            isExpired = json.optBoolean("is_expired", false),
            statusMessage = json.optString("status_message")
        ).also { req ->
            require(req.sessionID > 0L) { "Session pembayaran tidak valid" }
            require(req.platNomor.isNotBlank()) { "Plat nomor tidak ditemukan pada QRIS" }
            require(req.waktuMasuk.isNotBlank()) { "Waktu masuk tidak ditemukan pada QRIS" }
            require(req.nominal > 0L) { "Nominal pembayaran tidak valid" }
        }
    }
}
