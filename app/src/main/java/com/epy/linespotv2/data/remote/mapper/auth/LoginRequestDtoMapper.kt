package com.epy.linespotv2.data.remote.mapper.auth

import com.epy.linespotv2.data.remote.dto.auth.LoginRequestDto
import com.epy.linespotv2.domain.model.auth.LoginReqModel

fun LoginReqModel.toDto(): LoginRequestDto = LoginRequestDto(
    identity = identity,
    password = password
)
