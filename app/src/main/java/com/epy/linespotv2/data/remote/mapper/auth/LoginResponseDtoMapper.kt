package com.epy.linespotv2.data.remote.mapper.auth

import com.epy.linespotv2.data.remote.dto.auth.LoginResponseDto
import com.epy.linespotv2.domain.model.auth.LoginRespModel

fun LoginResponseDto.toDomain(): LoginRespModel = LoginRespModel(
    userModel = user.toDomain(),
    tokenSet = tokens.toDomain()
)


