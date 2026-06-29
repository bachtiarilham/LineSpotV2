package com.epy.linespotv2.presentation.settings

sealed interface SettingsEffect {
    object NavigateToLogin : SettingsEffect   // setelah logout
    object NavigateBack    : SettingsEffect   // tombol kembali
}