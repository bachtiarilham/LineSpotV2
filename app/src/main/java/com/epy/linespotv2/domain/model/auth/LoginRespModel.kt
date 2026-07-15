package com.epy.linespotv2.domain.model.auth

data class LoginRespModel(
    val tokenSetModel : TokenSetModel,
    val roleId : Long,
)
