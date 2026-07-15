package com.epy.linespotv2.data.repository_impl.settings

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.data.remote.api.ApiService
import com.epy.linespotv2.data.remote.mapper.profile.toDomain
import com.epy.linespotv2.domain.model.profile.CustomerModel
import com.epy.linespotv2.domain.model.profile.JukirModel
import com.epy.linespotv2.domain.repository.settings.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val api : ApiService
) : SettingsRepository {
    override suspend fun getJukirProfile(): ApiCondition<JukirModel> =
        try {
            val data = api.getJukirUser().data?.toDomain()
                ?: throw Exception("Data identitas jukir tidak ditemukan")
            ApiCondition.AppSuccess(data)
        } catch (e: Exception) {
            ApiCondition.AppFailure(e)
        }

    override suspend fun getCustomerProfile(): ApiCondition<CustomerModel> =
        try {
            val data = api.getCustomerUser().data?.toDomain()
                ?: throw Exception("Data identitas customer tidak ditemukan")
            ApiCondition.AppSuccess(data)
        } catch (e: Exception) {
            ApiCondition.AppFailure(e)
        }
}