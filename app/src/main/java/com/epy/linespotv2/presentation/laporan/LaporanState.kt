package com.epy.linespotv2.presentation.laporan

import com.epy.linespotv2.domain.model.laporan.LaporanModel

data class LaporanState(
    val isLoading: Boolean = false,
    val isRefresh: Boolean = false,
    val error: String? = null,
    val laporanEffect: LaporanEffect? = null,
    val laporanModel: LaporanModel? = null,
    val isLoadingLokasi: Boolean = false,
    val lokasiList: List<String> = listOf("Semua Area"),
    val selectedLokasi: String = "Semua Area",
    val errorLokasi: String? = null,
)
