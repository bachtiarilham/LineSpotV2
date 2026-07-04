package com.epy.linespotv2.domain.usecase

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.utils.Dispatcher
import com.epy.linespotv2.core.utils.isValidEmail
import com.epy.linespotv2.domain.model.auth.LoginModel
import com.epy.linespotv2.domain.repository.LoginRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject


class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
    private val dispatcher : Dispatcher
){
    operator suspend fun invoke (
        email : String,
        password : String
    ):  ApiCondition<LoginModel> = withContext(dispatcher.io) {
        if (email.isBlank()){
            val errorEmailKosong = Exception("Email/Username tidak boleh kosong")
            return@withContext ApiCondition.AppFailure(errorEmailKosong)
        }

        if (!email.isValidEmail())
            return@withContext ApiCondition.AppFailure(Exception("Format email tidak valid"))

        if (password.isBlank()){
            val errorPasswordKosong = Exception("Password tidak boleh kosong")
            return@withContext ApiCondition.AppFailure(errorPasswordKosong)
        }
        return@withContext loginRepository.login(email,password)
    }
}