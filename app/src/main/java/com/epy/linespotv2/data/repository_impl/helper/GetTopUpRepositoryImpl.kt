package com.epy.linespotv2.data.repository_impl.helper

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.data.remote.api.ApiService
import com.epy.linespotv2.data.remote.mapper.helper.toModel
import com.epy.linespotv2.domain.model.helper.TopupModel
import com.epy.linespotv2.domain.repository.helper.GetTopUpRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTopUpRepositoryImpl @Inject constructor(
    private val api: ApiService
) : GetTopUpRepository {

    override suspend fun getTopUp(): ApiCondition<TopupModel> {
        return try {
            val response = api.getTopUp()
            val message = response.message?.takeIf { it.isNotBlank() }

            if (!response.success) {
                ApiCondition.AppFailure(
                    IllegalStateException(message ?: "Permintaan top up gagal")
                )
            } else {
                val body = response.data?.toModel()
                if (body == null) {
                    ApiCondition.AppFailure(
                        IllegalStateException(message ?: "Data top up kosong")
                    )
                } else {
                    ApiCondition.AppSuccess(body)
                }
            }
        } catch (e: HttpException) {
            val errorMessage = when (e.code()) {
                401 -> "Autentikasi gagal. Silakan login kembali."
                403 -> "Akses ditolak."
                404 -> "Data top up tidak ditemukan."
                500 -> "Terjadi kesalahan pada server."
                else -> e.message ?: "Terjadi kesalahan saat memuat top up"
            }
            ApiCondition.AppFailure(IllegalStateException(errorMessage, e))
        } catch (e: IOException) {
            ApiCondition.AppFailure(IOException("Gagal terhubung ke server", e))
        } catch (e: Exception) {
            ApiCondition.AppFailure(e)
        }
    }
}
