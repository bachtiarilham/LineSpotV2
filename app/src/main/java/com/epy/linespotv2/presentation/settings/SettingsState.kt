package com.epy.linespotv2.presentation.settings

import com.epy.linespotv2.domain.model.profile.CustomerModel

data class SettingsState(
    val isLoading: Boolean         = false,
    val userModel: CustomerModel?        = null,
    val error: String?             = null,
    val showLogoutDialog: Boolean  = false,
    val settingsEffect: SettingsEffect? = null,
)