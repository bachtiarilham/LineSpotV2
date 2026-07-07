package com.epy.linespotv2.data.remote.mapper.payment

import com.epy.linespotv2.data.remote.dto.payment.PostParkingRequestDto
import com.epy.linespotv2.domain.model.payment.PostParkingReqModel

fun PostParkingReqModel.toDto() : PostParkingRequestDto = PostParkingRequestDto(
    nomorPolisi = nomorPolisi,
    jenisKendaraan = jenisKendaraan,
    waktuMasuk = waktuMasuk,
    zonaParkir = zonaParkir,
    lokasiParkir = lokasiParkir
)