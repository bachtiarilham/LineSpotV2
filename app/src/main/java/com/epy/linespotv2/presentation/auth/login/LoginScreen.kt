package com.epy.linespotv2.presentation.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun LoginScreen (
    onNavigateToCustomerHome: () -> Unit,
    onNavigateToJukirHome: () -> Unit,
    onNavigateToRegister: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    LoginScreenContent(
        state = state,
        onIntent = {viewModel.onIntent(it)},
        consumeEffect = {viewModel.consumeEffect()},
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
    // Konsumsi effect navigasi sekali saja
    LaunchedEffect(state.loginEffect) {
        when (state.loginEffect) {
            is LoginEffect.NavigateToRegister -> {
                onNavigateToRegister()
                consumeEffect()
            }
            is LoginEffect.NavigateToCustomerHome -> {
                onNavigateToCustomerHome() // Arahkan ke Home Customer
                consumeEffect()
            }
            is LoginEffect.NavigateToJukirHome -> {
                onNavigateToJukirHome()    // Arahkan ke Home Jukir Parkir
                consumeEffect()
            }
            is LoginEffect.ShowToast -> {
                // Tampilkan toast / snackbar
                consumeEffect()
            }
            null -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Masuk", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(32.dp))

        OutlinedTextField(
            value = state.username,
            onValueChange = { onIntent(LoginIntent.onUsernameChanged(it)) },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = state.password,
            onValueChange = { onIntent(LoginIntent.onPasswordChanged(it)) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        if (!state.error.isNullOrBlank()) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = state.error!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { onIntent(LoginIntent.clickLogin) },
            enabled = !state.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Masuk")
            }
        }
        Spacer(Modifier.height(12.dp))

        TextButton(onClick = { onIntent(LoginIntent.clickRegister) }) {
            Text("Belum punya akun? Daftar")
        }
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun LoginScreenPreviewNormal() {
    MaterialTheme {
        // Tampilan Kondisi Normal / Kosong
        LoginScreenContent(
            state = LoginState(username = "", password = "", isLoading = false, error = null),
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
        // Kamu bisa ngetes simulasi kalau Jukir salah masukin password
        LoginScreenContent(
            state = LoginState(
                username = "jukir@gmail.com",
                password = "123",
                isLoading = false,
                error = "Password yang kamu masukkan salah!" // <--- Muncul di preview otomatis!
            ),
            onIntent = {},
            consumeEffect = {},
            onNavigateToCustomerHome = {},
            onNavigateToJukirHome = {},
            onNavigateToRegister = {}
        )
    }
}