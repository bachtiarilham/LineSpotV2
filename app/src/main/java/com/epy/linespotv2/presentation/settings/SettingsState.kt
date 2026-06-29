package com.epy.linespotv2.presentation.settings

import com.epy.linespotv2.domain.model.UserModel

data class SettingsState(
    val isLoading: Boolean         = false,
    val profile: UserModel?        = null,
    val error: String?             = null,
    val showLogoutDialog: Boolean  = false,
    val settingsEffect: SettingsEffect? = null
)