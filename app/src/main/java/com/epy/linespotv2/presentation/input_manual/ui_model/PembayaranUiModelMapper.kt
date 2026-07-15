package com.epy.linespotv2.presentation.input_manual.ui_model

import com.epy.linespotv2.core.utils.toRupiah
import com.epy.linespotv2.domain.model.parking.PostParkingRespModel

fun PostParkingRespModel.toUiModel(): PembayaranUiModel {
    val paymentName = paymentStatusName.orEmpty()
    val isSuccess = paymentStatusCode.equals("PAID", ignoreCase = true) ||
        paymentStatusCode.equals("SUCCESS", ignoreCase = true)

    return PembayaranUiModel(
        title = "Pembayaran",
        statusTitle = if (isSuccess) "Pembayaran berhasil" else "Menunggu pembayaran",
        statusMessage = buildStatusMessage(),
        isSuccess = isSuccess,
        totalPembayaranLabel = (amount ?: 0L).toRupiah(),
        detailLabel = "Lihat Detail",
        qrPayload = qrString.orEmpty(),
        qrExpiredLabel = qrExpiredAt.orEmpty().ifBlank { "QRIS berlaku sementara" },
        paymentOptions = buildPaymentOptions(paymentName)
    )
}

private fun PostParkingRespModel.buildStatusMessage(): String {
    return paymentStatusName.orEmpty().ifBlank {
        "Silakan tunjukkan QRIS ini kepada pengguna untuk melakukan pembayaran."
    }
}

private fun PostParkingRespModel.buildPaymentOptions(
    paymentName: String
): List<PembayaranOptionUiModel> {
    return listOf(
        PembayaranOptionUiModel(
            title = "Tunai",
            subtitle = "Terima pembayaran secara tunai"
        ),
        PembayaranOptionUiModel(
            title = "Non Tunai",
            subtitle = paymentName.ifBlank { "Pembayaran dengan QRIS atau kanal digital" }
        ),
        PembayaranOptionUiModel(
            title = "Transfer Bank",
            subtitle = "Gunakan transfer bank bila tersedia"
        )
    )
}
