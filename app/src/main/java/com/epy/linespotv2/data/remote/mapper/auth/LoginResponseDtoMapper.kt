package com.epy.linespotv2.data.remote.mapper.auth

import com.epy.linespotv2.data.remote.dto.auth.LoginRespDto
import com.epy.linespotv2.domain.model.auth.LoginRespModel

fun LoginRespDto.toDomain(): LoginRespModel = LoginRespModel(
    tokenSetModel = tokenSetDto.toDomain(),
    roleId = roleId
)
