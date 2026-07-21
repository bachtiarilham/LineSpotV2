package com.epy.linespotv2.presentation.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.epy.linespotv2.R
import com.epy.linespotv2.core.ui.theme.Error
import com.epy.linespotv2.core.ui.theme.LoginBgBottom
import com.epy.linespotv2.core.ui.theme.LoginBgTop
import com.epy.linespotv2.core.ui.theme.LoginFieldBg
import com.epy.linespotv2.core.ui.theme.LoginFieldBorder
import com.epy.linespotv2.core.ui.theme.LoginHint
import com.epy.linespotv2.core.ui.theme.SmartBlue
import com.epy.linespotv2.core.ui.theme.InterFontFamily

@Composable
fun LoginScreen(
    onNavigateToCustomerHome: () -> Unit,
    onNavigateToJukirHome: () -> Unit,
    onNavigateToRegister: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LoginScreenContent(
        state = state,
        onIntent = { viewModel.onIntent(it) },
        consumeEffect = { viewModel.consumeEffect() },
        onNavigateToCustomerHome = onNavigateToCustomerHome,
        onNavigateToJukirHome = onNavigateToJukirHome,
        onNavigateToRegister = onNavigateToRegister
    )
}

@Composable
fun LoginScreenContent(
    state: LoginState,
    onIntent: (LoginIntent) -> Unit,
    consumeEffect: () -> Unit,
    onNavigateToCustomerHome: () -> Unit,
    onNavigateToJukirHome: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    LaunchedEffect(state.loginEffect) {
        when (state.loginEffect) {
            is LoginEffect.NavigateToRegister -> {
                onNavigateToRegister()
                consumeEffect()
            }
            is LoginEffect.NavigateToCustomerHome -> {
                onNavigateToCustomerHome()
                consumeEffect()
            }
            is LoginEffect.NavigateToJukirHome -> {
                onNavigateToJukirHome()
                consumeEffect()
            }
            is LoginEffect.ShowToast -> {
                consumeEffect()
            }
            null -> Unit
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(LoginBgTop, LoginBgBottom)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ── Logo ──────────────────────────────────────────
            Image(
                painter = painterResource(id = R.drawable.linespot_logo),
                contentDescription = "LineSpot Logo",
                modifier = Modifier
                    .height(72.dp)
                    .padding(bottom = 8.dp)
            )

            Spacer(Modifier.height(12.dp))


            Text(
                text = "LineSpot",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge.copy(color = Color.White)
            )

            Spacer(Modifier.height(48.dp))

            // ── Field Username ────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(LoginFieldBg)
                    .border(1.dp, LoginFieldBorder, RoundedCornerShape(10.dp))
            ) {
                TextField(
                    value = state.identity,
                    onValueChange = { onIntent(LoginIntent.onUsernameChanged(it)) },
                    placeholder = {
                        Text(
                            text = "LineSpotUser",
                            color = LoginHint,
                            style = MaterialTheme.typography.bodyMedium.copy(color = LoginHint)
                        )
                    },
                    label = {
                        Text(
                            text = "Username",
                            color = LoginHint,
                            style = MaterialTheme.typography.bodySmall.copy(color = LoginHint, fontSize = 11.sp)
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = SmartBlue,
                        focusedLabelColor = LoginHint,
                        unfocusedLabelColor = LoginHint,
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(14.dp))

            // ── Field Password ────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(LoginFieldBg)
                    .border(1.dp, LoginFieldBorder, RoundedCornerShape(10.dp))
            ) {
                TextField(
                    value = state.password,
                    onValueChange = { onIntent(LoginIntent.onPasswordChanged(it)) },
                    placeholder = {
                        Text(
                            text = "Password",
                            color = LoginHint,
                            style = MaterialTheme.typography.bodyMedium.copy(color = LoginHint)
                        )
                    },
                    label = {
                        Text(
                            text = "Password",
                            color = LoginHint,
                            style = MaterialTheme.typography.bodySmall.copy(color = LoginHint, fontSize = 11.sp)
                        )
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = SmartBlue,
                        focusedLabelColor = LoginHint,
                        unfocusedLabelColor = LoginHint,
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // ── Lupa kata sandi? ──────────────────────────────
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                TextButton(onClick = { /* TODO: Forgot password */ }) {
                    Text(
                        text = "Lupa kata sandi?",
                        style = MaterialTheme.typography.bodySmall.copy(color = LoginHint, fontSize = 12.sp)
                    )
                }
            }

            // ── Error message ─────────────────────────────────
            if (!state.error.isNullOrBlank()) {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = state.error!!,
                    color = Error,
                    style = MaterialTheme.typography.bodySmall.copy(color = Error),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(20.dp))

            // ── Tombol Masuk ──────────────────────────────────
            Button(
                onClick = { onIntent(LoginIntent.clickLogin) },
                enabled = !state.isLoading,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SmartBlue,
                    disabledContainerColor = SmartBlue.copy(alpha = 0.5f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                } else {
                    Text(
                        text = "Masuk",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // ── Divider "Atau" ────────────────────────────────
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = LoginFieldBorder
                )
                Text(
                    text = "  Atau  ",
                    style = MaterialTheme.typography.bodyMedium.copy(color = LoginHint, fontSize = 13.sp)
                )
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = LoginFieldBorder
                )
            }

            Spacer(Modifier.height(20.dp))

            // ── Link Daftar ───────────────────────────────────
            TextButton(onClick = { onIntent(LoginIntent.clickRegister) }) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                fontFamily = InterFontFamily,
                                fontWeight = FontWeight.Normal,
                                color = LoginHint,
                                fontSize = 14.sp
                            )
                        ) {
                            append("Belum punya akun? ")
                        }
                        withStyle(
                            SpanStyle(
                                fontFamily = InterFontFamily,
                                color = SmartBlue,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        ) {
                            append("Daftar")
                        }
                    },
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun LoginScreenPreviewNormal() {
    MaterialTheme {
        LoginScreenContent(
            state = LoginState(identity = "", password = "", isLoading = false, error = null),
            onIntent = {},
            consumeEffect = {},
            onNavigateToCustomerHome = {},
            onNavigateToJukirHome = {},
            onNavigateToRegister = {}
        )
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun LoginScreenPreviewEror() {
    MaterialTheme {
        LoginScreenContent(
            state = LoginState(
                identity = "LineSpotUser",
                password = "123",
                isLoading = false,
                error = "Password yang kamu masukkan salah!"
            ),
            onIntent = {},
            consumeEffect = {},
            onNavigateToCustomerHome = {},
            onNavigateToJukirHome = {},
            onNavigateToRegister = {}
        )
    }
}