package com.epy.linespotv2.domain.usecase.user

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.utils.Dispatcher
import com.epy.linespotv2.domain.model.UserModel
import com.epy.linespotv2.domain.repository.UserRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val dispatcher: Dispatcher
) {
    suspend operator fun invoke(): ApiCondition<UserModel> =
        withContext(dispatcher.io) {
            userRepository.getProfile()
        }
}