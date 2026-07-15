package com.epy.linespotv2.domain.usecase.settings

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.utils.Dispatcher
import com.epy.linespotv2.domain.model.profile.JukirModel
import com.epy.linespotv2.domain.repository.auth.AuthRepository
import com.epy.linespotv2.domain.repository.settings.SettingsRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetJukirProfileUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val dispatcher: Dispatcher
) {
    suspend operator fun invoke(): ApiCondition<JukirModel> =
        withContext(dispatcher.io) {
            settingsRepository.getJukirProfile()
        }
}