package com.epy.linespotv2.data.repository_impl

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.preferences.AppPreferences
import com.epy.linespotv2.core.preferences.TarifItem
import com.epy.linespotv2.data.remote.api.ApiService
//import com.epy.linespotv2.data.remote.api.LoginApi
import com.epy.linespotv2.data.remote.dto.auth.LoginRequestDto
import com.epy.linespotv2.domain.model.auth.LoginModel
import com.epy.linespotv2.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val api : ApiService,
    private val prefs: AppPreferences
) : LoginRepository {

    override suspend fun login(username: String, password: String): ApiCondition<LoginModel> =
        try {
            val response = api.login(LoginRequestDto(identity = username, password = password))

            if (response.success && response.data!= null){
                val data = response.data

                prefs.token        = data.tokens.accessToken
                prefs.refreshtoken = data.tokens.refreshToken
                prefs.userId       = data.user.userId
                prefs.username     = data.user.username
                prefs.fullName     = data.user.fullName
                prefs.email        = data.user.email
                prefs.phone        = data.user.phone
                prefs.nik          = data.user.nik
                prefs.roleId       = data.user.role
                prefs.zona         = data.user.zona
                prefs.lokasi       = data.user.lokasi
                prefs.tarif        = data.user.tarif.map { tarif ->
                    TarifItem(
                        kendaraan = tarif.kendaraan,
                        nominal = tarif.nominal.toIntOrNull() ?: 0
                    )
                }

                ApiCondition.AppSuccess(
                    LoginModel(
                        token = data.tokens.accessToken,
                        id = data.user.userId.toInt(),
                        nama = data.user.fullName,
                        email = data.user.email,
                        role = data.user.role,
                        avatar_url = "",
                        theme = "",
                        language = ""
                    )
                )
            } else {
                ApiCondition.AppFailure(Exception(response.message ?: "login gagal"))
            }
        } catch (e: Exception) {
            ApiCondition.AppFailure(e)
        }
}
