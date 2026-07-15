package com.epy.linespotv2.domain.usecase.payment

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.utils.Dispatcher
import com.epy.linespotv2.domain.model.parking.PostParkingReqModel
import com.epy.linespotv2.domain.model.parking.PostParkingRespModel
import com.epy.linespotv2.domain.repository.parking.PostParkingRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostParkingUseCase @Inject constructor(
    private val repository : PostParkingRepository,
    private val dispatcher : Dispatcher
){
    suspend operator fun invoke(reqModel: PostParkingReqModel): ApiCondition<PostParkingRespModel> = withContext(dispatcher.io) {
        if (reqModel.plateNumber.isNullOrBlank()) {
            return@withContext ApiCondition.AppFailure(
                IllegalArgumentException("Nomor polisi tidak boleh kosong")
            )
        }

        if (reqModel.vehicleTypeCode.isNullOrBlank()) {
            return@withContext ApiCondition.AppFailure(
                IllegalArgumentException("Jenis kendaraan belum dipilih")
            )
        }

        return@withContext repository.postParking(reqModel)
    }
}
