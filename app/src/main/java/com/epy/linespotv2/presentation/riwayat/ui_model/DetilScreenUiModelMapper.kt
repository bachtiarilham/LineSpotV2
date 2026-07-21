package com.epy.linespotv2.presentation.riwayat.ui_model

import com.epy.linespotv2.domain.model.riwayat.DetilParkirResponseModel
import com.epy.linespotv2.domain.model.riwayat.DetilTransaksiResponseModel

// Mapper dari DetilParkirResponseModel ke DetilRiwayatUiModel (Detail Parkir)
fun DetilParkirResponseModel.toUiModel(): DetilScreenUiModel {
    val infoItems = listOf(
        DetilRiwayatInfoItemUiModel("Nomor Tiket", transactionCode),
        DetilRiwayatInfoItemUiModel("Waktu Masuk", occurredAt),
        DetilRiwayatInfoItemUiModel("Area Parkir", "$locationName - ${zoneName ?: "-"}"),
        DetilRiwayatInfoItemUiModel("Jenis Kendaraan", vehicleTypeName),
        DetilRiwayatInfoItemUiModel("Tarif Dasar", "Rp $baseAmount"),
        DetilRiwayatInfoItemUiModel("Diskon", "Rp $discountAmount"),
        DetilRiwayatInfoItemUiModel("Total Tarif", "Rp $finalAmount", emphasized = true)

    )

    return DetilScreenUiModel(
        ticketCode = transactionCode,
        statusChipLabel = transactionStatus,
        statusLabel = operationType,
        statusDateTime = occurredAt,
        vehicleInitial = vehicleTypeName.take(1),
        vehicleType = vehicleTypeName,
        totalAmountLabel = "Rp $finalAmount",
        isEntry = operationType.equals("Masuk", ignoreCase = true),
        ticketInfoItems = infoItems,
        paymentMethodLabel = paymentMethodName,
        paymentAmountLabel = "Rp $finalAmount",
        noteLabel = "Status Pembayaran",
        noteValue = transactionStatus,
        showDownloadPdfButton = false // Tidak butuh unduh PDF untuk parkir biasa
    )
}

// Mapper dari DetilTransaksiResponseModel ke DetilRiwayatUiModel (Detail Transaksi Keuangan)
fun DetilTransaksiResponseModel.toUiModel(): DetilScreenUiModel {
    val infoItems = listOf(
        DetilRiwayatInfoItemUiModel("ID Transaksi", topUpCode),
        DetilRiwayatInfoItemUiModel("Waktu Transaksi", createdAt),
        DetilRiwayatInfoItemUiModel("Jenis Transaksi", "Top Up Saldo"),
        DetilRiwayatInfoItemUiModel("Nominal", "Rp $amount"),
        DetilRiwayatInfoItemUiModel("Biaya Admin", "Rp $adminFee"),
        DetilRiwayatInfoItemUiModel("Total Bayar", "Rp $totalAmount", emphasized = true)
    )

    return DetilScreenUiModel(
        ticketCode = topUpCode,
        statusChipLabel = transactionStatus,
        statusLabel = "Top Up",
        statusDateTime = createdAt,
        vehicleInitial = "K", // Keuangan
        vehicleType = "Transaksi Keuangan",
        totalAmountLabel = "Rp $totalAmount",
        isEntry = true, // Transaksi masuk
        ticketInfoItems = infoItems,
        paymentMethodLabel = paymentMethodName,
        paymentAmountLabel = "Rp $totalAmount",
        noteLabel = "Status Transaksi",
        noteValue = transactionStatus,
        showDownloadPdfButton = true // Mengaktifkan opsi Unduh Bukti PDF untuk keuangan
    )
}
