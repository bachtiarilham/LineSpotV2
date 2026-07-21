package com.epy.linespotv2.domain.usecase.riwayat

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.riwayat.DetilParkirResponseModel
import com.epy.linespotv2.domain.repository.riwayat.RiwayatRepository
import javax.inject.Inject

class DetilParkirUseCase @Inject constructor(
    private val repository : RiwayatRepository,
){
    suspend operator fun invoke(transactionCode : String): ApiCondition<DetilParkirResponseModel> = repository.getParkirDetil(transactionCode)
}