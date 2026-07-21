package com.epy.linespotv2.core.ui.theme

import androidx.compose.ui.graphics.Color

// ── Material baseline (tetap untuk kompatibilitas theme) ──────────────────────
val Purple80     = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80       = Color(0xFFEFB8C8)

val Purple40     = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40       = Color(0xFF7D5260)

// ── Warna Utama (Design System LineSpot) ─────────────────────────────────────
val DarkBlue   = Color(0xFF00183D)   // Navy gelap — background utama dark mode
val SmartBlue  = Color(0xFF1565FF)   // Biru primer — CTA, tombol, link aktif
val Tangerine  = Color(0xFFFFBA00)   // Oranye — aksen, notifikasi, badge
val LightGold  = Color(0xFFFFD166)   // Kuning emas — highlight, promo, premium
val Success    = Color(0xFF22C55E)   // Hijau — status sukses, konfirmasi

val Error      = Color(0xFFEF4444)   // Merah — error, alert kritis
val Warning    = Color(0xFFFFB020)   // Kuning oranye — peringatan
val PageBg     = Color(0xFFF5F7FB)   // Abu sangat terang — background halaman
val Card       = Color(0xFFFFFFEE)   // Putih — surface card
val GreyText   = Color(0xFF8A94A6)   // Abu — teks sekunder, placeholder, hint

// ── Warna Turunan / Utility ───────────────────────────────────────────────────
val CardBorder    = Color(0xFFE7EDF7)
val SoftBlue      = Color(0xFFF4F8FF)
val SoftGreen     = Color(0xFFEAFBF0)
val SoftGreenText = Success
val SoftBlueText  = SmartBlue
val SoftDivider   = Color(0xFFE9EEF7)

// ── Login screen spesifik ─────────────────────────────────────────────────────
val LoginBgTop    = DarkBlue                 // #00183D
val LoginBgBottom = Color(0xFF002A6B)        // Sedikit lebih terang dari DarkBlue
val LoginFieldBorder = Color(0xFF1A3A7A)
val LoginFieldBg     = Color(0x1AFFFFFF)    // Putih 10% transparan
val LoginHint        = GreyText

// ── Alias umum ────────────────────────────────────────────────────────────────
val Black = Color.Black
val White = Color.White