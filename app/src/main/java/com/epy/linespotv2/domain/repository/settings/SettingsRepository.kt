package com.epy.linespotv2.domain.repository.settings

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.profile.CustomerModel
import com.epy.linespotv2.domain.model.profile.JukirModel

interface SettingsRepository {
    suspend fun getJukirProfile(): ApiCondition<JukirModel>
    suspend fun getCustomerProfile(): ApiCondition<CustomerModel>

}