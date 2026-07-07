package com.epy.linespotv2.domain.model.auth

data class UserExistRespModel (
    val emailExists    : Boolean,
    val usernameExists : Boolean,
    val phoneExists    : Boolean,
)