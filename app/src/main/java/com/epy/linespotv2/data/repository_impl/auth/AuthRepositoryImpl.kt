package com.epy.linespotv2.data.repository_impl.auth

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.preferences.AppPreferences
import com.epy.linespotv2.core.preferences.TarifItem
import com.epy.linespotv2.data.remote.api.ApiService
import com.epy.linespotv2.data.remote.mapper.auth.prefs.toPrefs
import com.epy.linespotv2.data.remote.mapper.auth.toDomain
import com.epy.linespotv2.data.remote.mapper.auth.toDto
import com.epy.linespotv2.domain.model.auth.ChangePasswordReqModel
import com.epy.linespotv2.domain.model.auth.LoginReqModel
import com.epy.linespotv2.domain.model.auth.LoginRespModel
import com.epy.linespotv2.domain.model.auth.RegisterReqModel
import com.epy.linespotv2.domain.model.auth.UserModel
import com.epy.linespotv2.domain.repository.auth.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api : ApiService,
    private val prefs: AppPreferences
) : AuthRepository {

    override suspend fun login(reqModel: LoginReqModel): ApiCondition<LoginRespModel> =
        try {
            val response = api.login(reqModel.toDto())

            if (response.success && response.data!= null){
                val userPrefs = response.data.toPrefs()
                prefs.saveUser(userPrefs)
                ApiCondition.AppSuccess(response.data.toDomain())
            } else {
                ApiCondition.AppFailure(Exception(response.message ?: "login gagal"))
            }
        } catch (e: Exception) {
            ApiCondition.AppFailure(e)
        }

    override suspend fun register(
        reqModel: RegisterReqModel
    ): ApiCondition<String> =
        runCatching {
            val response = api.register(reqModel.toDto())

            if (!response.success) {
                throw Exception(response.message ?: "Registrasi gagal")
            }

            response.message?.takeIf { it.isNotBlank() } ?: "Registrasi berhasil, silahkan login"
        }.fold(
            onSuccess = { ApiCondition.AppSuccess(it) },
            onFailure = { ApiCondition.AppFailure(it) }
        )

    override suspend fun getProfile(): ApiCondition<UserModel> =
        try {
            val data = api.getCurrentUser().data?.toDomain()
                ?: throw Exception("Data profil tidak ditemukan")

            prefs.fullName = data.fullName
            prefs.email = data.email
            prefs.phone = data.phone
            prefs.username = data.username
            prefs.nik = data.nik
            prefs.userId = data.userId
            prefs.roleId = data.role
            prefs.zona = data.zona
            prefs.lokasi = data.lokasi
            prefs.tarif = data.tarif.map { tarif ->
                TarifItem(
                    kendaraan = tarif.kendaraan,
                    nominal = tarif.nominal.toInt()
                )
            }

            ApiCondition.AppSuccess(data)
        } catch (e: Exception) {
            ApiCondition.AppFailure(e)
        }

    override suspend fun logout() {
        prefs.clear()
    }

    override suspend fun changePassword(reqModel: ChangePasswordReqModel): ApiCondition<String> =
        runCatching {
            val response = api.changePassword(reqModel.toDto())

            if (!response.success) {
                throw Exception(response.message ?: "Registrasi gagal")
            }

            response.message?.takeIf { it.isNotBlank() } ?: "Registrasi berhasil, silahkan login"
        }.fold(
            onSuccess = { ApiCondition.AppSuccess(it) },
            onFailure = { ApiCondition.AppFailure(it) }
        )
}
