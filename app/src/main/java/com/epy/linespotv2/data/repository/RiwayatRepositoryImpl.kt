package com.epy.linespotv2.data.repository

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.data.remote.api.ApiService
import com.epy.linespotv2.data.remote.dto.RiwayatRequestDto
import com.epy.linespotv2.data.remote.mapper.toDomain
import com.epy.linespotv2.domain.model.RiwayatModel
import com.epy.linespotv2.domain.model.RiwayatPaymentFilter
import com.epy.linespotv2.domain.model.RiwayatTransactionFilter
import com.epy.linespotv2.domain.model.RiwayatVehicleFilter
import com.epy.linespotv2.domain.repository.RiwayatRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RiwayatRepositoryImpl @Inject constructor(
    private val api: ApiService
) : RiwayatRepository {

    override suspend fun getRiwayatPage(
        userId: Long,
        username: String,
        roleId: Long,
        startDate : String,
        endDate : String,
        transaction : RiwayatTransactionFilter,
        payment : RiwayatPaymentFilter,
        vehicle : RiwayatVehicleFilter,
        lokasi : String
    ): ApiCondition<RiwayatModel> =
        try {
            val response = api.getRiwayatPage(RiwayatRequestDto(
                userId = userId,
                username = username,
                roleId = roleId,
                startDate = startDate,
                endDate = endDate,
                transaction = transaction,
                payment = payment,
                vehicle = vehicle,
                lokasi = lokasi,
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
