package com.epy.linespotv2.presentation.riwayat

import com.epy.linespotv2.domain.model.helper.LokasiItemModel
import com.epy.linespotv2.domain.model.riwayat.RiwayatResponseModel
import com.epy.linespotv2.presentation.riwayat.ui_model.RiwayatScreenUiModel

data class RiwayatState(
    //riwayatscreen
    val isLoading: Boolean = false,
    val isRefresh: Boolean = false,
    val error: String? = null,
    val riwayatEffect: RiwayatEffect? = null,
    val riwayatResponseModel: RiwayatResponseModel? = null,
    //riwayatfilterscreen
    val isLoadingLokasi: Boolean = false,
    val lokasiList: List<LokasiItemModel> = emptyList(),
    val selectedLokasi: String = "SEMUA",
    val selectedLokasiCode: String? = "SEMUA",
    val selectedVehicleCode: String = "SEMUA",
    val selectedPaymentCode: String = "SEMUA",
    val errorLokasi: String? = null,
    val screenUiModel: RiwayatScreenUiModel? = null
)
