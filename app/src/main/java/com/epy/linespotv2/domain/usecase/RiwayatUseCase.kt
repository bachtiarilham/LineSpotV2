package com.epy.linespotv2.domain.usecase

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.utils.Dispatcher
import com.epy.linespotv2.core.utils.parseIndonesiaDateOrNull
import com.epy.linespotv2.domain.model.RiwayatModel
import com.epy.linespotv2.domain.model.RiwayatPaymentFilter
import com.epy.linespotv2.domain.model.RiwayatTransactionFilter
import com.epy.linespotv2.domain.model.RiwayatVehicleFilter
import com.epy.linespotv2.domain.repository.RiwayatRepository
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RiwayatUseCase @Inject constructor(
    private val repository : RiwayatRepository,
    private val dispatcher : Dispatcher
){
    suspend operator fun invoke(
        userId: Long,
        username: String,
        roleId: Long,
        startDate : String,
        endDate : String,
        transaction : RiwayatTransactionFilter,
        payment : RiwayatPaymentFilter,
        vehicle : RiwayatVehicleFilter,
        lokasi : String
    ): ApiCondition<RiwayatModel> = withContext(dispatcher.io) {
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

        if (diffInDaysInclusive > 7) {
            return@withContext ApiCondition.AppFailure(
                Exception("Rentang tanggal maksimal 7 hari")
            )
        }

        return@withContext repository.getRiwayatPage(
            userId,
            username,
            roleId,
            startDate,
            endDate,
            transaction,
            payment,
            vehicle,
            lokasi
        )
    }
}
