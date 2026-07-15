package com.epy.linespotv2.data.repository_impl.parking

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.data.remote.api.ApiService
import com.epy.linespotv2.data.remote.mapper.parking.toDomain
import com.epy.linespotv2.data.remote.mapper.parking.toDto
import com.epy.linespotv2.domain.model.parking.PostParkingReqModel
import com.epy.linespotv2.domain.model.parking.PostParkingRespModel
import com.epy.linespotv2.domain.repository.parking.PostParkingRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostParkingRepositoryImpl @Inject constructor(
    private val api: ApiService
) : PostParkingRepository {

    override suspend fun postParking( reqModel: PostParkingReqModel): ApiCondition<PostParkingRespModel> {
        return try {
            val response = api.postParking( reqModel.toDto())
            val message = response.message?.takeIf { it.isNotBlank() }
            if (!response.success) {
                return ApiCondition.AppFailure(
                    IllegalStateException(message ?: "Proses input manual gagal")
                )
            }

            val body = response.data
            if (body == null) {
                return ApiCondition.AppFailure(
                    IllegalStateException(message ?: "Data input manual kosong")
                )
            }

            ApiCondition.AppSuccess(body.toDomain())
        } catch (e: HttpException) {
            val errorMessage = when (e.code()) {
                401 -> "Autentikasi gagal. Silakan login kembali."
                403 -> "Akses ditolak."
                404 -> "Data input manual tidak ditemukan."
                500 -> "Terjadi kesalahan pada server."
                else -> e.message ?: "Terjadi kesalahan saat memproses input manual"
            }
            ApiCondition.AppFailure(IllegalStateException(errorMessage, e))
        } catch (e: IOException) {
            ApiCondition.AppFailure(
                IOException(
                    "Gagal terhubung ke server saat memproses input manual",
                    e
                )
            )
        } catch (e: Exception) {
            ApiCondition.AppFailure(e)
        }
    }
}