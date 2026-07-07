package com.epy.linespotv2.domain.usecase.auth

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.utils.Dispatcher
import com.epy.linespotv2.core.utils.isValidEmail
import com.epy.linespotv2.core.utils.PasswordHash
import com.epy.linespotv2.domain.model.auth.LoginReqModel
import com.epy.linespotv2.domain.model.auth.LoginRespModel
import com.epy.linespotv2.domain.repository.auth.AuthRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject


class LoginUseCase @Inject constructor(
    private val loginRepository: AuthRepository,
    private val dispatcher : Dispatcher
){
    operator suspend fun invoke ( reqModel : LoginReqModel):  ApiCondition<LoginRespModel> = withContext(dispatcher.io) {
        if (reqModel.identity.isBlank()){
            val errorEmailKosong = Exception("Email/Username tidak boleh kosong")
            return@withContext ApiCondition.AppFailure(errorEmailKosong)
        }

        if (!reqModel.identity.isValidEmail())
            return@withContext ApiCondition.AppFailure(Exception("Format email tidak valid"))

        if (reqModel.password.isBlank()){
            val errorPasswordKosong = Exception("Password tidak boleh kosong")
            return@withContext ApiCondition.AppFailure(errorPasswordKosong)
        }

        val hashedPassword = try {
            PasswordHash.hashPassword(reqModel.password)
        } catch (exception: Exception) {
            return@withContext ApiCondition.AppFailure(exception)
        }

        return@withContext loginRepository.login(reqModel)
    }
}
