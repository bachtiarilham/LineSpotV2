package com.epy.linespotv2.presentation.post_qr

sealed interface PostQrEffect {
    object NavigateBack : PostQrEffect
    data class NavigateToHasilBayarParkir(
        val rawQr: String
    ) : PostQrEffect
    data class ShowToast(val message: String) : PostQrEffect
}
