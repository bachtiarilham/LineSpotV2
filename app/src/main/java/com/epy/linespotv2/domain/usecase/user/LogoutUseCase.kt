package com.epy.linespotv2.domain.usecase.user

import com.epy.linespotv2.core.utils.Dispatcher
import com.epy.linespotv2.domain.repository.auth.AuthRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val userRepository: AuthRepository,
    private val dispatcher: Dispatcher
) {
    suspend operator fun invoke() =
        withContext(dispatcher.io) {
            userRepository.logout()
        }
}