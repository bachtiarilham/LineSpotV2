package com.epy.linespotv2.data.remote.mapper.auth

import com.epy.linespotv2.data.remote.dto.auth.ChangePasswordRequestDto
import com.epy.linespotv2.domain.model.auth.ChangePasswordReqModel

fun ChangePasswordReqModel.toDto() : ChangePasswordRequestDto = ChangePasswordRequestDto(
    oldPassword = oldPassword,
    newPassword = newPassword
)