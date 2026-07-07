package com.epy.linespotv2.data.remote.mapper.auth

import com.epy.linespotv2.data.remote.dto.auth.UserDto
import com.epy.linespotv2.data.remote.dto.helper.TarifDto
import com.epy.linespotv2.domain.model.auth.UserModel
import com.epy.linespotv2.domain.model.helper.TarifModel

fun UserDto.toDomain(): UserModel = UserModel(
    userId = userId,
    nik = nik,
    fullName = fullName,
    phone = phone,
    email = email,
    username = username,
    avatar_url = avatarUrl,
    role = role,
    isVerified = isVerified,
    lokasi = lokasi,
    zona = zona,
    tarif = tarif.toDomain(),
    registeredAt = registeredAt,
    createdAt = createdAt,
    updatedAt = updatedAt
)

private fun List<TarifDto?>?.toDomain(): List<TarifModel> {
    return this.orEmpty().filterNotNull().map { tarif ->
        TarifModel(
            kendaraan = tarif.kendaraan,
            nominal = tarif.nominal
        )
    }
}
