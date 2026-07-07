package com.epy.linespotv2.core.utils

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object PasswordHash {
    fun hashPassword(rawPassword: String): String {
        require(!(rawPassword == null || rawPassword.trim { it <= ' ' }
            .isEmpty())) { "Password tidak boleh kosong" }

        try {
            val digest = MessageDigest.getInstance("SHA-256")
            val hashedBytes = digest.digest(rawPassword.toByteArray(StandardCharsets.UTF_8))
            return toHex(hashedBytes)
        } catch (exception: NoSuchAlgorithmException) {
            throw IllegalStateException("Algoritma hashing tidak tersedia", exception)
        }
    }

    fun verifyPassword(rawPassword: String, hashedPassword: String?): Boolean {
        if (hashedPassword == null || hashedPassword.trim { it <= ' ' }.isEmpty()) {
            return false
        }

        return hashPassword(rawPassword).equals(hashedPassword, ignoreCase = true)
    }

    private fun toHex(bytes: ByteArray): String {
        val builder = StringBuilder(bytes.size * 2)
        for (value in bytes) {
            builder.append(String.format("%02x", value))
        }
        return builder.toString()
    }
}