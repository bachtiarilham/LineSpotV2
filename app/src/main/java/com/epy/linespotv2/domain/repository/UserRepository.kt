// domain/repository/UserRepository.kt
package com.epy.linespotv2.domain.repository

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.UserModel

interface UserRepository {
    suspend fun getProfile(): ApiCondition<UserModel>
    suspend fun logout()
}