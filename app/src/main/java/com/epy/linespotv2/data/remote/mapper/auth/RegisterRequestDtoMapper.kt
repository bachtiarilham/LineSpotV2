package com.epy.linespotv2.data.remote.mapper.auth

import com.epy.linespotv2.data.remote.dto.auth.RegisterRequestDto
import com.epy.linespotv2.domain.model.auth.RegisterReqModel

fun RegisterReqModel.toDto(): RegisterRequestDto = RegisterRequestDto(
    fullName = fullName,
    nik = nik,
    email = email,
    phone = phone,
    username = username,
    password = password
)
