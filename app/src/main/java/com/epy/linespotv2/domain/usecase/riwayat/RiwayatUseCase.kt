package com.epy.linespotv2.domain.usecase.riwayat

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.utils.Dispatcher
import com.epy.linespotv2.core.utils.parseIndonesiaDateOrNull
import com.epy.linespotv2.domain.model.riwayat.RiwayatRequestModel
import com.epy.linespotv2.domain.model.riwayat.RiwayatResponseModel
import com.epy.linespotv2.domain.repository.riwayat.RiwayatRepository
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RiwayatUseCase @Inject constructor(
    private val repository : RiwayatRepository,
    private val dispatcher : Dispatcher
){
    suspend operator fun invoke( reqModel : RiwayatRequestModel): ApiCondition<RiwayatResponseModel> = withContext(dispatcher.io) {
        if (reqModel.startDate.isBlank() || reqModel.endDate.isBlank()) {
            return@withContext ApiCondition.AppFailure(
                Exception("Tanggal awal dan tanggal akhir tidak boleh kosong")
            )
        }

        val parsedStartDate = reqModel.startDate.parseIndonesiaDateOrNull()
        val parsedEndDate = reqModel.endDate.parseIndonesiaDateOrNull()

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

        if (diffInDaysInclusive > 7) {
            return@withContext ApiCondition.AppFailure(
                Exception("Rentang tanggal maksimal 7 hari")
            )
        }

        return@withContext repository.getRiwayatPage(reqModel)
    }
}