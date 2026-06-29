package com.epy.linespotv2.domain.model

data class PembayaranModel(
    val title: String = "Pembayaran",
    val statusCard: PembayaranStatusCard = PembayaranStatusCard(),
    val totalPembayaran: Long = 0L,
    val detailLabel: String = "Lihat Detail",
    val qrisSection: PembayaranQrisSection = PembayaranQrisSection(),
    val paymentOptionsTitle: String = "Pilih Opsi Pembayaran Lain",
    val paymentOptions: List<PembayaranOption> = emptyList(),
    val printButtonLabel: String = "Cetak Struk",

)

data class PembayaranStatusCard(
    val title: String = "",
    val message: String = "",
    val isSuccess: Boolean = true
)

data class PembayaranQrisSection(
    val title: String = "Scan & Bayar dengan QRIS",
    val qrContent: IsiQr = IsiQr(),
    val masaBerlakuQr: String = "",
    val countdownSeconds: Long = 0L,
    val alternativeLabel: String = "atau"
)

data class IsiQr (
    val sessionId : Long = 0L,
    val plat_nomor : String = "",
    val lokasi : String = "",
    val waktu_masuk : String = "",
    val durasi : String = "",
    val nominal : Long = 0L,
    val isPaid : Boolean = false,
    val paymentStatus : Long = 0L,
    val isExpired : Boolean = false,
    val statusMessage : String = ""
)

data class PembayaranOption(
    val type: PembayaranOptionType,
    val title: String,
    val subtitle: String
)

enum class PembayaranOptionType {
    TUNAI,
    E_WALLET,
    TRANSFER_BANK,
    METODE_LAIN
}
