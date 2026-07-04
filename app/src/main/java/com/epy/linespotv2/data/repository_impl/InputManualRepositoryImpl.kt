package com.epy.linespotv2.data.repository_impl

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.data.remote.api.ApiService
import com.epy.linespotv2.data.remote.dto.payment.PostParkingRequestDto
import com.epy.linespotv2.data.remote.mapper.toDomain
import com.epy.linespotv2.domain.model.PembayaranModel
import com.epy.linespotv2.domain.repository.InputManualRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class InputManualRepositoryImpl @Inject constructor(
    private val api: ApiService
) : InputManualRepository {

    override suspend fun postParking(
        nomorPolisi: String,
        jenisKendaraan: String,
        waktuMasuk: String,
        zonaParkir: String,
        lokasiParkir: String
    ): ApiCondition<PembayaranModel> {
        return try {
            val response = api.postParking(
                PostParkingRequestDto(
                    nomorPolisi = nomorPolisi,
                    jenisKendaraan = jenisKendaraan,
                    waktuMasuk = waktuMasuk,
                    zonaParkir = zonaParkir,
                    lokasiParkir = lokasiParkir,
                )
            )

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
            ApiCondition.AppFailure(IOException("Gagal terhubung ke server saat memproses input manual", e))
        } catch (e: Exception) {
            ApiCondition.AppFailure(e)
        }
    }
}
