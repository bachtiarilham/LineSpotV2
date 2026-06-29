package com.epy.linespotv2.presentation.post_qr

data class PostQrState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val postQrEffect: PostQrEffect? = null,
    val stage: PostQrStage = PostQrStage.Scanning,
    val isCameraPermissionGranted: Boolean = false,
    val isTorchEnabled: Boolean = false,
    val canProcessScan: Boolean = true,
    val rawQr: String = "",
)
