package com.epy.linespotv2.data.repository

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.data.remote.api.ApiService
import com.epy.linespotv2.data.remote.dto.SubmitQrRequestDto
import com.epy.linespotv2.data.remote.mapper.toDomain
import com.epy.linespotv2.domain.model.HasilBayarParkirModel
import com.epy.linespotv2.domain.repository.PostQrRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostQrRepositoryImpl @Inject constructor(
    private val api: ApiService
) : PostQrRepository {

    override suspend fun submitQr(
        sessionId : Long,
        plat_nomor : String,
        lokasi : String,
        waktu_masuk : String,
        durasi : String,
        nominal : Long,
        isPaid : Boolean,
        paymentStatus : Long,
        isExpired : Boolean,
        statusMessage : String
    ): ApiCondition<HasilBayarParkirModel> =
        try {
            val response = api.postPaymentParking(SubmitQrRequestDto(
                    sessionId = sessionId,
                    plat_nomor = plat_nomor,
                    lokasi = lokasi,
                    waktu_masuk = waktu_masuk,
                    durasi =durasi,
                    nominal = nominal,
                    isPaid = isPaid,
                    paymentStatus = paymentStatus,
                    isExpired = isExpired,
                    statusMessage = statusMessage
                )
            )
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