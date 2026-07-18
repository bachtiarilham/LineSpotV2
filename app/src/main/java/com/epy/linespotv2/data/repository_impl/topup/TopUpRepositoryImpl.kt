package com.epy.linespotv2.data.repository_impl.topup

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.data.remote.api.ApiService
import com.epy.linespotv2.data.remote.mapper.topup.toDto
import com.epy.linespotv2.data.remote.mapper.topup.toModel
import com.epy.linespotv2.domain.model.topup.TopupCreateRequestModel
import com.epy.linespotv2.domain.model.topup.TopupCreateResponseModel
import com.epy.linespotv2.domain.model.topup.TopupStatusResponseModel
import com.epy.linespotv2.domain.repository.topup.TopUpRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TopUpRepositoryImpl @Inject constructor(
    private val api: ApiService
) : TopUpRepository {

    override suspend fun topUpCreate(
        reqModel: TopupCreateRequestModel
    ): ApiCondition<TopupCreateResponseModel>? {
        return try {
            val response = api.postTopUpCreate(reqModel.toDto())
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
                400 -> "Permintaan top up tidak valid."
                401 -> "Autentikasi gagal. Silakan login kembali."
                403 -> "Akses ditolak."
                404 -> "Data top up tidak ditemukan."
                500 -> "Terjadi kesalahan pada server."
                else -> e.message ?: "Terjadi kesalahan saat membuat top up"
            }
            ApiCondition.AppFailure(IllegalStateException(errorMessage, e))
        } catch (e: IOException) {
            ApiCondition.AppFailure(IOException("Gagal terhubung ke server", e))
        } catch (e: Exception) {
            ApiCondition.AppFailure(e)
        }
    }

    override suspend fun topUpStatus(
        req: String
    ): ApiCondition<TopupStatusResponseModel> {
        return try {
            val response = api.getTopUpStatus(req)
            val message = response.message?.takeIf { it.isNotBlank() }

            if (!response.success) {
                ApiCondition.AppFailure(
                    IllegalStateException(message ?: "Status top up gagal dimuat")
                )
            } else {
                val body = response.data?.toModel()
                if (body == null) {
                    ApiCondition.AppFailure(
                        IllegalStateException(message ?: "Data status top up kosong")
                    )
                } else {
                    ApiCondition.AppSuccess(body)
                }
            }
        } catch (e: HttpException) {
            val errorMessage = when (e.code()) {
                400 -> "Permintaan status top up tidak valid."
                401 -> "Autentikasi gagal. Silakan login kembali."
                403 -> "Akses ditolak."
                404 -> "Status top up tidak ditemukan."
                500 -> "Terjadi kesalahan pada server."
                else -> e.message ?: "Terjadi kesalahan saat memuat status top up"
            }
            ApiCondition.AppFailure(IllegalStateException(errorMessage, e))
        } catch (e: IOException) {
            ApiCondition.AppFailure(IOException("Gagal terhubung ke server", e))
        } catch (e: Exception) {
            ApiCondition.AppFailure(e)
        }
    }
}
