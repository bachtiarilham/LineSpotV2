package com.epy.linespotv2.data.repository_impl

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.data.remote.api.ApiService
import com.epy.linespotv2.data.remote.dto.laporan.LaporanRequestDto
import com.epy.linespotv2.data.remote.mapper.toDomain
import com.epy.linespotv2.domain.model.laporan.LaporanModel
import com.epy.linespotv2.domain.repository.LaporanRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LaporanRepositoryImpl @Inject constructor(
    private val api: ApiService
) : LaporanRepository {

    override suspend fun getLaporanPage(
        userId: Long,
        username: String,
        roleId: Long,
        startDate : String,
        endDate : String,
        lokasi : String
    ): ApiCondition<LaporanModel> =
        try {
            val response = api.getLaporanPage(LaporanRequestDto(
                    userId = userId,
                    username = username,
                    roleId = roleId,
                    startDate = startDate,
                    endDate = endDate,
                    lokasi = lokasi,
                )
            )
            val message = response.message?.takeIf { it.isNotBlank() }
            if (!response.success) {
                ApiCondition.AppFailure(
                    IllegalStateException(message ?: "Permintaan laporan gagal")
                )
            }
            val body = response.data
            if (body == null) {
                ApiCondition.AppFailure(
                    IllegalStateException(message ?: "Data laporan kosong")
                )
            }
            ApiCondition.AppSuccess(body.toDomain())
        } catch (e: HttpException) {
            val errorMessage = when (e.code()) {
                401 -> "Autentikasi gagal. Silakan login kembali."
                403 -> "Akses ditolak."
                404 -> "Data laporan tidak ditemukan."
                500 -> "Terjadi kesalahan pada server."
                else -> e.message ?: "Terjadi kesalahan saat memuat laporan"
            }
            ApiCondition.AppFailure(IllegalStateException(errorMessage, e))
        } catch (e: IOException) {
            ApiCondition.AppFailure(IOException("Gagal terhubung ke server", e))
        } catch (e: Exception) {
            ApiCondition.AppFailure(e)
        }
}
