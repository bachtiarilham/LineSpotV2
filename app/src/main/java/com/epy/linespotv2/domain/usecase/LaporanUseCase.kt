package com.epy.linespotv2.domain.usecase

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.utils.Dispatcher
import com.epy.linespotv2.core.utils.parseIndonesiaDateOrNull
import com.epy.linespotv2.domain.model.laporan.LaporanModel
import com.epy.linespotv2.domain.repository.LaporanRepository
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LaporanUseCase @Inject constructor(
    private val repository : LaporanRepository,
    private val dispatcher : Dispatcher
){
    suspend operator fun invoke(
        userId: Long,
        username: String,
        roleId: Long,
        startDate : String,
        endDate : String,
        lokasi : String
    ): ApiCondition<LaporanModel> = withContext(dispatcher.io) {
        if (startDate.isBlank() || endDate.isBlank()) {
            return@withContext ApiCondition.AppFailure(
                Exception("Tanggal awal dan tanggal akhir tidak boleh kosong")
            )
        }

        val parsedStartDate = startDate.parseIndonesiaDateOrNull()
        val parsedEndDate = endDate.parseIndonesiaDateOrNull()

        if (parsedStartDate == null || parsedEndDate == null) {
            return@withContext ApiCondition.AppFailure(
                Exception("Format tanggal tidak valid")
            )
        }

        if (parsedEndDate.before(parsedStartDate)) {
            return@withContext ApiCondition.AppFailure(
                Exception("Tanggal akhir tidak boleh lebih kecil dari tanggal awal")
            )
        }

        val diffInMillis = parsedEndDate.time - parsedStartDate.time
        val diffInDaysInclusive = TimeUnit.MILLISECONDS.toDays(diffInMillis) + 1

        if (diffInDaysInclusive > 31) {
            return@withContext ApiCondition.AppFailure(
                Exception("Rentang tanggal maksimal 31 hari")
            )
        }

        return@withContext repository.getLaporanPage(
            userId,
            username,
            roleId,
            startDate,
            endDate,
            lokasi
        )
    }
}
