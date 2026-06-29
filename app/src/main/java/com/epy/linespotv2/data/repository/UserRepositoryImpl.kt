// data/repository/UserRepositoryImpl.kt
package com.epy.linespotv2.data.repository

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.preferences.AppPreferences
import com.epy.linespotv2.data.remote.api.ApiService
//import com.epy.linespotv2.data.remote.api.UserApi
import com.epy.linespotv2.data.remote.mapper.toDomain
import com.epy.linespotv2.domain.model.UserModel
import com.epy.linespotv2.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val prefs: AppPreferences
) : UserRepository {

    override suspend fun getProfile(): ApiCondition<UserModel> =
        try {
            val data = api.getCurrentUser().data?.toDomain()
                ?: throw Exception("Data profil tidak ditemukan")
            // Sinkron ke prefs supaya data lokal selalu fresh
            prefs.fullName = data.fullName
            prefs.email    = data.email
            prefs.phone    = data.phone
            prefs.roleId     = data.role
            ApiCondition.AppSuccess(data)
        } catch (e: Exception) {
            ApiCondition.AppFailure(e)
        }

    override suspend fun logout() {
        prefs.clear()   // hapus semua data termasuk token
    }
}