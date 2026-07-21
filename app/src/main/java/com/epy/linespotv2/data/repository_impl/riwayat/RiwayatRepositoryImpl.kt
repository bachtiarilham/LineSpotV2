package com.epy.linespotv2.data.repository_impl.riwayat

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.data.remote.api.ApiService
import com.epy.linespotv2.data.remote.mapper.riwayat.toDomain
import com.epy.linespotv2.data.remote.mapper.riwayat.toDomainModel
import com.epy.linespotv2.data.remote.mapper.riwayat.toDto
import com.epy.linespotv2.domain.model.riwayat.DetilParkirResponseModel
import com.epy.linespotv2.domain.model.riwayat.DetilTransaksiResponseModel
import com.epy.linespotv2.domain.model.riwayat.RiwayatRequestModel
import com.epy.linespotv2.domain.model.riwayat.RiwayatResponseModel
import com.epy.linespotv2.domain.repository.riwayat.RiwayatRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RiwayatRepositoryImpl @Inject constructor(
    private val api: ApiService
) : RiwayatRepository {

    override suspend fun getRiwayatPage(
        reqModel: RiwayatRequestModel
    ): ApiCondition<RiwayatResponseModel> {
        return try {
            val response = api.getRiwayatPage(reqModel.toDto())
            val message = response.message?.takeIf { it.isNotBlank() }

            if (!response.success) {
                return ApiCondition.AppFailure(
                    IllegalStateException(message ?: "Permintaan riwayat gagal")
                )
            }

            val body = response.data
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

    override suspend fun getParkirDetil(
        trxCode: String
    ): ApiCondition<DetilParkirResponseModel> {
        return try {
            val response = api.getParkirDetil(trxCode)
            val message = response.message?.takeIf { it.isNotBlank() }

            if (!response.success) {
                return ApiCondition.AppFailure(
                    IllegalStateException(message ?: "Permintaan detail parkir gagal")
                )
            }

            val body = response.data
            if (body != null) {
                ApiCondition.AppSuccess(body.toDomainModel())
            } else {
                ApiCondition.AppFailure(IllegalStateException("Data detail parkir kosong"))
            }
        } catch (e: HttpException) {
            val errorMessage = when (e.code()) {
                401 -> "Autentikasi gagal. Silakan login kembali."
                403 -> "Akses ditolak."
                404 -> "Detail parkir tidak ditemukan."
                500 -> "Terjadi kesalahan pada server."
                else -> e.message ?: "Terjadi kesalahan saat memuat detail parkir"
            }
            ApiCondition.AppFailure(IllegalStateException(errorMessage, e))
        } catch (e: IOException) {
            ApiCondition.AppFailure(IOException("Gagal terhubung ke server", e))
        } catch (e: Exception) {
            ApiCondition.AppFailure(e)
        }
    }

    override suspend fun getTransaksiDetil(
        topUpCode: String
    ): ApiCondition<DetilTransaksiResponseModel> {
        return try {
            val response = api.getTransaksiDetil(topUpCode)
            val message = response.message?.takeIf { it.isNotBlank() }

            if (!response.success) {
                return ApiCondition.AppFailure(
                    IllegalStateException(message ?: "Permintaan detail transaksi gagal")
                )
            }

            val body = response.data
            if (body != null) {
                ApiCondition.AppSuccess(body.toDomainModel())
            } else {
                ApiCondition.AppFailure(IllegalStateException("Data detail transaksi kosong"))
            }
        } catch (e: HttpException) {
            val errorMessage = when (e.code()) {
                401 -> "Autentikasi gagal. Silakan login kembali."
                403 -> "Akses ditolak."
                404 -> "Detail transaksi tidak ditemukan."
                500 -> "Terjadi kesalahan pada server."
                else -> e.message ?: "Terjadi kesalahan saat memuat detail transaksi"
            }
            ApiCondition.AppFailure(IllegalStateException(errorMessage, e))
        } catch (e: IOException) {
            ApiCondition.AppFailure(IOException("Gagal terhubung ke server", e))
        } catch (e: Exception) {
            ApiCondition.AppFailure(e)
        }
    }
}
