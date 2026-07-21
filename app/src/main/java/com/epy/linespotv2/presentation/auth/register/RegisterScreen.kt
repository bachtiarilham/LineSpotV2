package com.epy.linespotv2.presentation.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.epy.linespotv2.core.ui.theme.Error
import com.epy.linespotv2.core.ui.theme.LoginBgBottom
import com.epy.linespotv2.core.ui.theme.LoginBgTop
import com.epy.linespotv2.core.ui.theme.LoginFieldBg
import com.epy.linespotv2.core.ui.theme.LoginFieldBorder
import com.epy.linespotv2.core.ui.theme.LoginHint
import com.epy.linespotv2.core.ui.theme.SmartBlue
import com.epy.linespotv2.core.ui.theme.InterFontFamily

@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit = {},
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RegisterScreenContent(
        state = state,
        onIntent = viewModel::onIntent,
        consumeEffect = viewModel::consumeEffect,
        onNavigateToLogin = onNavigateToLogin
    )
}

@Composable
fun RegisterScreenContent(
    state: RegisterState,
    onIntent: (RegisterIntent) -> Unit,
    consumeEffect: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.registerEffect) {
        when (val effect = state.registerEffect) {
            is RegisterEffect.ShowMessage -> {
                snackbarHostState.showSnackbar(effect.message)
                consumeEffect()
            }
            is RegisterEffect.NavigateToLogin -> {
                onNavigateToLogin()
                consumeEffect()
            }
            is RegisterEffect.NavigateBack -> {
                onNavigateToLogin()
                consumeEffect()
            }
            null -> Unit
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color.Transparent
    ) { innerPadding ->
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
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 28.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Buat Akun",
                    color = Color.White,
                    style = MaterialTheme.typography.displayLarge.copy(color = Color.White)
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "Daftarkan diri kamu sebagai Jukir",
                    color = LoginHint,
                    style = MaterialTheme.typography.bodyMedium.copy(color = LoginHint),
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(36.dp))

                // ── Field Nama Lengkap ─────────────────────────────
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(LoginFieldBg)
                        .border(1.dp, LoginFieldBorder, RoundedCornerShape(10.dp))
                ) {
                    TextField(
                        value = state.fullName,
                        onValueChange = { onIntent(RegisterIntent.OnFullNameChanged(it)) },
                        placeholder = { Text(text = "Nama Lengkap", color = LoginHint, style = MaterialTheme.typography.bodyMedium.copy(color = LoginHint)) },
                        label = { Text(text = "Nama Lengkap", color = LoginHint, style = MaterialTheme.typography.bodySmall.copy(color = LoginHint, fontSize = 11.sp)) },
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
                            unfocusedLabelColor = LoginHint
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(Modifier.height(12.dp))

                // ── Field NIK ─────────────────────────────────────
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(LoginFieldBg)
                        .border(1.dp, LoginFieldBorder, RoundedCornerShape(10.dp))
                ) {
                    TextField(
                        value = state.nik,
                        onValueChange = { onIntent(RegisterIntent.OnNikChanged(it)) },
                        placeholder = { Text(text = "NIK (16 digit)", color = LoginHint, style = MaterialTheme.typography.bodyMedium.copy(color = LoginHint)) },
                        label = { Text(text = "NIK (16 digit)", color = LoginHint, style = MaterialTheme.typography.bodySmall.copy(color = LoginHint, fontSize = 11.sp)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                            unfocusedLabelColor = LoginHint
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(Modifier.height(12.dp))

                // ── Field Email ───────────────────────────────────
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(LoginFieldBg)
                        .border(1.dp, LoginFieldBorder, RoundedCornerShape(10.dp))
                ) {
                    TextField(
                        value = state.email,
                        onValueChange = { onIntent(RegisterIntent.OnEmailChanged(it)) },
                        placeholder = { Text(text = "Email", color = LoginHint, style = MaterialTheme.typography.bodyMedium.copy(color = LoginHint)) },
                        label = { Text(text = "Email", color = LoginHint, style = MaterialTheme.typography.bodySmall.copy(color = LoginHint, fontSize = 11.sp)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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
                            unfocusedLabelColor = LoginHint
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(Modifier.height(12.dp))

                // ── Field Nomor HP ────────────────────────────────
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(LoginFieldBg)
                        .border(1.dp, LoginFieldBorder, RoundedCornerShape(10.dp))
                ) {
                    TextField(
                        value = state.phone,
                        onValueChange = { onIntent(RegisterIntent.OnPhoneChanged(it)) },
                        placeholder = { Text(text = "Nomor HP", color = LoginHint, style = MaterialTheme.typography.bodyMedium.copy(color = LoginHint)) },
                        label = { Text(text = "Nomor HP", color = LoginHint, style = MaterialTheme.typography.bodySmall.copy(color = LoginHint, fontSize = 11.sp)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
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
                            unfocusedLabelColor = LoginHint
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(Modifier.height(12.dp))

                // ── Field Username ────────────────────────────────
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(LoginFieldBg)
                        .border(1.dp, LoginFieldBorder, RoundedCornerShape(10.dp))
                ) {
                    TextField(
                        value = state.username,
                        onValueChange = { onIntent(RegisterIntent.OnUsernameChanged(it)) },
                        placeholder = { Text(text = "Username", color = LoginHint, style = MaterialTheme.typography.bodyMedium.copy(color = LoginHint)) },
                        label = { Text(text = "Username", color = LoginHint, style = MaterialTheme.typography.bodySmall.copy(color = LoginHint, fontSize = 11.sp)) },
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
                            unfocusedLabelColor = LoginHint
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(Modifier.height(12.dp))

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
                        onValueChange = { onIntent(RegisterIntent.OnPasswordChanged(it)) },
                        placeholder = { Text(text = "Password", color = LoginHint, style = MaterialTheme.typography.bodyMedium.copy(color = LoginHint)) },
                        label = { Text(text = "Password", color = LoginHint, style = MaterialTheme.typography.bodySmall.copy(color = LoginHint, fontSize = 11.sp)) },
                        singleLine = true,
                        visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            IconButton(onClick = { onIntent(RegisterIntent.OnTogglePasswordVisibility) }) {
                                Icon(
                                    imageVector = if (state.isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = null,
                                    tint = LoginHint
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = SmartBlue,
                            focusedLabelColor = LoginHint,
                            unfocusedLabelColor = LoginHint
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(Modifier.height(12.dp))

                // ── Field Konfirmasi Password ──────────────────────
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(LoginFieldBg)
                        .border(1.dp, LoginFieldBorder, RoundedCornerShape(10.dp))
                ) {
                    TextField(
                        value = state.confirmPassword,
                        onValueChange = { onIntent(RegisterIntent.OnConfirmPasswordChanged(it)) },
                        placeholder = { Text(text = "Konfirmasi Password", color = LoginHint, style = MaterialTheme.typography.bodyMedium.copy(color = LoginHint)) },
                        label = { Text(text = "Konfirmasi Password", color = LoginHint, style = MaterialTheme.typography.bodySmall.copy(color = LoginHint, fontSize = 11.sp)) },
                        singleLine = true,
                        visualTransformation = if (state.isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            IconButton(onClick = { onIntent(RegisterIntent.OnToggleConfirmPasswordVisibility) }) {
                                Icon(
                                    imageVector = if (state.isConfirmPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = null,
                                    tint = LoginHint
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = SmartBlue,
                            focusedLabelColor = LoginHint,
                            unfocusedLabelColor = LoginHint
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                if (!state.error.isNullOrBlank()) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = state.error,
                        color = Error,
                        style = MaterialTheme.typography.bodySmall.copy(color = Error),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(Modifier.height(28.dp))

                // ── Tombol Daftar ─────────────────────────────────
                Button(
                    onClick = { onIntent(RegisterIntent.OnRegisterClicked) },
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
                            text = "Daftar",
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))

                // ── Link Masuk ────────────────────────────────────
                TextButton(onClick = { onIntent(RegisterIntent.OnLoginClicked) }) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    fontFamily = InterFontFamily,
                                    color = LoginHint,
                                    fontSize = 14.sp
                                )
                            ) {
                                append("Sudah punya akun? ")
                            }
                            withStyle(
                                SpanStyle(
                                    fontFamily = InterFontFamily,
                                    color = SmartBlue,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            ) {
                                append("Masuk")
                            }
                        },
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun RegisterScreenPreviewNormal() {
    MaterialTheme {
        RegisterScreenContent(
            state = RegisterState(),
            onIntent = {},
            consumeEffect = {},
            onNavigateToLogin = {}
        )
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun RegisterScreenPreviewError() {
    MaterialTheme {
        RegisterScreenContent(
            state = RegisterState(
                fullName = "Budi Santoso",
                nik = "123",
                email = "budi@gmail.com",
                phone = "08123456789",
                username = "budisantoso",
                password = "pass123",
                confirmPassword = "pass456",
                error = "Konfirmasi password tidak cocok"
            ),
            onIntent = {},
            consumeEffect = {},
            onNavigateToLogin = {}
        )
    }
}
