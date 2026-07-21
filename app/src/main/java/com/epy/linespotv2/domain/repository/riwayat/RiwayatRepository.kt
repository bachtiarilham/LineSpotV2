package com.epy.linespotv2.domain.repository.riwayat

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.riwayat.DetilParkirResponseModel
import com.epy.linespotv2.domain.model.riwayat.DetilTransaksiResponseModel
import com.epy.linespotv2.domain.model.riwayat.RiwayatRequestModel
import com.epy.linespotv2.domain.model.riwayat.RiwayatResponseModel

interface RiwayatRepository {
    suspend fun getRiwayatPage(reqModel : RiwayatRequestModel): ApiCondition<RiwayatResponseModel>
    suspend fun getParkirDetil (trxCode : String) : ApiCondition<DetilParkirResponseModel>
    suspend fun getTransaksiDetil (topUpCode : String) : ApiCondition<DetilTransaksiResponseModel>

}