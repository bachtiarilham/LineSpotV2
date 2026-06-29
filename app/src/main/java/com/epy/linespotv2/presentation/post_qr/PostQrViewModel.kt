package com.epy.linespotv2.presentation.post_qr

import androidx.lifecycle.viewModelScope
import com.epy.linespotv2.core.base.BaseViewModel
import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.usecase.PostQrUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostQrViewModel @Inject constructor(
    private val postQrUseCase: PostQrUseCase,
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
                canProcessScan = true
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

            when (val result = postQrUseCase(rawQr)) {
                is ApiCondition.AppSuccess -> {

                    updateState {
                        it.copy(
                            isLoading = false,
                            error = null
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
                canProcessScan = true
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
}
