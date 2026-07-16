package com.epy.linespotv2.presentation.post_qr

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.epy.linespotv2.core.ui.theme.DarkBlue
import com.epy.linespotv2.core.ui.theme.GreyText
import com.epy.linespotv2.core.ui.theme.PageBg
import com.epy.linespotv2.core.ui.theme.SmartBlue
import com.epy.linespotv2.core.ui.theme.White
import com.epy.linespotv2.core.utils.Camera

private val ScanSurface = Color(0xFFFDFDFD)
private val ScanCardBorder = Color(0xFFE8EEF8)
private val ScanIconBg = Color(0xFFF2F6FF)
private val ScanIconGreenBg = Color(0xFFE8F9EE)
private val ScanIconGreen = Color(0xFF22C55E)
private val ScanHintBlue = Color(0xFF8FB9FF)

@Composable
fun ScanScreen(
    onNavigateBack: () -> Unit = {},
    onNavigateToHasilBayarParkir: () -> Unit = {},
    viewModel: PostQrViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onIntent(PostQrIntent.LoadPage)
    }

    LaunchedEffect(state.postQrEffect) {
        when (state.postQrEffect) {
            PostQrEffect.NavigateBack -> {
                onNavigateBack()
                viewModel.consumeEffect()
            }

            is PostQrEffect.NavigateToHasilBayarParkir -> {
                onNavigateToHasilBayarParkir()
                viewModel.consumeEffect()
            }

            is PostQrEffect.ShowToast -> {
                viewModel.consumeEffect()
            }

            null -> Unit
        }
    }

    ScanScreenContent(
        state = state,
        onBack = { viewModel.onIntent(PostQrIntent.ClickBack) },
        onQrDetected = { viewModel.onIntent(PostQrIntent.QrDetected(it)) },
        onPermissionChanged = { viewModel.onIntent(PostQrIntent.CameraPermissionChanged(it)) },
        onCameraError = { viewModel.onIntent(PostQrIntent.CameraError(it)) },
        onPickFromGallery = {},
    )
}

@Composable
private fun ScanScreenContent(
    state: PostQrState,
    onBack: () -> Unit,
    onQrDetected: (String) -> Unit,
    onPermissionChanged: (Boolean) -> Unit,
    onCameraError: (String) -> Unit,
    onPickFromGallery: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBg)
            .systemBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ScanTopBar(
            title = "Scan QRIS",
            onBack = onBack
        )

        ScanInfoCard()

        ScanCameraSection(
            isTorchEnabled = state.isTorchEnabled,
            canProcessScan = state.canProcessScan,
            onQrDetected = onQrDetected,
            onPermissionChanged = onPermissionChanged,
            onCameraError = onCameraError
        )

        ScanDetectedHint(
            text = when {
                state.isLoading -> "Memproses QRIS..."
                state.error != null -> state.error
                else -> "QRIS akan otomatis terdeteksi"
            }
        )

        GalleryPickerCard(onClick = onPickFromGallery)


        SecurityCard()

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun ScanTopBar(
    title: String,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ChevronLeft,
            contentDescription = null,
            tint = DarkBlue,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(24.dp)
                .clickable { onBack() }
        )
        Text(
            text = title,
            color = DarkBlue,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun ScanInfoCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        border = BorderStroke(1.dp, ScanCardBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(ScanIconBg, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Collections,
                    contentDescription = null,
                    tint = SmartBlue,
                    modifier = Modifier.size(18.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Arahkan kamera ke QRIS",
                    color = DarkBlue,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = "Pastikan QR code berada dalam area kotak dan terlihat jelas.",
                    color = GreyText,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun ScanCameraSection(
    isTorchEnabled: Boolean,
    canProcessScan: Boolean,
    onQrDetected: (String) -> Unit,
    onPermissionChanged: (Boolean) -> Unit,
    onCameraError: (String) -> Unit
) {
    val isPreview = LocalInspectionMode.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(380.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(ScanSurface, RoundedCornerShape(24.dp))
            .border(1.dp, ScanCardBorder, RoundedCornerShape(24.dp))
    ) {
        if (isPreview) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color(0xFFF8FAFF), RoundedCornerShape(24.dp))
            )
        } else {
            Camera.QrisScannerPreview(
                modifier = Modifier.matchParentSize(),
                isTorchEnabled = isTorchEnabled,
                onQrDetected = { if (canProcessScan) onQrDetected(it) },
                onPermissionDenied = { onPermissionChanged(false) },
                onCameraUnavailable = onCameraError
            )

            LaunchedEffect(Unit) {
                onPermissionChanged(true)
            }
        }

        ScanFrame(
            modifier = Modifier
                .matchParentSize()
                .padding(14.dp)
        )

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(64.dp),
            contentAlignment = Alignment.Center
        ) {
            CenterQrMarker()
        }
    }
}

