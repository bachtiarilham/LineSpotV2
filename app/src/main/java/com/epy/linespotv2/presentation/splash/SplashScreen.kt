package com.epy.linespotv2.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.epy.linespotv2.R
import com.epy.linespotv2.core.ui.theme.LineSpotV2Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit = {},
    onNavigateToCustomerHome: () -> Unit, // <-- Tambahkan callback Customer
    onNavigateToJukirHome: () -> Unit,
) {

    val uiEvent by viewModel.uiEvent.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        delay(1200)
        viewModel.checkLoginAndNavigate()
    }

    LaunchedEffect(uiEvent) {
        when (uiEvent) {
            is SplashUiEvent.NavigateToCustomerHome -> {
                onNavigateToCustomerHome()
                viewModel.consumeEvent()
            }
            is SplashUiEvent.NavigateToJukirHome -> {
                onNavigateToJukirHome()
                viewModel.consumeEvent()
            }
            is SplashUiEvent.NavigateToLogin -> {
                onNavigateToLogin()
                viewModel.consumeEvent()
            }
            null -> Unit
        }
    }

    SplashScreenContent()
}

@Composable
private fun SplashScreenContent() {
    val alpha = remember { Animatable(0f) }
    val scale = remember { Animatable(0.82f) }
    var showText by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        launch {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 700)
            )
        }
        launch {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
        delay(350.milliseconds)
        showText = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.graphicsLayer {
                this.alpha = alpha.value
                scaleX = scale.value
                scaleY = scale.value
            }
        ) {
            Image(
                modifier = Modifier
                    .size(112.dp),
                painter = painterResource(id = R.drawable.linespot_logo),
                contentDescription = "Logo",
                contentScale = ContentScale.Fit
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            AnimatedVisibility(
                visible = showText,
                enter = fadeIn(animationSpec = tween(500)) + slideInVertically(
                    animationSpec = tween(500),
                    initialOffsetY = { it / 3 }
                )
            ) {
                Text(
                    text = "LineSpot V2",
                    color = Color(0xFF001834),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SplashScreenPreview() {
    LineSpotV2Theme {
        SplashScreenContent()
    }
}
