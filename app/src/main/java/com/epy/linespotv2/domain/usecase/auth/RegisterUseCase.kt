package com.epy.linespotv2.domain.usecase.auth

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.utils.Dispatcher
import com.epy.linespotv2.core.utils.PasswordHash
import com.epy.linespotv2.core.utils.isValidEmail
import com.epy.linespotv2.domain.model.auth.RegisterReqModel
import com.epy.linespotv2.domain.repository.auth.AuthRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val registerRepository: AuthRepository,
    private val dispatcher: Dispatcher
) {
    suspend operator fun invoke( reqModel : RegisterReqModel
    ): ApiCondition<String> = withContext(dispatcher.io) {

        if (reqModel.fullName.isBlank())
            return@withContext ApiCondition.AppFailure(Exception("Nama lengkap tidak boleh kosong"))

        if (reqModel.nik.isBlank())
            return@withContext ApiCondition.AppFailure(Exception("NIK tidak boleh kosong"))

        if (reqModel.nik.length != 16)
            return@withContext ApiCondition.AppFailure(Exception("NIK harus 16 digit"))

        if (reqModel.email.isBlank())
            return@withContext ApiCondition.AppFailure(Exception("Email tidak boleh kosong"))

        if (!reqModel.email.isValidEmail())
            return@withContext ApiCondition.AppFailure(Exception("Format email tidak valid"))

        if (reqModel.phone.isBlank())
            return@withContext ApiCondition.AppFailure(Exception("Nomor HP tidak boleh kosong"))

        if (reqModel.username.isBlank())
            return@withContext ApiCondition.AppFailure(Exception("Username tidak boleh kosong"))

        if (reqModel.password.isBlank())
            return@withContext ApiCondition.AppFailure(Exception("Password tidak boleh kosong"))

        if (reqModel.password.length < 8)
            return@withContext ApiCondition.AppFailure(Exception("Password minimal 8 karakter"))

        try {
            PasswordHash.hashPassword(reqModel.password)
        } catch (exception: Exception) {
            return@withContext ApiCondition.AppFailure(exception)
        }

        return@withContext registerRepository.register(reqModel)
    }
}
