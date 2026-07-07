package com.epy.linespotv2.data.repository_impl.payment

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.data.remote.api.ApiService
import com.epy.linespotv2.data.remote.dto.payment.PostPaymentParkingRequestDto
import com.epy.linespotv2.data.remote.mapper.auth.toDomain
import com.epy.linespotv2.data.remote.mapper.payment.toDomain
import com.epy.linespotv2.data.remote.mapper.payment.toDto
import com.epy.linespotv2.domain.model.payment.PostPaymentParkingReqModel
import com.epy.linespotv2.domain.model.payment.PostPaymentParkingRespModel
import com.epy.linespotv2.domain.repository.payment.PostPaymentParkingRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostPaymentParkingRepositoryImpl @Inject constructor(
    private val api: ApiService
) : PostPaymentParkingRepository {

    override suspend fun postPaymentParking( reqModel: PostPaymentParkingReqModel): ApiCondition<PostPaymentParkingRespModel> =
        try {
            val response = api.postPaymentParking(reqModel.toDto())
            val message = response.message?.takeIf { it.isNotBlank() }
            if (!response.success) {
                ApiCondition.AppFailure(
                    IllegalStateException(message ?: "Permintaan riwayat gagal")
                )
            }
            val body = response.data
            if (body == null) {
                ApiCondition.AppFailure(
                    IllegalStateException(message ?: "Data riwayat kosong")
                )
            }
            ApiCondition.AppSuccess(body.toDomain())
        } catch (e: HttpException) {
            val errorMessage = when (e.code()) {
                401 -> "Autentikasi gagal. Silakan login kembali."
                403 -> "Akses ditolak."
                404 -> "Data riwayat tidak ditemukan."
                500 -> "Terjadi kesalahan pada server."
                else -> e.message ?: "Terjadi kesalahan saat memuat riwayat"
            }
            ApiCondition.AppFailure(IllegalStateException(errorMessage, e))
        } catch (e: IOException) {
            ApiCondition.AppFailure(IOException("Gagal terhubung ke server", e))
        } catch (e: Exception) {
            ApiCondition.AppFailure(e)
        }
}