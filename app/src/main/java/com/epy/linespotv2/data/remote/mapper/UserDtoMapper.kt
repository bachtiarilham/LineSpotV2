// data/remote/mapper/UserDtoMapper.kt
package com.epy.linespotv2.data.remote.mapper

import com.epy.linespotv2.data.remote.dto.UserProfileDto
import com.epy.linespotv2.domain.model.UserModel

fun UserProfileDto.toDomain(): UserModel = UserModel(
    userId     = userId ?: 0L,
    nik        = nik.orEmpty(),
    fullName   = fullName.orEmpty(),
    phone      = phone.orEmpty(),
    email      = email.orEmpty(),
    username   = username.orEmpty(),
    role       = role ?: 0L,
    isVerified = isVerified ?: false
)