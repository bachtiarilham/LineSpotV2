package com.epy.linespotv2.core.utils

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val API_DATE_PATTERN = "yyyy-MM-dd"
private val INDONESIA_LOCALE: Locale = Locale.forLanguageTag("id-ID")

fun Long.toRupiah(): String {
    val formatter = NumberFormat.getNumberInstance(INDONESIA_LOCALE)
    return "Rp ${formatter.format(this)}"
}

fun Long.toCountdownText(): String {
    val minutes = this / 60
    val seconds = this % 60
    return String.format(INDONESIA_LOCALE,"%02d:%02d", minutes, seconds)
}

fun Date.toApiDate(): String {
    val formatter = SimpleDateFormat(API_DATE_PATTERN, Locale.US)
    return formatter.format(this)
}

fun String.parseApiDateOrNull(): Date? {
    return runCatching {
        SimpleDateFormat(API_DATE_PATTERN, Locale.US).apply {
            isLenient = false
        }.parse(this)
    }.getOrNull()
}

fun String.toApiDateOrSelf(): String {
    return parseApiDateOrNull()?.toApiDate() ?: this
}

fun String?.normalizeBackendDateToApiDate(): String {
    val value = this.orEmpty().trim()
    if (value.isBlank()) return ""

    val candidate = value.substringBefore("T").trim()
    return candidate.takeIf { it.parseApiDateOrNull() != null } ?: value.take(10)
}
