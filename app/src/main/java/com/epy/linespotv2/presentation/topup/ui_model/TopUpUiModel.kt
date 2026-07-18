package com.epy.linespotv2.presentation.topup.ui_model

data class TopUpUiModel(
    val title: String = "Top Up Saldo",
    val nominalOptions: List<TopUpNominalUiModel> = emptyList(),
    val paymentMethods: List<TopUpPaymentMethodUiModel> = emptyList(),
    val selectedNominalAmount: Long? = null,
    val selectedPaymentMethodCode: String? = null,
    val customNominalInput: String = "",
    val customNominalAmount: Long? = null,
    val isSubmitEnabled: Boolean = false
)

data class TopUpNominalUiModel(
    val optionId: Long = 0L,
    val amount: Long = 0L,
    val label: String = "",
    val isSelected: Boolean = false
)

data class TopUpPaymentMethodUiModel(
    val paymentMethodId: Long = 0L,
    val title: String = "",
    val subtitle: String = "",
    val code: String = "",
    val logoUrl: String = "",
    val isSelected: Boolean = false
)
