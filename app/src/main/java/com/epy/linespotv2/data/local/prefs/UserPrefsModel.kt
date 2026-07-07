package com.epy.linespotv2.data.local.prefs

data class UserPrefsModel (
    val token : String,
    val refreshToken : String,
    val userId: Long,
    val username : String,
    val fullName : String,
    val email : String,
    val phone : String,
    val nik : String,
    val roleId : Long,
    val zona : String,
    val lokasi : String,
    val tarif : List<TarifPrefsModel>
)

