package com.epy.linespotv2.data.local.prefs

data class UserPrefsModel (
    val zona : String,
    val lokasi : String,
    val tarif : List<TarifPrefsModel>
)

