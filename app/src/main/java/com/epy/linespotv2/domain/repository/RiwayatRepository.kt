package com.epy.linespotv2.domain.repository

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.RiwayatModel
import com.epy.linespotv2.domain.model.RiwayatPaymentFilter
import com.epy.linespotv2.domain.model.RiwayatTransactionFilter
import com.epy.linespotv2.domain.model.RiwayatVehicleFilter

interface RiwayatRepository {
    suspend fun getRiwayatPage(
        userId: Long,
        username: String,
        roleId: Long,
        startDate : String,
        endDate : String,
        transaction : RiwayatTransactionFilter,
        payment : RiwayatPaymentFilter,
        vehicle : RiwayatVehicleFilter,
        lokasi : String
    ): ApiCondition<RiwayatModel>
}
