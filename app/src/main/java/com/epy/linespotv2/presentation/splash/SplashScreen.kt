package com.epy.linespotv2.presentation.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    onNavigateToLogin : () -> Unit = {},
    onNavigateToCustomerHome: () -> Unit, // <-- Tambahkan callback Customer
    onNavigateToJukirHome: () -> Unit,
    ) {

    val uiEvent by viewModel.uiEvent.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
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



    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .alpha(alpha = 0.5f),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Icon(
                modifier = Modifier
                    .size(80.dp),
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = "Logo",
                tint = Color.Red
            )
            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text ="LineSpot V2",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 4.sp
            )

        }
    }
}
