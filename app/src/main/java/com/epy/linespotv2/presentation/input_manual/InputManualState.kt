package com.epy.linespotv2.presentation.input_manual

import com.epy.linespotv2.domain.model.payment.InputManualModel
import com.epy.linespotv2.domain.model.payment.InputManualTarifSummary
import com.epy.linespotv2.domain.model.payment.InputManualVehicleOption
import com.epy.linespotv2.domain.model.payment.InputManualVehicleType
import com.epy.linespotv2.domain.model.payment.PostParkingRespModel

data class InputManualState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val inputManualEffect: InputManualEffect? = null,
    val inputManualModel: InputManualModel = defaultInputManualModel(),
    val postParkingRespModel: PostParkingRespModel? = null,
    val isCheckingPaymentStatus: Boolean = false
)

fun InputManualState.withSelectedVehicleTarif(
    totalTarif: Long,
    selectedVehicle: InputManualVehicleType = inputManualModel.selectedVehicle
): InputManualState {
    return copy(
        inputManualModel = inputManualModel.copy(
            selectedVehicle = selectedVehicle,
            tarifSummary = inputManualModel.tarifSummary.copy(
                totalTarif = totalTarif
            )
        )
    )
}

private fun defaultInputManualModel(): InputManualModel {
    return InputManualModel(
        selectedVehicle = InputManualVehicleType.MOTOR,
        tarifSummary = InputManualTarifSummary()
    )
}
