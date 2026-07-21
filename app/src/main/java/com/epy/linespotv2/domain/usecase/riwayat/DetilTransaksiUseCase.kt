package com.epy.linespotv2.domain.usecase.riwayat


import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.domain.model.riwayat.DetilTransaksiResponseModel
import com.epy.linespotv2.domain.repository.riwayat.RiwayatRepository
import javax.inject.Inject

class DetilTransaksiUseCase @Inject constructor(
    private val repository : RiwayatRepository,
){
    suspend operator fun invoke(topUpCode : String): ApiCondition<DetilTransaksiResponseModel> = repository.getTransaksiDetil(topUpCode)
}