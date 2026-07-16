package com.epy.linespotv2.data.remote.mapper.auth

import com.epy.linespotv2.data.remote.dto.auth.TokenSetDto
import com.epy.linespotv2.domain.model.auth.TokenSetModel

fun TokenSetDto.toDomain(): TokenSetModel = TokenSetModel(
    accessToken = accessToken,
    refreshToken = refreshToken,
    expiresAt = expiresIn
)

