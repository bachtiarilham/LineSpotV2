package com.epy.linespotv2.presentation.topup

import com.epy.linespotv2.domain.model.helper.TopupModel
import com.epy.linespotv2.domain.model.topup.TopupCreateResponseModel
import com.epy.linespotv2.domain.model.topup.TopupStatusResponseModel
import com.epy.linespotv2.presentation.topup.ui_model.TopUpUiModel

data class TopUpState(
    val isLoadingPage: Boolean = false,
    val isSubmitting: Boolean = false,
    val isCheckingStatus: Boolean = false,
    val error: String? = null,
    val topupModel: TopupModel? = null,
    val topupCreateResponseModel: TopupCreateResponseModel? = null,
    val topupStatusResponseModel: TopupStatusResponseModel? = null,
    val topUpUiModel: TopUpUiModel = TopUpUiModel(),
    val selectedNominalAmount: Long? = null,
    val selectedPaymentMethodCode: String? = null,
    val customNominalInput: String = "",
    val activeTopupCode: String? = null,
    val topUpEffect: TopUpEffect? = null
)
