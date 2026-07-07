package com.epy.linespotv2.data.repository_impl.riwayat

import androidx.datastore.preferences.protobuf.LazyStringArrayList.emptyList
import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.data.remote.api.ApiService
import com.epy.linespotv2.data.remote.dto.riwayat.RiwayatRequestDto
import com.epy.linespotv2.data.remote.mapper.riwayat.toDomain
import com.epy.linespotv2.data.remote.mapper.riwayat.toDto
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
            if (body?.sections == emptyList()) {
                return ApiCondition.AppSuccess(body.toDomain())
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
}
