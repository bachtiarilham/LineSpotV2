package com.epy.linespotv2.data.remote.mapper

import com.epy.linespotv2.data.remote.dto.HasilBayarParkirDetailItemDto
import com.epy.linespotv2.data.remote.dto.HasilBayarParkirDto
import com.epy.linespotv2.domain.model.HasilBayarParkirDetailItem
import com.epy.linespotv2.domain.model.HasilBayarParkirModel

fun HasilBayarParkirDto?.toDomain(): HasilBayarParkirModel {
    return HasilBayarParkirModel(
        title = this?.title.orEmpty().ifBlank { "Struk Pembayaran" },
        successTitle = this?.successTitle.orEmpty().ifBlank { "Pembayaran Berhasil!" },
        successDescription = this?.successDescription.orEmpty()
            .ifBlank { "Terima kasih, pembayaran Anda telah diterima." },
        totalAmount = this?.totalAmount.orEmpty().ifBlank { "Rp 8.000" },
        paymentStatus = this?.paymentStatus.orEmpty().ifBlank { "Lunas" },
        referenceNumber = this?.referenceNumber.orEmpty().ifBlank { "2024052314451234" },
        verificationMessage = this?.verificationMessage.orEmpty()
            .ifBlank { "Transaksi aman terverifikasi" },
        thankYouTitle = this?.thankYouTitle.orEmpty().ifBlank { "Terima kasih!" },
        thankYouDescription = this?.thankYouDescription.orEmpty()
            .ifBlank { "Mari dukung parkir tertib untuk kota yang lebih nyaman." },
        downloadLabel = this?.downloadLabel.orEmpty().ifBlank { "Unduh Struk" },
        backToHomeLabel = this?.backToHomeLabel.orEmpty().ifBlank { "Kembali ke Beranda" },
        details = this?.details.toDetailItems()
    )
}

private fun List<HasilBayarParkirDetailItemDto>?.toDetailItems(): List<HasilBayarParkirDetailItem> {
    return this?.map { it.toDomain() }?.filter {
        it.label.isNotBlank() || it.value.isNotBlank()
    } ?: emptyList()
}

private fun HasilBayarParkirDetailItemDto?.toDomain(): HasilBayarParkirDetailItem {
    return HasilBayarParkirDetailItem(
        label = this?.label.orEmpty(),
        value = this?.value.orEmpty()
    )
}