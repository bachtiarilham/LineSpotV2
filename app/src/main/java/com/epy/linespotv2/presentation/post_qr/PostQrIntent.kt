package com.epy.linespotv2.presentation.post_qr

sealed class PostQrIntent {
    object LoadPage : PostQrIntent()
    object ClickBack : PostQrIntent()
    data class QrDetected(val rawQr: String) : PostQrIntent()
    data class CameraPermissionChanged(val isGranted: Boolean) : PostQrIntent()
    data class CameraError(val message: String) : PostQrIntent()
    object RescanQr : PostQrIntent()
    object DismissError : PostQrIntent()
    object ClickFlash : PostQrIntent()
    object ClickHelp : PostQrIntent()
}
