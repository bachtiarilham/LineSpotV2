package com.epy.linespotv2.core.utils

import android.util.Patterns

fun String.isValidEmail(): Boolean =
    Patterns.EMAIL_ADDRESS.matcher(this).matches()