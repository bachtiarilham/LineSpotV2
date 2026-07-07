package com.epy.linespotv2.domain.model.auth

data class SessionModel (
    val userId : Long,
    val refreshToken : String,
    val tokenType   : String,
    val expiresAt : String,
    val updatedAt : String,
    val createdAt : String,
    val isExpired : Boolean,
)