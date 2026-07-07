package com.epy.linespotv2.presentation.settings

sealed class SettingsIntent {
    object LoadSettingsPage           : SettingsIntent()
    object OnLogoutClicked       : SettingsIntent()
    object OnConfirmLogout       : SettingsIntent()
    object OnDismissLogout       : SettingsIntent()
    object OnBackClicked         : SettingsIntent()
    object NavigateToProfil : SettingsIntent()
    object NavigateToBantuanFaq : SettingsIntent()
    object NavigateToKeamanan : SettingsIntent()
    object NavigateToMetodePembayaran : SettingsIntent()
    object NavigateToPrivasi : SettingsIntent()
    object NavigateToSyaratKetentuan : SettingsIntent()
    object NavigateToTentangAplikasi : SettingsIntent()
}