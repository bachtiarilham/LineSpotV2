package com.epy.linespotv2.presentation.settings

sealed interface SettingsEffect {
    object NavigateToLogin : SettingsEffect   // setelah logout
    object NavigateBack    : SettingsEffect   // tombol kembali
    object NavigateToProfil : SettingsEffect
    object NavigateToBantuanFaq : SettingsEffect
    object NavigateToKeamanan : SettingsEffect
    object NavigateToMetodePembayaran : SettingsEffect
    object NavigateToPrivasi : SettingsEffect
    object NavigateToSyaratKetentuan : SettingsEffect
    object NavigateToTentangAplikasi : SettingsEffect
}