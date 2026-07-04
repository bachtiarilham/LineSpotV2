package com.epy.linespotv2.data.remote.mapper

import com.epy.linespotv2.data.remote.dto.payment.PostParkingResponseDto
import com.epy.linespotv2.data.remote.dto.payment.PembayaranOptionDto
import com.epy.linespotv2.data.remote.dto.payment.PembayaranQrisSectionDto
import com.epy.linespotv2.data.remote.dto.payment.PembayaranStatusCardDto
import com.epy.linespotv2.data.remote.dto.payment.IsiQrDto
import com.epy.linespotv2.domain.model.IsiQr
import com.epy.linespotv2.domain.model.PembayaranModel
import com.epy.linespotv2.domain.model.PembayaranOption
import com.epy.linespotv2.domain.model.PembayaranOptionType
import com.epy.linespotv2.domain.model.PembayaranQrisSection
import com.epy.linespotv2.domain.model.PembayaranStatusCard

fun PostParkingResponseDto?.toDomain(): PembayaranModel {
    return PembayaranModel(
        title = this?.title ?: "Pembayaran",
        statusCard = this?.statusCard.toDomain(),
        totalPembayaran = this?.totalPembayaran ?: 0L,
        detailLabel = this?.detailLabel ?: "Lihat Detail",
        qrisSection = this?.qrisSection.toDomain(),
        paymentOptionsTitle = this?.paymentOptionsTitle ?: "Pilih Opsi Pembayaran Lain",
        paymentOptions = this?.paymentOptions.toDomain(),
        printButtonLabel = this?.printButtonLabel ?: "Cetak Struk"
    )
}

private fun PembayaranStatusCardDto?.toDomain(): PembayaranStatusCard {
    return PembayaranStatusCard(
        title = this?.title.orEmpty(),
        message = this?.message.orEmpty(),
        isSuccess = this?.isSuccess ?: true
    )
}

private fun PembayaranQrisSectionDto?.toDomain(): PembayaranQrisSection {
    return PembayaranQrisSection(
        title = this?.title ?: "Scan & Bayar dengan QRIS",
        qrContent = this?.qrContent.toDomain(),
        masaBerlakuQr = this?.information.orEmpty(),
        countdownSeconds = this?.countdown ?: 0L,
        alternativeLabel = this?.alternativeLabel ?: "atau"
    )
}

private fun IsiQrDto?.toDomain(): IsiQr {
    return IsiQr(
        sessionId = this?.sessionId ?: 0L,
        plat_nomor = this?.plat_nomor.orEmpty(),
        lokasi = this?.lokasi.orEmpty(),
        waktu_masuk = this?.waktu_masuk.orEmpty(),
        durasi = this?.durasi.orEmpty(),
        nominal = this?.nominal ?: 0L,
        isPaid = this?.isPaid ?: false,
        paymentStatus = this?.paymentStatus ?: 0L,
        isExpired = this?.isExpired ?: false,
        statusMessage = this?.statusMessage ?: ""
    )
}

private fun List<PembayaranOptionDto>?.toDomain(): List<PembayaranOption> {
    return this.orEmpty().map { it.toDomain() }
}

private fun PembayaranOptionDto.toDomain(): PembayaranOption {
    return PembayaranOption(
        type = type.toPembayaranOptionType(),
        title = title.orEmpty(),
        subtitle = subtitle.orEmpty()
    )
}

private fun String?.toPembayaranOptionType(): PembayaranOptionType {
    return when (this?.trim()?.uppercase()) {
        "TUNAI", "CASH" -> PembayaranOptionType.TUNAI
        "E_WALLET", "EWALLET", "WALLET" -> PembayaranOptionType.E_WALLET
        "TRANSFER_BANK", "BANK_TRANSFER" -> PembayaranOptionType.TRANSFER_BANK
        "METODE_LAIN", "LAINNYA", "OTHER" -> PembayaranOptionType.METODE_LAIN
        else -> PembayaranOptionType.METODE_LAIN
    }
}
