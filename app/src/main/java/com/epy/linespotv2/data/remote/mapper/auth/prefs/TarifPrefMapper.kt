package com.epy.linespotv2.data.remote.mapper.auth.prefs

import com.epy.linespotv2.data.local.prefs.TarifPrefsModel
import com.epy.linespotv2.data.remote.dto.helper.TarifDto

fun TarifDto?.toPrefs(): TarifPrefsModel {
    return TarifPrefsModel(
        kendaraan = this?.kendaraan.orEmpty(),
        nominal = this?.nominal ?: 0L
    )
}

fun List<TarifDto?>?.toPrefs(): List<TarifPrefsModel> {
    return this.orEmpty()
        .filterNotNull()
        .map { it.toPrefs() }
}
