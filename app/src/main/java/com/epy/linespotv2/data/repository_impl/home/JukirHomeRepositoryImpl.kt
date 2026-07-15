package com.epy.linespotv2.data.repository_impl.home

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.preferences.AppPreferences
import com.epy.linespotv2.core.utils.Dispatcher
import com.epy.linespotv2.data.local.dao.HomeDao
import com.epy.linespotv2.data.local.entity.JukirEntity
import com.epy.linespotv2.data.remote.api.ApiService
import com.epy.linespotv2.data.remote.mapper.home.toJukirDomain as toJukirHomeDomain
import com.epy.linespotv2.data.remote.mapper.profile.toEntity
import com.epy.linespotv2.data.remote.mapper.profile.toJukirDomain as toJukirProfileDomain
import com.epy.linespotv2.domain.model.home.JukirHomeModel
import com.epy.linespotv2.domain.repository.home.JukirHomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class JukirHomeRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val homeDao: HomeDao,
    private val dispatcher: Dispatcher,
    private val prefs: AppPreferences
) : JukirHomeRepository {

    override fun getJukirHomePage(): Flow<ApiCondition<JukirHomeModel>> = flow {
        homeDao.getJukirCache()
            ?.takeIf { it.isStillValid() }
            ?.let { cachedJukir ->
                emit(
                    ApiCondition.AppSuccess(
                        JukirHomeModel(
                            jukirModel = cachedJukir.toJukirProfileDomain(),
                            contents = emptyList()
                        )
                    )
                )
            }

        try {
            val response = api.getJukirHomePage()
            val message = response.message?.takeIf { it.isNotBlank() }

            if (!response.success) {
                emit(
                    ApiCondition.AppFailure(
                        IllegalStateException(message ?: "Permintaan home gagal")
                    )
                )
                return@flow
            }

            val body = response.data
            if (body == null) {
                emit(
                    ApiCondition.AppFailure(
                        IllegalStateException(message ?: "Data home kosong")
                    )
                )
                return@flow
            }

            val home = body.toJukirHomeDomain()
            home.jukirModel?.let { jukirModel ->
                homeDao.insertJukirCache(jukirModel.toEntity())
            }
            emit(ApiCondition.AppSuccess(home))
        } catch (e: HttpException) {
            if (e.code() == 401) {
                prefs.clear()
            }

            val errorMessage = when (e.code()) {
                401 -> "Autentikasi gagal. Silakan login kembali."
                403 -> "Akses ditolak."
                404 -> "Data home tidak ditemukan."
                500 -> "Terjadi kesalahan pada server."
                else -> e.message ?: "Terjadi kesalahan saat memuat home"
            }
            emit(ApiCondition.AppFailure(IllegalStateException(errorMessage, e)))
        } catch (e: IOException) {
            emit(ApiCondition.AppFailure(IOException("Gagal terhubung ke server", e)))
        } catch (e: Exception) {
            emit(ApiCondition.AppFailure(e))
        }
    }.flowOn(dispatcher.io)

    private fun JukirEntity.isStillValid(): Boolean {
        return System.currentTimeMillis() - cachedAt <= CACHE_TTL_MS
    }

    companion object {
        private const val CACHE_TTL_MS = 5 * 60 * 1000L
    }
}
