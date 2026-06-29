package com.epy.linespotv2.presentation.auth.register

sealed interface RegisterEffect {
    object NavigateToLogin : RegisterEffect   // registrasi sukses → ke login
    object NavigateBack    : RegisterEffect   // tap "Sudah punya akun?" → kembali
}