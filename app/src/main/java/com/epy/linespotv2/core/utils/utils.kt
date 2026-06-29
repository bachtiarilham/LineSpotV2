package com.epy.linespotv2.core.utils

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val INDONESIA_DATE_PATTERN = "dd MMMM yyyy"
private const val INDONESIA_DATE_TIME_PATTERN = "dd MMMM yyyy, HH:mm:ss"
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

//date
fun Date.toIndonesiaDate(): String {
    val formatter = SimpleDateFormat(INDONESIA_DATE_PATTERN, INDONESIA_LOCALE)

    return formatter.format(this)
}

fun Date.toIndonesiaDateTime(): String {
    val formatter = SimpleDateFormat(INDONESIA_DATE_TIME_PATTERN, INDONESIA_LOCALE)
    return formatter.format(this)
}

fun String.parseIndonesiaDateOrNull(): Date? {
    return runCatching {
        SimpleDateFormat(INDONESIA_DATE_PATTERN, INDONESIA_LOCALE).apply {
            isLenient = false
        }.parse(this)
    }.getOrNull()
}

fun String.parseIndonesiaDateTimeOrNull(): Date? {
    return runCatching {
        SimpleDateFormat(INDONESIA_DATE_TIME_PATTERN, INDONESIA_LOCALE).apply {
            isLenient = false
        }.parse(this)
    }.getOrNull()
}
