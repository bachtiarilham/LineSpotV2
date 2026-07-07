package com.epy.linespotv2.data.remote.mapper.auth

import com.epy.linespotv2.data.remote.dto.auth.RefreshTokenRequestDto
import com.epy.linespotv2.domain.model.auth.RefreshTokenReqModel

fun RefreshTokenReqModel.toDto(): RefreshTokenRequestDto = RefreshTokenRequestDto(
    refreshToken = refreshToken
)
