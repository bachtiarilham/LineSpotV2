package com.epy.linespotv2.presentation.riwayat

import com.epy.linespotv2.domain.model.riwayat.RiwayatResponseModel
import com.epy.linespotv2.presentation.riwayat.ui_model.RiwayatScreenUiModel
import com.epy.linespotv2.presentation.riwayat.ui_model.DetilScreenUiModel

enum class RiwayatTab {
    PARKIR,
    KEUANGAN
}

data class RiwayatState(
    // riwayatscreen
    val isLoading: Boolean = false,
    val isRefresh: Boolean = false,
    val error: String? = null,
    val riwayatEffect: RiwayatEffect? = null,
    val riwayatResponseModel: RiwayatResponseModel? = null,
    val screenUiModel: RiwayatScreenUiModel? = null,
    
    // tab active
    val selectedTab: RiwayatTab = RiwayatTab.PARKIR,
    
    // detail selected
    val selectedDetail: DetilScreenUiModel? = null,
    
    // riwayatfilterscreen (Hanya menyisakan rentang tanggal)
    val filterStartDate: String = "",
    val filterEndDate: String = ""
)
