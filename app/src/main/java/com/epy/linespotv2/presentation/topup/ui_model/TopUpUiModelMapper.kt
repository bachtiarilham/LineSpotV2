package com.epy.linespotv2.presentation.topup.ui_model

import com.epy.linespotv2.core.utils.toRupiah
import com.epy.linespotv2.domain.model.helper.MetodeItemModel
import com.epy.linespotv2.domain.model.helper.NominalItemModel
import com.epy.linespotv2.domain.model.helper.TopupModel

fun TopupModel?.toUiModel(
    selectedNominalAmount: Long?,
    selectedPaymentMethodCode: String?,
    customNominalInput: String
): TopUpUiModel {
    val selectedAmount = selectedNominalAmount ?: customNominalInput.toLongOrNull()
    val paymentMethods = this?.metodePayment.orEmpty().map { method ->
        method.toUiModel(selectedPaymentMethodCode)
    }
    val normalizedSelectedPaymentCode = selectedPaymentMethodCode
        ?: paymentMethods.firstOrNull()?.code

    return TopUpUiModel(
        nominalOptions = this?.nominal.orEmpty().map { option ->
            option.toUiModel(selectedAmount)
        },
        paymentMethods = paymentMethods,
        selectedNominalAmount = selectedNominalAmount,
        selectedPaymentMethodCode = normalizedSelectedPaymentCode,
        customNominalInput = customNominalInput,
        customNominalAmount = customNominalInput.toLongOrNull(),
        isSubmitEnabled = selectedAmount != null && selectedAmount > 0L && !normalizedSelectedPaymentCode.isNullOrBlank()
    )
}

private fun NominalItemModel.toUiModel(selectedAmount: Long?): TopUpNominalUiModel {
    val amountValue = nominalAmount ?: 0L
    return TopUpNominalUiModel(
        optionId = optionId ?: 0L,
        amount = amountValue,
        label = label.orEmpty().ifBlank { amountValue.toRupiah() },
        isSelected = selectedAmount == amountValue
    )
}

private fun MetodeItemModel.toUiModel(selectedPaymentMethodCode: String?): TopUpPaymentMethodUiModel {
    val requestCode = namaPayment.orEmpty()
    val displayName = codePayment.orEmpty()
    return TopUpPaymentMethodUiModel(
        paymentMethodId = paymentMethodId ?: 0L,
        title = displayName.ifBlank { requestCode.ifBlank { "Metode Pembayaran" } },
        subtitle = requestCode.ifBlank { "Pilih metode pembayaran" },
        code = requestCode,
        logoUrl = logoPayment.orEmpty(),
        isSelected = requestCode.equals(selectedPaymentMethodCode, ignoreCase = true)
    )
}
