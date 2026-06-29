package com.epy.linespotv2.presentation.riwayat

import com.epy.linespotv2.domain.model.RiwayatModel

data class RiwayatState(
    //riwayatscreen
    val isLoading: Boolean = false,
    val isRefresh: Boolean = false,
    val error: String? = null,
    val riwayatEffect: RiwayatEffect? = null,
    val riwayatModel: RiwayatModel? = null,
    //riwayatfilterscreen
    val isLoadingLokasi: Boolean = false,
    val lokasiList: List<String> = listOf("Semua Area"),
    val selectedLokasi: String = "Semua Area",
    val errorLokasi: String? = null,
)