@Composable
private fun ScanFrame(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        ScanCorner(modifier = Modifier.align(Alignment.TopStart), top = true, start = true)
        ScanCorner(modifier = Modifier.align(Alignment.TopEnd), top = true, start = false)
        ScanCorner(modifier = Modifier.align(Alignment.BottomStart), top = false, start = true)
        ScanCorner(modifier = Modifier.align(Alignment.BottomEnd), top = false, start = false)

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(2.dp)
                .background(SmartBlue)
        )

        SmallFocusFrame(
            modifier = Modifier.align(Alignment.Center),
            size = 56.dp
        )
    }
}

@Composable
private fun ScanCorner(
    modifier: Modifier,
    top: Boolean,
    start: Boolean
) {
    CornerShape(
        modifier = modifier.size(24.dp),
        top = top,
        start = start
    )
}

@Composable
private fun CornerShape(
    modifier: Modifier,
    top: Boolean,
    start: Boolean
) {
    val shape: Shape = when {
        top && start -> RoundedCornerShape(topStart = 12.dp)
        top -> RoundedCornerShape(topEnd = 12.dp)
        start -> RoundedCornerShape(bottomStart = 12.dp)
        else -> RoundedCornerShape(bottomEnd = 12.dp)
    }

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .align(if (top) Alignment.TopStart else Alignment.BottomStart)
                .width(22.dp)
                .height(3.dp)
                .background(SmartBlue, shape)
        )
        Box(
            modifier = Modifier
                .align(if (start) Alignment.TopStart else Alignment.TopEnd)
                .width(3.dp)
                .height(22.dp)
                .background(SmartBlue, shape)
        )
    }
}

@Composable
private fun SmallFocusFrame(
    modifier: Modifier = Modifier,
    size: Dp
) {
    Box(modifier = modifier.size(size)) {
        FocusCorner(Modifier.align(Alignment.TopStart), top = true, start = true)
        FocusCorner(Modifier.align(Alignment.TopEnd), top = true, start = false)
        FocusCorner(Modifier.align(Alignment.BottomStart), top = false, start = true)
        FocusCorner(Modifier.align(Alignment.BottomEnd), top = false, start = false)
    }
}

@Composable
private fun FocusCorner(
    modifier: Modifier,
    top: Boolean,
    start: Boolean
) {
    Box(modifier = modifier.size(14.dp)) {
        Box(
            modifier = Modifier
                .align(if (top) Alignment.TopCenter else Alignment.BottomCenter)
                .width(14.dp)
                .height(2.dp)
                .background(ScanHintBlue, RoundedCornerShape(8.dp))
        )
        Box(
            modifier = Modifier
                .align(if (start) Alignment.CenterStart else Alignment.CenterEnd)
                .width(2.dp)
                .height(14.dp)
                .background(ScanHintBlue, RoundedCornerShape(8.dp))
        )
    }
}

@Composable
private fun CenterQrMarker() {
    Icon(
        imageVector = Icons.Default.Collections,
        contentDescription = null,
        tint = ScanHintBlue,
        modifier = Modifier.size(24.dp)
    )
}

@Composable
private fun ScanDetectedHint(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            tint = SmartBlue,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            color = GreyText,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun GalleryPickerCard(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        border = BorderStroke(1.dp, ScanCardBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(ScanIconBg, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Collections,
                    contentDescription = null,
                    tint = SmartBlue,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "Pilih dari Galeri",
                    color = DarkBlue,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = "Unggah QRIS dari galeri perangkat",
                    color = GreyText,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = GreyText,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun SecurityCard() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(ScanIconGreenBg, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Security,
                contentDescription = null,
                tint = ScanIconGreen,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Transaksi Anda aman",
                color = DarkBlue,
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = "QRIS menggunakan standar keamanan Bank Indonesia",
                color = GreyText,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ScanScreenPreview() {
    MaterialTheme {
        ScanScreenContent(
            state = PostQrState(),
            onBack = {},
            onQrDetected = {},
            onPermissionChanged = {},
            onCameraError = {},
            onPickFromGallery = {}
        )
    }
}
