package com.epy.linespotv2.domain.usecase.payment

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.utils.Dispatcher
import com.epy.linespotv2.core.utils.parseIndonesiaDateTimeOrNull
import com.epy.linespotv2.domain.model.payment.PostParkingReqModel
import com.epy.linespotv2.domain.model.payment.PostParkingRespModel
import com.epy.linespotv2.domain.repository.payment.PostParkingRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostParkingUseCase @Inject constructor(
    private val repository : PostParkingRepository,
    private val dispatcher : Dispatcher
){
    suspend operator fun invoke( reqModel: PostParkingReqModel): ApiCondition<PostParkingRespModel> = withContext(dispatcher.io) {
        if (reqModel.nomorPolisi.isBlank() || reqModel.lokasiParkir.isBlank()) {
            return@withContext ApiCondition.AppFailure(
                Exception("Tanggal awal dan tanggal akhir tidak boleh kosong")
            )
        }
        val parsedDate = reqModel.waktuMasuk.parseIndonesiaDateTimeOrNull()
        if (parsedDate == null) {
            return@withContext ApiCondition.AppFailure(
                Exception("Format tanggal tidak valid")
            )
        }
        return@withContext repository.postParking(reqModel)
    }
}