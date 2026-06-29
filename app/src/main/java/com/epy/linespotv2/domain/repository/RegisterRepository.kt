package com.epy.linespotv2.domain.repository

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.RegisterModel

interface RegisterRepository {
    suspend fun register(
        fullName: String,
        nik: String,
        email: String,
        phone: String,
        username: String,
        password: String
    ): ApiCondition<RegisterModel>
}