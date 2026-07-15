package com.epy.linespotv2.presentation.riwayat

import com.epy.linespotv2.domain.model.riwayat.RiwayatResponseModel
import com.epy.linespotv2.presentation.riwayat.ui_model.RiwayatPaymentFilter
import com.epy.linespotv2.presentation.riwayat.ui_model.RiwayatFilterUiModel
import com.epy.linespotv2.presentation.riwayat.ui_model.RiwayatScreenUiModel
import com.epy.linespotv2.presentation.riwayat.ui_model.RiwayatVehicleFilter

data class RiwayatState(
    //riwayatscreen
    val isLoading: Boolean = false,
    val isRefresh: Boolean = false,
    val error: String? = null,
    val riwayatEffect: RiwayatEffect? = null,
    val riwayatResponseModel: RiwayatResponseModel? = null,
    //riwayatfilterscreen
    val isLoadingLokasi: Boolean = false,
    val lokasiList: List<String> = listOf("Semua Area"),
    val selectedLokasi: String = "Semua Area",
    val selectedVehicle: RiwayatVehicleFilter = RiwayatVehicleFilter.ALL,
    val selectedPayment : RiwayatPaymentFilter = RiwayatPaymentFilter.ALL,
    val errorLokasi: String? = null,
    val screenUiModel: RiwayatScreenUiModel? = null,
    val filterUiModel: RiwayatFilterUiModel = RiwayatFilterUiModel()
)
