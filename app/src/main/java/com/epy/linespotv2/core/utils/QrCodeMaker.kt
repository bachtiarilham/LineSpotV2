package com.epy.linespotv2.core.utils

import android.graphics.Bitmap
import android.graphics.Color
import com.epy.linespotv2.domain.model.parking.PostParkingRespModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import org.json.JSONObject
import androidx.core.graphics.set

object QrCodeMaker {

    fun buildPayload(Qr: PostParkingRespModel): String {
        return JSONObject().apply {
            put("session_id", Qr.qrString)
        }.toString()
    }

    fun createBitmap(
        Qr: PostParkingRespModel,
        size: Int = 768
    ): Bitmap {
        return createBitmapFromText(
            content = buildPayload(Qr),
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
