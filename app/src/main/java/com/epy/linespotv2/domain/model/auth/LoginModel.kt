package com.epy.linespotv2.domain.model.auth

import com.epy.linespotv2.data.remote.dto.helper.TarifDto
import com.google.gson.annotations.SerializedName


//data class LoginModel(
//    val token: String,
//
//    val id: Int,
//    val nama: String,
//    val email: String,
//    val role: Long,
//    val avatar_url: String,
//
//    val theme: String,
//    val language: String
//)

data class LoginRequestModel (
    val identity: String,
    val password: String,
)

data class LoginResponseModel (
    val userModel: UserModel,
    val tokenSet: TokenSet,
)