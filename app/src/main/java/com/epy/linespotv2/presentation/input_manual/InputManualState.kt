package com.epy.linespotv2.presentation.input_manual

import com.epy.linespotv2.domain.model.helper.LokasiItemModel
import com.epy.linespotv2.domain.model.parking.PostParkingRespModel
import com.epy.linespotv2.domain.model.helper.TarifModel
import com.epy.linespotv2.domain.model.payment.InputManualModel
import com.epy.linespotv2.presentation.input_manual.ui_model.InputManualUiModel
import com.epy.linespotv2.presentation.input_manual.ui_model.PembayaranUiModel

data class InputManualState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val inputManualEffect: InputManualEffect? = null,
    val inputManualModel: InputManualModel = InputManualModel(),
    val inputManualUiModel: InputManualUiModel = InputManualUiModel(),
    val tarifModel: TarifModel = TarifModel(),
    val lokasiList: List<LokasiItemModel> = emptyList(),
    val isLoadingLokasi: Boolean = false,
    val postParkingRespModel: PostParkingRespModel? = null,
    val pembayaranUiModel: PembayaranUiModel? = null,
    val isCheckingPaymentStatus: Boolean = false
)
