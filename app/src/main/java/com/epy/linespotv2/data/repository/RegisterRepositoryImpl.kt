// data/repository/RegisterRepositoryImpl.kt
package com.epy.linespotv2.data.repository

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.data.remote.api.ApiService
//import com.epy.linespotv2.data.remote.api.RegisterApi
import com.epy.linespotv2.data.remote.dto.RegisterRequestDto
import com.epy.linespotv2.data.remote.mapper.toDomain
import com.epy.linespotv2.domain.model.RegisterModel
import com.epy.linespotv2.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val api: ApiService
) : RegisterRepository {

    override suspend fun register(
        fullName: String,
        nik: String,
        email: String,
        phone: String,
        username: String,
        password: String
    ): ApiCondition<RegisterModel> =
        runCatching {
            api.register(
                RegisterRequestDto(
                    fullName = fullName,
                    nik      = nik,
                    email    = email,
                    phone    = phone,
                    username = username,
                    password = password
                )
            ).data?.toDomain() ?: throw Exception("Registrasi gagal, data kosong")
        }.fold(
            onSuccess = { ApiCondition.AppSuccess(it) },
            onFailure = { ApiCondition.AppFailure(it) }
        )
}