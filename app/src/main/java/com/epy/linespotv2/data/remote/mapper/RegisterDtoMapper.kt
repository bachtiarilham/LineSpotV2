package com.epy.linespotv2.data.remote.mapper

import com.epy.linespotv2.data.remote.dto.auth.RegisterResultDto
import com.epy.linespotv2.domain.model.auth.RegisterModel


fun RegisterResultDto.toDomain(): RegisterModel = RegisterModel(
    userId = user.userId,
    fullName = user.fullName,
    email = user.email,
    username = user.username,
    role = user.role,
    isVerified = user.isVerified
)
