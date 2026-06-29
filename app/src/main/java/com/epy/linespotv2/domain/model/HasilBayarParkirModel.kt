package com.epy.linespotv2.domain.model

data class HasilBayarParkirModel(
    val title: String = "Struk Pembayaran",
    val successTitle: String = "Pembayaran Berhasil!",
    val successDescription: String = "Terima kasih, pembayaran Anda telah diterima.",
    val totalAmount: String = "Rp 8.000",
    val paymentStatus: String = "Lunas",
    val referenceNumber: String = "2024052314451234",
    val verificationMessage: String = "Transaksi aman terverifikasi",
    val thankYouTitle: String = "Terima kasih!",
    val thankYouDescription: String = "Mari dukung parkir tertib untuk kota yang lebih nyaman.",
    val downloadLabel: String = "Unduh Struk",
    val backToHomeLabel: String = "Kembali ke Beranda",
    val details: List<HasilBayarParkirDetailItem> = listOf(
        HasilBayarParkirDetailItem("Metode Pembayaran", "QRIS"),
        HasilBayarParkirDetailItem("Waktu Pembayaran", "23 Mei 2024, 14:45"),
        HasilBayarParkirDetailItem("ID Transaksi", "TRX24052314451234"),
        HasilBayarParkirDetailItem("ID Merchant", "LINE SPOT PARKIR"),
        HasilBayarParkirDetailItem("NMID Merchant", "ID1020032154158"),
        HasilBayarParkirDetailItem("Terminal ID", "A01"),
        HasilBayarParkirDetailItem("Reference Number", "2024052314451234")
    )
)

data class HasilBayarParkirDetailItem(
    val label: String = "",
    val value: String = ""
)
