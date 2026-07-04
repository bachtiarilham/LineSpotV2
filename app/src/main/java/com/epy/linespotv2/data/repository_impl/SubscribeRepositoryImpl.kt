package com.epy.linespotv2.data.repository_impl

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.utils.Dispatcher
import com.epy.linespotv2.data.remote.api.ApiService
import com.epy.linespotv2.data.remote.mapper.toDomain
import com.epy.linespotv2.domain.model.SubscribeModel
import com.epy.linespotv2.domain.repository.SubscribeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SubscribeRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val dispatcher: Dispatcher
) : SubscribeRepository {

    override fun getPage(): Flow<ApiCondition<SubscribeModel>> = flow {
        emit(ApiCondition.AppLoading)

        try {
            val response = api.getPage()
            val message = response.message?.takeIf { it.isNotBlank() }

            if (!response.success) {
                emit(
                    ApiCondition.AppFailure(
                        IllegalStateException(message ?: "Permintaan subscribe gagal")
                    )
                )
                return@flow
            }

            val body = response.data
            if (body == null) {
                emit(
                    ApiCondition.AppFailure(
                        IllegalStateException(message ?: "Data subscribe kosong")
                    )
                )
                return@flow
            }

            emit(ApiCondition.AppSuccess(body.toDomain()))
        } catch (e: HttpException) {
            val errorMessage = when (e.code()) {
                401 -> "Autentikasi gagal. Silakan login kembali."
                403 -> "Akses ditolak."
                404 -> "Data subscribe tidak ditemukan."
                500 -> "Terjadi kesalahan pada server."
                else -> e.message ?: "Terjadi kesalahan saat memuat subscribe"
            }
            emit(ApiCondition.AppFailure(IllegalStateException(errorMessage, e)))
        } catch (e: IOException) {
            emit(ApiCondition.AppFailure(IOException("Gagal terhubung ke server", e)))
        } catch (e: Exception) {
            emit(ApiCondition.AppFailure(e))
        }
    }.flowOn(dispatcher.io)
}
