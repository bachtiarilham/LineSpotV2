package com.epy.linespotv2.presentation.auth.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit = {},
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RegisterScreenContent(
        state            = state,
        onIntent         = viewModel::onIntent,
        consumeEffect    = viewModel::consumeEffect,
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
    LaunchedEffect(state.registerEffect) {
        when (state.registerEffect) {
            is RegisterEffect.NavigateToLogin -> { onNavigateToLogin(); consumeEffect() }
            is RegisterEffect.NavigateBack    -> { onNavigateToLogin(); consumeEffect() }
            null -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Buat Akun", style = MaterialTheme.typography.headlineMedium)
        Text(
            text  = "Daftarkan diri kamu sebagai Jukir",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(32.dp))

        // Nama Lengkap
        OutlinedTextField(
            value       = state.fullName,
            onValueChange = { onIntent(RegisterIntent.OnFullNameChanged(it)) },
            label       = { Text("Nama Lengkap") },
            singleLine  = true,
            modifier    = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        // NIK
        OutlinedTextField(
            value         = state.nik,
            onValueChange = { onIntent(RegisterIntent.OnNikChanged(it)) },
            label         = { Text("NIK (16 digit)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine    = true,
            modifier      = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        // Email
        OutlinedTextField(
            value         = state.email,
            onValueChange = { onIntent(RegisterIntent.OnEmailChanged(it)) },
            label         = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine    = true,
            modifier      = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        // No HP
        OutlinedTextField(
            value         = state.phone,
            onValueChange = { onIntent(RegisterIntent.OnPhoneChanged(it)) },
            label         = { Text("Nomor HP") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine    = true,
            modifier      = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        // Username
        OutlinedTextField(
            value         = state.username,
            onValueChange = { onIntent(RegisterIntent.OnUsernameChanged(it)) },
            label         = { Text("Username") },
            singleLine    = true,
            modifier      = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        // Password
        OutlinedTextField(
            value         = state.password,
            onValueChange = { onIntent(RegisterIntent.OnPasswordChanged(it)) },
            label         = { Text("Password") },
            singleLine    = true,
            visualTransformation = if (state.isPasswordVisible)
                VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon  = {
                IconButton(onClick = { onIntent(RegisterIntent.OnTogglePasswordVisibility) }) {
                    Icon(
                        imageVector = if (state.isPasswordVisible)
                            Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = null
                    )
                }
            },
            modifier      = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        // Konfirmasi Password
        OutlinedTextField(
            value         = state.confirmPassword,
            onValueChange = { onIntent(RegisterIntent.OnConfirmPasswordChanged(it)) },
            label         = { Text("Konfirmasi Password") },
            singleLine    = true,
            visualTransformation = if (state.isConfirmPasswordVisible)
                VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon  = {
                IconButton(onClick = { onIntent(RegisterIntent.OnToggleConfirmPasswordVisibility) }) {
                    Icon(
                        imageVector = if (state.isConfirmPasswordVisible)
                            Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = null
                    )
                }
            },
            modifier      = Modifier.fillMaxWidth()
        )

        // Error
        if (!state.error.isNullOrBlank()) {
            Spacer(Modifier.height(8.dp))
            Text(
                text  = state.error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(24.dp))

        // Tombol Daftar
        Button(
            onClick  = { onIntent(RegisterIntent.OnRegisterClicked) },
            enabled  = !state.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier    = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color       = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Daftar")
            }
        }
        Spacer(Modifier.height(12.dp))

        TextButton(onClick = { onIntent(RegisterIntent.OnLoginClicked) }) {
            Text("Sudah punya akun? Masuk")
        }
    }
}

// ─── Preview ─────────────────────────────────────────────────────────────────

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun RegisterScreenPreviewNormal() {
    MaterialTheme {
        RegisterScreenContent(
            state         = RegisterState(),
            onIntent      = {},
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
                fullName        = "Budi Santoso",
                nik             = "123",
                email           = "budi@gmail.com",
                phone           = "08123456789",
                username        = "budisantoso",
                password        = "pass123",
                confirmPassword = "pass456",
                error           = "Konfirmasi password tidak cocok"
            ),
            onIntent      = {},
            consumeEffect = {},
            onNavigateToLogin = {}
        )
    }
}