package com.epy.linespotv2.domain.usecase

import com.epy.linespotv2.core.network.ApiCondition
import com.epy.linespotv2.core.utils.Dispatcher
import com.epy.linespotv2.domain.model.HasilBayarParkirModel
import com.epy.linespotv2.domain.repository.PostQrRepository
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

class PostQrUseCase @Inject constructor(
    private val repository: PostQrRepository,
    private val dispatcher: Dispatcher
) {
    suspend operator fun invoke(
        rawQr: String
    ): ApiCondition<HasilBayarParkirModel> =
        withContext(dispatcher.io) {
            if (rawQr.isBlank()) {
                return@withContext ApiCondition.AppFailure(
                    IllegalArgumentException("QRIS tidak boleh kosong")
                )
            }

            val payload = runCatching { JSONObject(rawQr) }
                .getOrElse {
                    return@withContext ApiCondition.AppFailure(
                        IllegalArgumentException("Format QRIS tidak valid")
                    )
                }

            val sessionId = payload.optLong("session_id", 0L)
            val platNomor = payload.optString("plat_nomor").trim()
            val lokasi = payload.optString("lokasi").trim()
            val waktuMasuk = payload.optString("waktu_masuk").trim()
            val durasi = payload.optString("durasi").trim()
            val nominal = payload.optLong("nominal", 0L)
            val isPaid = payload.optBoolean("sudah_bayar", false)
            val paymentStatus = payload.optLong("payment_status", 0L)
            val isExpired = payload.optBoolean("is_expired", false)
            val statusMessage = payload.optString("status_message").trim()

            if (sessionId <= 0L) {
                return@withContext ApiCondition.AppFailure(
                    IllegalArgumentException("Session QRIS tidak valid")
                )
            }

            if (platNomor.isBlank()) {
                return@withContext ApiCondition.AppFailure(
                    IllegalArgumentException("Plat nomor pada QRIS tidak ditemukan")
                )
            }

            if (lokasi.isBlank()) {
                return@withContext ApiCondition.AppFailure(
                    IllegalArgumentException("Lokasi parkir pada QRIS tidak ditemukan")
                )
            }

            if (waktuMasuk.isBlank()) {
                return@withContext ApiCondition.AppFailure(
                    IllegalArgumentException("Waktu masuk pada QRIS tidak ditemukan")
                )
            }

            if (durasi.isBlank()) {
                return@withContext ApiCondition.AppFailure(
                    IllegalArgumentException("Durasi parkir pada QRIS tidak ditemukan")
                )
            }

            if (nominal < 0L) {
                return@withContext ApiCondition.AppFailure(
                    IllegalArgumentException("Nominal pada QRIS tidak valid")
                )
            }

            repository.submitQr(
                sessionId = sessionId,
                plat_nomor = platNomor,
                lokasi = lokasi,
                waktu_masuk = waktuMasuk,
                durasi = durasi,
                nominal = nominal,
                isPaid = isPaid,
                paymentStatus = paymentStatus,
                isExpired = isExpired,
                statusMessage = statusMessage
            )
        }
}