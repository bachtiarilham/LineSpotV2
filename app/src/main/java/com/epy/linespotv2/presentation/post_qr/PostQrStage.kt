package com.epy.linespotv2.presentation.post_qr

sealed interface PostQrStage {
    object Scanning : PostQrStage         // kamera aktif, menunggu QR
    object LoadingDetail : PostQrStage    // QR terdeteksi, menunggu server
    data class Error(val message : String) : PostQrStage
}