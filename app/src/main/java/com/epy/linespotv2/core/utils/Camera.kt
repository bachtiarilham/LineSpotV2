package com.epy.linespotv2.core.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.LuminanceSource
import com.google.zxing.MultiFormatReader
import com.google.zxing.NotFoundException
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.Result
import com.google.zxing.common.HybridBinarizer
import java.util.EnumMap
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

object Camera {

    @Composable
    fun QrisScannerPreview(
        modifier: Modifier = Modifier,
        isTorchEnabled: Boolean = false,
        onQrDetected: (String) -> Unit,
        onPermissionDenied: (() -> Unit)? = null,
        onCameraUnavailable: ((String) -> Unit)? = null
    ) {
        val context = LocalContext.current
        val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
        val previewView = remember {
            PreviewView(context).apply {
                scaleType = PreviewView.ScaleType.FILL_CENTER
            }
        }
        val analysisExecutor = remember { Executors.newSingleThreadExecutor() }
        var hasPermission by remember {
            mutableStateOf(context.hasCameraPermission())
        }

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { granted ->
            hasPermission = granted
            if (!granted) {
                onPermissionDenied?.invoke()
            }
        }

        LaunchedEffect(Unit) {
            if (!hasPermission) {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }

        DisposableEffect(Unit) {
            onDispose {
                analysisExecutor.shutdown()
            }
        }

        if (!hasPermission) return

        AndroidView(
            modifier = modifier,
            factory = { previewView },
            update = {
                val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
                cameraProviderFuture.addListener(
                    {
                        runCatching {
                            val cameraProvider = cameraProviderFuture.get()
                            val preview = Preview.Builder().build().also { cameraPreview ->
                                cameraPreview.surfaceProvider = previewView.surfaceProvider
                            }

                            val analysis = ImageAnalysis.Builder()
                                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                .build()
                                .also { imageAnalysis ->
                                    imageAnalysis.setAnalyzer(
                                        analysisExecutor,
                                        QrisAnalyzer(onQrDetected = onQrDetected)
                                    )
                                }

                            cameraProvider.unbindAll()

                            val camera = cameraProvider.bindToLifecycle(
                                lifecycleOwner,
                                CameraSelector.DEFAULT_BACK_CAMERA,
                                preview,
                                analysis
                            )

                            camera.cameraControl.enableTorch(isTorchEnabled)
                        }.onFailure { error ->
                            onCameraUnavailable?.invoke(
                                error.message ?: "Gagal membuka kamera"
                            )
                        }
                    },
                    ContextCompat.getMainExecutor(context)
                )
            }
        )

        DisposableEffect(lifecycleOwner) {
            onDispose {
                runCatching {
                    ProcessCameraProvider.getInstance(context).get().unbindAll()
                }
            }
        }
    }
}

private class QrisAnalyzer(
    private val onQrDetected: (String) -> Unit
) : ImageAnalysis.Analyzer {

    private val isProcessing = AtomicBoolean(false)
    private val reader = MultiFormatReader().apply {
        setHints(
            EnumMap<DecodeHintType, Any>(DecodeHintType::class.java).apply {
                put(
                    DecodeHintType.POSSIBLE_FORMATS,
                    listOf(com.google.zxing.BarcodeFormat.QR_CODE)
                )
                put(DecodeHintType.TRY_HARDER, true)
            }
        )
    }

    override fun analyze(image: androidx.camera.core.ImageProxy) {
        if (!isProcessing.compareAndSet(false, true)) {
            image.close()
            return
        }

        try {
            val source = image.toLuminanceSource()
            val result = decode(source) ?: decode(source.rotateCounterClockwise())

            if (!result?.text.isNullOrBlank()) {
                onQrDetected(result!!.text)
            }
        } catch (_: Exception) {
            // Ignore invalid frames and keep scanning.
        } finally {
            isProcessing.set(false)
            image.close()
        }
    }

    private fun decode(source: LuminanceSource?): Result? {
        return try {
            val bitmap = BinaryBitmap(HybridBinarizer(source))
            reader.decodeWithState(bitmap)
        } catch (_: NotFoundException) {
            null
        } finally {
            reader.reset()
        }
    }
}

private fun androidx.camera.core.ImageProxy.toLuminanceSource(): PlanarYUVLuminanceSource {
    val yBuffer = planes.first().buffer
    val ySize = yBuffer.remaining()
    val yBytes = ByteArray(ySize)
    yBuffer.get(yBytes)

    return PlanarYUVLuminanceSource(
        yBytes,
        width,
        height,
        0,
        0,
        width,
        height,
        false
    )
}

private fun Context.hasCameraPermission(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
}
