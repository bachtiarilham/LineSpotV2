package com.epy.linespotv2.presentation.input_manual

import androidx.lifecycle.viewModelScope
import com.epy.linespotv2.core.base.BaseViewModel
import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.preferences.AppPreferences
import com.epy.linespotv2.core.utils.toIndonesiaDateTime
import com.epy.linespotv2.domain.model.InputManualVehicleType
import com.epy.linespotv2.domain.model.PembayaranModel
import com.epy.linespotv2.domain.usecase.GetPembayaranStatusUseCase
import com.epy.linespotv2.domain.usecase.InputManualUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class InputManualViewModel @Inject constructor(
    private val doInputManualUseCase: InputManualUseCase,
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
            is InputManualIntent.ChangeWaktuMasuk -> updateWaktuMasuk(intent.waktuMasuk)
            is InputManualIntent.SelectZonaParkir -> updateZonaParkir(intent.zonaParkir)
            is InputManualIntent.SelectLokasiParkir -> updateLokasiParkir(intent.lokasiParkir)
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
        updateState { current ->
            current.copy(
                inputManualModel = current.inputManualModel.copy(
                    waktuMasuk = current.inputManualModel.waktuMasuk.ifBlank { Date().toIndonesiaDateTime() },
                    areaParkir = buildAreaParkirText(
                        lokasi = prefs.lokasi,
                        zona = prefs.zona
                    )
                ),
                selectedZonaParkir = current.selectedZonaParkir.ifBlank { prefs.zona },
                selectedLokasiParkir = current.selectedLokasiParkir.ifBlank { prefs.lokasi }
            ).withSelectedVehicleTarif(
                tarifItems = prefs.tarif,
                selectedVehicle = current.inputManualModel.selectedVehicle
            )
        }
    }

    private fun updateNomorPolisi(nomorPolisi: String) {
        updateState {
            it.copy(
                inputManualModel = it.inputManualModel.copy(nomorPolisi = nomorPolisi)
            )
        }
    }

    private fun updateJenisKendaraan(jenisKendaraan: String) {
        val selectedVehicle = when (jenisKendaraan.trim().uppercase()) {
            "MOBIL" -> InputManualVehicleType.MOBIL
            else -> InputManualVehicleType.MOTOR
        }

        updateState {
            it.withSelectedVehicleTarif(
                selectedVehicle = selectedVehicle
            )
        }
    }

    private fun updateWaktuMasuk(waktuMasuk: String) {
        updateState {
            it.copy(
                inputManualModel = it.inputManualModel.copy(waktuMasuk = waktuMasuk)
            )
        }
    }

    private fun updateZonaParkir(zonaParkir: String) {
        updateState {
            it.copy(
                selectedZonaParkir = zonaParkir,
                inputManualModel = it.inputManualModel.copy(
                    areaParkir = buildAreaParkirText(
                        lokasi = it.selectedLokasiParkir,
                        zona = zonaParkir
                    )
                )
            )
        }
    }

    private fun updateLokasiParkir(lokasiParkir: String) {
        updateState {
            it.copy(
                selectedLokasiParkir = lokasiParkir,
                inputManualModel = it.inputManualModel.copy(
                    areaParkir = buildAreaParkirText(
                        lokasi = lokasiParkir,
                        zona = it.selectedZonaParkir
                    )
                )
            )
        }
    }

    private fun submitInputManual() {
        val current = state.value
        val model = current.inputManualModel

        viewModelScope.launch {
            updateState { it.copy(isLoading = true, error = null) }

            when (
                val result = doInputManualUseCase(
                    nomorPolisi = model.nomorPolisi,
                    jenisKendaraan = model.selectedVehicle.label,
                    waktuMasuk = model.waktuMasuk,
                    zonaParkir = current.selectedZonaParkir,
                    lokasiParkir = current.selectedLokasiParkir
                )
            ) {
                is ApiCondition.AppSuccess -> {
                    updateState {
                        it.copy(
                            isLoading = false,
                            pembayaranModel = mergePembayaranDefaults(result.data)
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
                    sendEffect(
                        InputManualEffect.ShowToast(
                            result.exception.message ?: "Terjadi kesalahan"
                        )
                    )
                }
                is ApiCondition.AppLoading -> Unit
            }
        }
    }

    private fun mergePembayaranDefaults(pembayaran: PembayaranModel): PembayaranModel {
        val current = state.value.pembayaranModel
        val currentQr = current?.qrisSection?.qrContent
        val resultQr = pembayaran.qrisSection.qrContent

        return PembayaranModel(
            title = pembayaran.title.ifBlank {
                current?.title ?: "Pembayaran"
            },
            statusCard = pembayaran.statusCard.copy(
                title = pembayaran.statusCard.title.ifBlank {
                    current?.statusCard?.title ?: "Tiket berhasil dibuat!"
                },
                message = pembayaran.statusCard.message.ifBlank {
                    current?.statusCard?.message
                        ?: "Silakan tunjukkan QRIS ini kepada pengguna untuk melakukan pembayaran."
                }
            ),
            totalPembayaran = pembayaran.totalPembayaran.takeIf { it > 0L }
                ?: current?.totalPembayaran
                ?: state.value.inputManualModel.tarifSummary.totalTarif,
            detailLabel = pembayaran.detailLabel.ifBlank {
                current?.detailLabel ?: "Lihat Detail"
            },
            qrisSection = pembayaran.qrisSection.copy(
                title = pembayaran.qrisSection.title.ifBlank {
                    current?.qrisSection?.title ?: "Scan & Bayar dengan QRIS"
                },
                qrContent = resultQr.copy(
                    sessionId = resultQr.sessionId.takeIf { it > 0L }
                        ?: currentQr?.sessionId
                        ?: 0L,
                    plat_nomor = resultQr.plat_nomor.ifBlank {
                        currentQr?.plat_nomor ?: state.value.inputManualModel.nomorPolisi
                    },
                    lokasi = resultQr.lokasi.ifBlank {
                        currentQr?.lokasi ?: state.value.selectedLokasiParkir
                    },
                    waktu_masuk = resultQr.waktu_masuk.ifBlank {
                        currentQr?.waktu_masuk ?: state.value.inputManualModel.waktuMasuk
                    },
                    durasi = resultQr.durasi.ifBlank {
                        currentQr?.durasi ?: state.value.inputManualModel.tarifSummary.durasiParkir
                    },
                    nominal = resultQr.nominal.takeIf { it > 0L }
                        ?: currentQr?.nominal
                        ?: state.value.inputManualModel.tarifSummary.totalTarif,
                    isPaid = resultQr.isPaid,
                    paymentStatus = resultQr.paymentStatus,
                    isExpired = resultQr.isExpired,
                    statusMessage = resultQr.statusMessage.ifBlank {
                        currentQr?.statusMessage.orEmpty()
                    }
                ),
                masaBerlakuQr = pembayaran.qrisSection.masaBerlakuQr.ifBlank {
                    current?.qrisSection?.masaBerlakuQr ?: "QRIS berlaku selama 15 menit"
                },
                countdownSeconds = pembayaran.qrisSection.countdownSeconds.takeIf { it > 0L }
                    ?: current?.qrisSection?.countdownSeconds
                    ?: (15 * 60L),
                alternativeLabel = pembayaran.qrisSection.alternativeLabel.ifBlank {
                    current?.qrisSection?.alternativeLabel ?: "atau"
                }
            ),
            paymentOptionsTitle = pembayaran.paymentOptionsTitle.ifBlank {
                current?.paymentOptionsTitle ?: "Pilih Opsi Pembayaran Lain"
            },
            paymentOptions = pembayaran.paymentOptions.ifEmpty {
                current?.paymentOptions.orEmpty()
            },
            printButtonLabel = pembayaran.printButtonLabel.ifBlank {
                current?.printButtonLabel ?: "Cetak Struk"
            }
        )
    }

    private fun startPolling() {
        if (pollingJob?.isActive == true) return
        val sessionId = state.value.pembayaranModel?.qrisSection?.qrContent?.sessionId ?: 0L
        if (sessionId <= 0L) return

        pollingJob = viewModelScope.launch {
            while (true) {
                refreshStatus()

                val qrState = state.value.pembayaranModel?.qrisSection?.qrContent
                val isFinal = qrState?.isPaid == true ||
                    qrState?.isExpired == true ||
                    (qrState?.paymentStatus ?: 0L) < 0L

                if (isFinal) {
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
        val sessionId = state.value.pembayaranModel?.qrisSection?.qrContent?.sessionId ?: 0L
        if (sessionId <= 0L) {
            updateState {
                it.copy(
                    isCheckingPaymentStatus = false,
                    error = "Session pembayaran tidak valid"
                )
            }
            return
        }

        viewModelScope.launch {
            doGetPembayaranStatusUseCase(sessionId = sessionId).collectLatest { result ->
                when (result) {
                    is ApiCondition.AppLoading -> {
                        updateState { it.copy(isCheckingPaymentStatus = true) }
                    }
                    is ApiCondition.AppSuccess -> {
                        val merged = mergePembayaranStatus(result.data)
                        updateState {
                            it.copy(
                                isCheckingPaymentStatus = false,
                                pembayaranModel = merged,
                                paymentStatus = merged.qrisSection.qrContent.paymentStatus,
                                statusMessage = merged.qrisSection.qrContent.statusMessage,
                                error = null
                            )
                        }

                        when {
                            merged.qrisSection.qrContent.isPaid -> {
                                sendEffect(InputManualEffect.ShowPaymentSuccess)
                                stopPolling()
                            }
                            merged.qrisSection.qrContent.isExpired ||
                                merged.qrisSection.qrContent.paymentStatus < 0L -> {
                                sendEffect(InputManualEffect.ShowPaymentFailed)
                                stopPolling()
                            }
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

    private fun mergePembayaranStatus(statusResult: PembayaranModel): PembayaranModel {
        val current = state.value.pembayaranModel
        val currentQr = current?.qrisSection?.qrContent
        val resultQr = statusResult.qrisSection.qrContent

        return PembayaranModel(
            title = statusResult.title.ifBlank {
                current?.title ?: "Pembayaran"
            },
            statusCard = statusResult.statusCard.copy(
                title = statusResult.statusCard.title.ifBlank {
                    current?.statusCard?.title ?: "Status Pembayaran"
                },
                message = statusResult.statusCard.message.ifBlank {
                    resultQr.statusMessage.ifBlank {
                        current?.statusCard?.message ?: "Status pembayaran sedang diperbarui."
                    }
                },
                isSuccess = resultQr.isPaid || statusResult.statusCard.isSuccess
            ),
            totalPembayaran = statusResult.totalPembayaran.takeIf { it > 0L }
                ?: resultQr.nominal.takeIf { it > 0L }
                ?: current?.totalPembayaran
                ?: state.value.inputManualModel.tarifSummary.totalTarif,
            detailLabel = statusResult.detailLabel.ifBlank {
                current?.detailLabel ?: "Lihat Detail"
            },
            qrisSection = statusResult.qrisSection.copy(
                title = statusResult.qrisSection.title.ifBlank {
                    current?.qrisSection?.title ?: "Scan & Bayar dengan QRIS"
                },
                qrContent = resultQr.copy(
                    sessionId = resultQr.sessionId.takeIf { it > 0L }
                        ?: currentQr?.sessionId
                        ?: 0L,
                    plat_nomor = resultQr.plat_nomor.ifBlank {
                        currentQr?.plat_nomor ?: state.value.inputManualModel.nomorPolisi
                    },
                    lokasi = resultQr.lokasi.ifBlank {
                        currentQr?.lokasi ?: state.value.selectedLokasiParkir
                    },
                    waktu_masuk = resultQr.waktu_masuk.ifBlank {
                        currentQr?.waktu_masuk ?: state.value.inputManualModel.waktuMasuk
                    },
                    durasi = resultQr.durasi.ifBlank {
                        currentQr?.durasi ?: state.value.inputManualModel.tarifSummary.durasiParkir
                    },
                    nominal = resultQr.nominal.takeIf { it > 0L }
                        ?: currentQr?.nominal
                        ?: state.value.inputManualModel.tarifSummary.totalTarif,
                    isPaid = resultQr.isPaid,
                    paymentStatus = resultQr.paymentStatus,
                    isExpired = resultQr.isExpired,
                    statusMessage = resultQr.statusMessage.ifBlank {
                        currentQr?.statusMessage.orEmpty()
                    }
                ),
                masaBerlakuQr = statusResult.qrisSection.masaBerlakuQr.ifBlank {
                    current?.qrisSection?.masaBerlakuQr ?: "QRIS sedang diperiksa"
                },
                countdownSeconds = statusResult.qrisSection.countdownSeconds.takeIf { it > 0L }
                    ?: current?.qrisSection?.countdownSeconds
                    ?: 0L,
                alternativeLabel = statusResult.qrisSection.alternativeLabel.ifBlank {
                    current?.qrisSection?.alternativeLabel ?: "atau"
                }
            ),
            paymentOptionsTitle = statusResult.paymentOptionsTitle.ifBlank {
                current?.paymentOptionsTitle ?: "Pilih Opsi Pembayaran Lain"
            },
            paymentOptions = statusResult.paymentOptions.ifEmpty {
                current?.paymentOptions.orEmpty()
            },
            printButtonLabel = statusResult.printButtonLabel.ifBlank {
                current?.printButtonLabel ?: "Cetak Struk"
            }
        )
    }

    private fun buildAreaParkirText(lokasi: String, zona: String): String {
        val left = lokasi.ifBlank { "Pilih Lokasi Parkir" }
        val right = zona.ifBlank { "Pilih Zona Parkir" }
        return "$left - $right"
    }

    private fun sendEffect(effect: InputManualEffect) {
        updateState { it.copy(inputManualEffect = effect) }
    }
}
