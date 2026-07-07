package com.epy.linespotv2.domain.model.payment

data class PostPaymentParkingRespModel(
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
    val details: List<PostPaymentParkingDetailItemModel> = listOf(
        PostPaymentParkingDetailItemModel("Metode Pembayaran", "QRIS"),
        PostPaymentParkingDetailItemModel("Waktu Pembayaran", "23 Mei 2024, 14:45"),
        PostPaymentParkingDetailItemModel("ID Transaksi", "TRX24052314451234"),
        PostPaymentParkingDetailItemModel("ID Merchant", "LINE SPOT PARKIR"),
        PostPaymentParkingDetailItemModel("NMID Merchant", "ID1020032154158"),
        PostPaymentParkingDetailItemModel("Terminal ID", "A01"),
        PostPaymentParkingDetailItemModel("Reference Number", "2024052314451234")
    )
)

data class PostPaymentParkingDetailItemModel(
    val label: String = "",
    val value: String = ""
)
