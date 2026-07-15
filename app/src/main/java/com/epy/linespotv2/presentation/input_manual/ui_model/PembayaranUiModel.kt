package com.epy.linespotv2.presentation.input_manual.ui_model

data class PembayaranUiModel(
    val title: String = "Pembayaran",
    val statusTitle: String = "Tiket berhasil dibuat!",
    val statusMessage: String = "Silakan tunjukkan QRIS ini kepada pengguna untuk melakukan pembayaran.",
    val isSuccess: Boolean = true,
    val totalPembayaranLabel: String = "Rp 0",
    val detailLabel: String = "Lihat Detail",
    val qrTitle: String = "Scan & Bayar dengan QRIS",
    val qrPayload: String = "",
    val qrExpiredLabel: String = "QRIS berlaku sementara",
    val paymentOptionsTitle: String = "Pilih Opsi Pembayaran Lain",
    val paymentOptions: List<PembayaranOptionUiModel> = emptyList()
)

data class PembayaranOptionUiModel(
    val title: String,
    val subtitle: String
)
