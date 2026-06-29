package com.epy.linespotv2.domain.usecase

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.utils.Dispatcher
import com.epy.linespotv2.core.utils.parseIndonesiaDateTimeOrNull
import com.epy.linespotv2.domain.model.InputManualModel
import com.epy.linespotv2.domain.model.PembayaranModel
import com.epy.linespotv2.domain.repository.InputManualRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InputManualUseCase @Inject constructor(
    private val repository : InputManualRepository,
    private val dispatcher : Dispatcher
){
    suspend operator fun invoke(
        nomorPolisi: String,
        jenisKendaraan: String,
        waktuMasuk: String,
        zonaParkir: String,
        lokasiParkir: String
    ): ApiCondition<PembayaranModel> = withContext(dispatcher.io) {
        if (nomorPolisi.isBlank() || lokasiParkir.isBlank()) {
            return@withContext ApiCondition.AppFailure(
                Exception("Tanggal awal dan tanggal akhir tidak boleh kosong")
            )
        }

        val parsedDate = waktuMasuk.parseIndonesiaDateTimeOrNull()

        if (parsedDate == null) {
            return@withContext ApiCondition.AppFailure(
                Exception("Format tanggal tidak valid")
            )
        }

        return@withContext repository.postParking(
            nomorPolisi,
            jenisKendaraan,
            waktuMasuk,
            zonaParkir,
            lokasiParkir,
        )
    }
}