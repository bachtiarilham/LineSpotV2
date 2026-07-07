package com.epy.linespotv2.core.utils

import android.graphics.Bitmap
import android.graphics.Color
import com.epy.linespotv2.domain.model.payment.IsiQr
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import org.json.JSONObject
import androidx.core.graphics.set

object QrCodeMaker {

    fun buildPayload(isiQr: IsiQr): String {
        return JSONObject().apply {
            put("session_id", isiQr.sessionId)
            put("plat_nomor", isiQr.plat_nomor)
            put("lokasi", isiQr.lokasi)
            put("waktu_masuk", isiQr.waktu_masuk)
            put("durasi", isiQr.durasi)
            put("nominal", isiQr.nominal)
            put("sudah_bayar", isiQr.isPaid)
        }.toString()
    }

    fun createBitmap(
        isiQr: IsiQr,
        size: Int = 768
    ): Bitmap {
        return createBitmapFromText(
            content = buildPayload(isiQr),
            size = size
        )
    }

    fun createBitmapFromText(
        content: String,
        size: Int = 768
    ): Bitmap {
        val hints = mapOf(
            EncodeHintType.MARGIN to 1
        )

        val matrix = QRCodeWriter().encode(
            content,
            BarcodeFormat.QR_CODE,
            size,
            size,
            hints
        )

        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        for (x in 0 until size) {
            for (y in 0 until size) {
                bitmap[x, y] = if (matrix[x, y]) Color.BLACK else Color.WHITE
            }
        }
        return bitmap
    }
}
