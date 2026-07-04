package com.epy.linespotv2.domain.usecase

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.utils.Dispatcher
import com.epy.linespotv2.core.utils.isValidEmail
import com.epy.linespotv2.domain.model.auth.RegisterModel
import com.epy.linespotv2.domain.repository.RegisterRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val registerRepository: RegisterRepository,
    private val dispatcher: Dispatcher
) {
    suspend operator fun invoke(
        fullName: String,
        nik: String,
        email: String,
        phone: String,
        username: String,
        password: String,
        confirmPassword: String
    ): ApiCondition<RegisterModel> = withContext(dispatcher.io) {

        if (fullName.isBlank())
            return@withContext ApiCondition.AppFailure(Exception("Nama lengkap tidak boleh kosong"))

        if (nik.isBlank())
            return@withContext ApiCondition.AppFailure(Exception("NIK tidak boleh kosong"))

        if (nik.length != 16)
            return@withContext ApiCondition.AppFailure(Exception("NIK harus 16 digit"))

        if (email.isBlank())
            return@withContext ApiCondition.AppFailure(Exception("Email tidak boleh kosong"))

        if (!email.isValidEmail())
            return@withContext ApiCondition.AppFailure(Exception("Format email tidak valid"))

        if (phone.isBlank())
            return@withContext ApiCondition.AppFailure(Exception("Nomor HP tidak boleh kosong"))

        if (username.isBlank())
            return@withContext ApiCondition.AppFailure(Exception("Username tidak boleh kosong"))

        if (password.isBlank())
            return@withContext ApiCondition.AppFailure(Exception("Password tidak boleh kosong"))

        if (password.length < 8)
            return@withContext ApiCondition.AppFailure(Exception("Password minimal 8 karakter"))

        if (password != confirmPassword)
            return@withContext ApiCondition.AppFailure(Exception("Konfirmasi password tidak cocok"))

        registerRepository.register(fullName, nik, email, phone, username, password)
    }
}