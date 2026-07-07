package com.epy.linespotv2.data.remote.mapper.auth.prefs

import com.epy.linespotv2.data.local.prefs.UserPrefsModel
import com.epy.linespotv2.data.remote.dto.auth.LoginResponseDto
import com.epy.linespotv2.data.remote.dto.auth.TokenSetDto
import com.epy.linespotv2.data.remote.dto.auth.UserDto

fun LoginResponseDto.toPrefs(): UserPrefsModel {
    return user.toPrefs(tokens)
}

fun UserDto.toPrefs(tokens: TokenSetDto): UserPrefsModel {
    return UserPrefsModel(
        token = tokens.accessToken,
        refreshToken = tokens.refreshToken,
        userId = userId,
        username = username,
        fullName = fullName,
        email = email,
        phone = phone,
        nik = nik,
        roleId = role,
        zona = zona,
        lokasi = lokasi,
        tarif = tarif.toPrefs()
    )
}
