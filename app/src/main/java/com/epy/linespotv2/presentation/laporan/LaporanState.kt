package com.epy.linespotv2.presentation.laporan

import com.epy.linespotv2.domain.model.laporan.LaporanResponseModel
import com.epy.linespotv2.presentation.laporan.ui_model.LaporanScreenUiModel

data class LaporanState(
    val isLoading: Boolean = false,
    val isRefresh: Boolean = false,
    val error: String? = null,
    val laporanEffect: LaporanEffect? = null,
    val laporanResponseModel: LaporanResponseModel? = null,
    val screenUiModel: LaporanScreenUiModel? = null,
)
