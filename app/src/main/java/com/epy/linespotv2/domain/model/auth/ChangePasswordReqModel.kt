package com.epy.linespotv2.domain.model.auth

data class ChangePasswordReqModel (
    val oldPassword : String,
    val newPassword : String
)