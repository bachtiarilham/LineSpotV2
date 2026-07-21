package com.epy.linespotv2.data.remote.mapper.riwayat

import com.epy.linespotv2.data.remote.dto.riwayat.ParkingItemDto
import com.epy.linespotv2.data.remote.dto.riwayat.ParkingSectionDto
import com.epy.linespotv2.data.remote.dto.riwayat.RiwayatResponseDto
import com.epy.linespotv2.data.remote.dto.riwayat.TopUpItemDto
import com.epy.linespotv2.data.remote.dto.riwayat.TopUpSectionDto
import com.epy.linespotv2.domain.model.riwayat.ParkingItemModel
import com.epy.linespotv2.domain.model.riwayat.ParkingSectionModel
import com.epy.linespotv2.domain.model.riwayat.RiwayatResponseModel
import com.epy.linespotv2.domain.model.riwayat.TopUpItemModel
import com.epy.linespotv2.domain.model.riwayat.TopUpSectionModel

fun RiwayatResponseDto?.toDomain(): RiwayatResponseModel = RiwayatResponseModel(
    parkingSections = this?.parkingSections?.map { it.toDomain() } ?: emptyList(),
    topUpSections = this?.topUpSections?.map { it.toDomain() } ?: emptyList()
)

private fun ParkingSectionDto.toDomain(): ParkingSectionModel = ParkingSectionModel(
    date = date,
    items = items?.map { it.toDomain() }
)

private fun ParkingItemDto.toDomain(): ParkingItemModel = ParkingItemModel(
    code = code,
    plateNumber = plateNumber,
    vehicleType = vehicleType,
    time = time,
    amount = amount,
    isEntry = isEntry
)

private fun TopUpSectionDto.toDomain(): TopUpSectionModel = TopUpSectionModel(
    date = date,
    items = items?.map { it.toDomain() }
)

private fun TopUpItemDto.toDomain(): TopUpItemModel = TopUpItemModel(
    code = code,
    paymentName = paymentName,
    transactionStatus = transactionStatus,
    providerName = providerName,
    time = time,
    amount = amount
)
