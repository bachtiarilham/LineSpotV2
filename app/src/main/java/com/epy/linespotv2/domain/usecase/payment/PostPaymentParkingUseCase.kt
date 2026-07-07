package com.epy.linespotv2.domain.usecase.payment

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.utils.Dispatcher
import com.epy.linespotv2.core.utils.parseIndonesiaDateTimeOrNull
import com.epy.linespotv2.domain.model.payment.PostParkingReqModel
import com.epy.linespotv2.domain.model.payment.PostParkingRespModel
import com.epy.linespotv2.domain.model.payment.PostPaymentParkingReqModel
import com.epy.linespotv2.domain.model.payment.PostPaymentParkingRespModel
import com.epy.linespotv2.domain.repository.payment.PostPaymentParkingRepository
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

class PostPaymentParkingUseCase @Inject constructor(
    private val repository: PostPaymentParkingRepository,
    private val dispatcher: Dispatcher
) {
    suspend operator fun invoke(reqModel: PostPaymentParkingReqModel): ApiCondition<PostPaymentParkingRespModel> = withContext(dispatcher.io) {
        return@withContext repository.postPaymentParking(reqModel)
    }
}