package com.epy.linespotv2.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epy.linespotv2.core.preferences.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Event untuk navigasi
sealed class SplashUiEvent {
    object NavigateToCustomerHome : SplashUiEvent()
    object NavigateToJukirHome : SplashUiEvent()
    object NavigateToLogin : SplashUiEvent()
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val prefs: AppPreferences
) : ViewModel() {

    private val _uiEvent = MutableStateFlow<SplashUiEvent?>(null)
    val uiEvent: StateFlow<SplashUiEvent?> = _uiEvent

    // Properti ini hanya untuk membaca status login
//    val isLoggedIn: Boolean
//        get() = prefs.isLoggedIn()

    // Fungsi untuk mengecek status dan menentukan navigasi
    fun checkLoginAndNavigate() {
        viewModelScope.launch {
            if (prefs.isLoggedIn()) {
                // 2. FILTERING BERDASARKAN ROLE
                // Asumsi prefs.roleId menyimpan String "1" atau "2"
                val navigationEvent = when (prefs.roleId.toString()) {
                    "1" -> SplashUiEvent.NavigateToCustomerHome
                    "2" -> SplashUiEvent.NavigateToJukirHome
                    else -> {
                        // Fallback: Jika user login tapi roleId tidak dikenali, paksa kembali ke login
                        SplashUiEvent.NavigateToLogin
                    }
                }
                _uiEvent.value = navigationEvent
            } else {
                _uiEvent.value = SplashUiEvent.NavigateToLogin
            }
        }
    }

    // Fungsi untuk mengonsumsi event (penting agar event tidak di-trigger ulang)
    fun consumeEvent() {
        _uiEvent.value = null
    }
}
