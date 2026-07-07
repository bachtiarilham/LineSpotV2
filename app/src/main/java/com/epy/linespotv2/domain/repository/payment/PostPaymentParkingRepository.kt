package com.epy.linespotv2.domain.repository.payment

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.payment.PostPaymentParkingReqModel
import com.epy.linespotv2.domain.model.payment.PostPaymentParkingRespModel

interface PostPaymentParkingRepository {
    suspend fun postPaymentParking(reqModel: PostPaymentParkingReqModel): ApiCondition<PostPaymentParkingRespModel>
}