package com.epy.linespotv2.presentation.settings

sealed class SettingsIntent {
    object LoadProfile           : SettingsIntent()
    object OnLogoutClicked       : SettingsIntent()
    object OnConfirmLogout       : SettingsIntent()
    object OnDismissLogout       : SettingsIntent()
    object OnBackClicked         : SettingsIntent()
}