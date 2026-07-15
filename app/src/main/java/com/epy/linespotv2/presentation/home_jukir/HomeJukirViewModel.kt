package com.epy.linespotv2.presentation.home_jukir

import androidx.lifecycle.viewModelScope
import com.epy.linespotv2.core.base.BaseViewModel
import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.utils.inlocation.InLocation
import com.epy.linespotv2.core.utils.inlocation.LocationBoundaryStatus
import com.epy.linespotv2.core.utils.inlocation.LocationTracker
import com.epy.linespotv2.domain.usecase.home.JukirHomeUseCase
import com.epy.linespotv2.presentation.home_jukir.ui_model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeJukirViewModel @Inject constructor(
    private val doHomeUseCase: JukirHomeUseCase,
    private val locationTracker: LocationTracker
) : BaseViewModel<HomeJukirIntent, HomeJukirState>(HomeJukirState()) {
    private var balancedLocationJob: Job? = null
    private var preciseLocationJob: Job? = null

    override fun onIntent(intent: HomeJukirIntent) {
        when (intent) {
            is HomeJukirIntent.loadHomeJukir -> loadHome()
            is HomeJukirIntent.clickProfile -> sendEffect(HomeJukirEffect.NavigateToSettings)
            is HomeJukirIntent.clickBantuan -> sendEffect(HomeJukirEffect.NavigateToBantuan)
            is HomeJukirIntent.clickTopUp -> sendEffect(HomeJukirEffect.NavigateToTopUp)
            is HomeJukirIntent.clickInputManual -> sendEffect(HomeJukirEffect.NavigateToInputManual)
            is HomeJukirIntent.clickLaporan -> sendEffect(HomeJukirEffect.NavigateToLaporan)
            is HomeJukirIntent.clickRiwayat -> sendEffect(HomeJukirEffect.NavigateToRiwayat)
            is HomeJukirIntent.clickScanTiket -> sendEffect(HomeJukirEffect.NavigateToScanTicket)
            is HomeJukirIntent.clickNotification -> sendEffect(HomeJukirEffect.NavigateToNotification)
            is HomeJukirIntent.dismissError -> updateState { it.copy(error = null) }
        }
    }

    fun consumeEffect() {
        updateState { it.copy(homeJukirEffect = null) }
    }

    private fun loadHome() {
        viewModelScope.launch {
            updateState {
                it.copy(
                    isLoading = true,
                    isRefresh = false,
                    error = null
                )
            }

            doHomeUseCase().collect { result ->
                when (result) {
                    is ApiCondition.AppLoading -> {
                        updateState {
                            it.copy(
                                isLoading = true,
                                isRefresh = false,
                                error = null
                            )
                        }
                    }

                    is ApiCondition.AppSuccess -> {
                        updateState {
                            it.copy(
                                isLoading = false,
                                isRefresh = false,
                                homeJukirModel = result.data,
                                uiModel = result.data.toUiModel(),
                                error = null
                            )
                        }
                        startBoundaryMonitoring()
                    }

                    is ApiCondition.AppFailure -> {
                        val errorMessage = result.exception.message ?: "Terjadi kesalahan"

                        if (errorMessage.isSessionExpiredMessage()) {
                            sendEffect(HomeJukirEffect.SessionExpired)
                        }

                        updateState {
                            it.copy(
                                isLoading = false,
                                isRefresh = false,
                                error = errorMessage
                            )
                        }
                    }
                }
            }
        }
    }

    private fun sendEffect(effect: HomeJukirEffect) {
        updateState { it.copy(homeJukirEffect = effect) }
    }

    override fun onCleared() {
        balancedLocationJob?.cancel()
        preciseLocationJob?.cancel()
        super.onCleared()
    }

    private fun startBoundaryMonitoring() {
        balancedLocationJob?.cancel()
        preciseLocationJob?.cancel()

        balancedLocationJob = viewModelScope.launch {
            val isLocationEnabled = runCatching { locationTracker.isLocationEnabled() }
                .getOrDefault(false)

            if (!isLocationEnabled) {
                updateLocationStatus(LocationBoundaryStatus.LocationDisabled)
                return@launch
            }

            runCatching {
                locationTracker.monitorBalancedLocation().collectLatest { location ->
                    evaluateLocation(
                        latitude = location.latitude,
                        longitude = location.longitude
                    )
                }
            }.onFailure {
                updateState {
                    it.copy(
                        locationBoundaryStatus = LocationBoundaryStatus.InvalidCurrentLocation,
                        locationStatusMessage = "Izin atau layanan lokasi belum siap."
                    )
                }
            }
        }
    }

    private fun evaluateLocation(latitude: Double, longitude: Double) {
        val profile = state.value.homeJukirModel?.jukirModel ?: return
        val status = InLocation.evaluate(
            currentLatitude = latitude,
            currentLongitude = longitude,
            minLatitude = profile.minLatitude,
            maxLatitude = profile.maxLatitude,
            minLongitude = profile.minLongitude,
            maxLongitude = profile.maxLongitude,
            centerLatitude = profile.centerLatitude,
            centerLongitude = profile.centerLongitude
        )

        when (status) {
            LocationBoundaryStatus.NeedPreciseRecheck -> {
                updateState { it.copy(isCheckingPreciseLocation = true) }
                startPreciseRecheck()
            }
            else -> {
                preciseLocationJob?.cancel()
                updateLocationStatus(status)
            }
        }
    }

    private fun startPreciseRecheck() {
        if (preciseLocationJob?.isActive == true) return

        preciseLocationJob = viewModelScope.launch {
            runCatching {
                locationTracker.monitorPreciseLocation().collectLatest { location ->
                    val profile = state.value.homeJukirModel?.jukirModel ?: return@collectLatest
                    val status = InLocation.evaluate(
                        currentLatitude = location.latitude,
                        currentLongitude = location.longitude,
                        minLatitude = profile.minLatitude,
                        maxLatitude = profile.maxLatitude,
                        minLongitude = profile.minLongitude,
                        maxLongitude = profile.maxLongitude,
                        centerLatitude = profile.centerLatitude,
                        centerLongitude = profile.centerLongitude
                    )

                    if (status != LocationBoundaryStatus.NeedPreciseRecheck) {
                        updateLocationStatus(status)
                        preciseLocationJob?.cancel()
                    }
                }
            }.onFailure {
                updateState {
                    it.copy(
                        isCheckingPreciseLocation = false,
                        locationBoundaryStatus = LocationBoundaryStatus.InvalidCurrentLocation,
                        locationStatusMessage = "Gagal memverifikasi lokasi akurat."
                    )
                }
            }
        }
    }

    private fun updateLocationStatus(status: LocationBoundaryStatus) {
        updateState {
            it.copy(
                isCheckingPreciseLocation = status == LocationBoundaryStatus.NeedPreciseRecheck,
                locationBoundaryStatus = status,
                locationStatusMessage = status.toDisplayMessage()
            )
        }
    }

    private fun LocationBoundaryStatus.toDisplayMessage(): String {
        return when (this) {
            LocationBoundaryStatus.Inside -> "Anda berada di dalam area tugas."
            LocationBoundaryStatus.Outside -> "Anda berada di luar area tugas."
            LocationBoundaryStatus.NeedPreciseRecheck -> "Sedang memverifikasi lokasi akurat."
            LocationBoundaryStatus.LocationDisabled -> "Layanan lokasi perangkat sedang nonaktif."
            LocationBoundaryStatus.InvalidCurrentLocation -> "Lokasi perangkat belum valid."
            LocationBoundaryStatus.InvalidBoundary -> "Boundary lokasi area tugas belum valid."
        }
    }

    private fun String.isSessionExpiredMessage(): Boolean {
        return contains("autentikasi gagal", ignoreCase = true) ||
            contains("login kembali", ignoreCase = true)
    }
}
