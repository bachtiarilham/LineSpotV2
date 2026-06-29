package com.epy.linespotv2.core.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit
import org.json.JSONArray
import org.json.JSONObject

data class TarifItem(
    val kendaraan: String,
    val nominal: Int
)

@Singleton
//@Inject constructor memberi tahu Dagger Hilt agar Hilt yang otomatis membuatkan kelas ini kapan pun dibutuhkan
class AppPreferences @Inject constructor(
    @ApplicationContext context : Context
) {
    private val mainKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "linespotv2_prefs",
        mainKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    companion object {
        // Auth
        private const val KEY_TOKEN         = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_USERNAME      = "user_username"
        private const val KEY_USER_ID       = "user_id"
        private const val KEY_FULL_NAME     = "user_full_name"
        private const val KEY_EMAIL         = "user_email"
        private const val KEY_PHONE         = "user_phone"
        private const val KEY_NIK           = "user_nik"
        private const val KEY_ROLE          = "role_id"
        private const val KEY_ZONA          = "zona"
        private const val KEY_LOKASI        = "lokasi"
        private const val KEY_TARIF         = "tarif"
    }

    // auth
    var token: String
        // ?: adalah operator elvis penyedia nilai cadangan jika data bernilai null
        // defValue adalah default value kalau misalkan key_token kosong
        get() = prefs.getString(KEY_TOKEN, "") ?: ""
        set(value) = prefs.edit { putString(KEY_TOKEN, value) }

    var refreshtoken: String
        get() = prefs.getString(KEY_REFRESH_TOKEN, "") ?: ""
        set(value) = prefs.edit { putString(KEY_REFRESH_TOKEN, value) }

    var username: String
        get() = prefs.getString(KEY_USERNAME,"") ?: ""
        set(value) = prefs.edit { putString(KEY_USERNAME, value) }

    var userId: Long
        get() = prefs.getLong(KEY_USER_ID, 0L)
        set(value) = prefs.edit { putLong(KEY_USER_ID, value) }

    var fullName: String
        get() = prefs.getString(KEY_FULL_NAME, "") ?: ""
        set(value) = prefs.edit { putString(KEY_FULL_NAME, value) }

    var email: String
        get() = prefs.getString(KEY_EMAIL, "") ?: ""
        set(value) = prefs.edit { putString(KEY_EMAIL, value) }

    var phone: String
        get() = prefs.getString(KEY_PHONE, "") ?: ""
        set(value) = prefs.edit { putString(KEY_PHONE, value) }

    var nik: String
        get() = prefs.getString(KEY_NIK, "") ?: ""
        set(value) = prefs.edit { putString(KEY_NIK, value) }

    var roleId: Long
        get() = prefs.getLong(KEY_ROLE, 0L)
        set(value) = prefs.edit { putLong(KEY_ROLE, value) }

    var zona: String
        get() = prefs.getString(KEY_ZONA, "") ?: ""
        set(value) = prefs.edit().putString(KEY_ZONA, value).apply()

    var lokasi: String
        get() = prefs.getString(KEY_LOKASI, "") ?: ""
        set(value) = prefs.edit().putString(KEY_LOKASI, value).apply()

    var tarif: List<TarifItem>
        get() = prefs.getString(KEY_TARIF, null).toTarifList()
        set(value) = prefs.edit { putString(KEY_TARIF, value.toJson()) }

    fun isLoggedIn(): Boolean = token.isNotBlank()
    fun clear()= prefs.edit().clear().apply()

    private fun List<TarifItem>.toJson(): String {
        val jsonArray = JSONArray()

        forEach { item ->
            jsonArray.put(
                JSONObject().apply {
                    put("kendaraan", item.kendaraan)
                    put("nominal", item.nominal)
                }
            )
        }

        return jsonArray.toString()
    }

    private fun String?.toTarifList(): List<TarifItem> {
        if (this.isNullOrBlank()) return emptyList()

        return runCatching {
            val jsonArray = JSONArray(this)
            List(jsonArray.length()) { index ->
                val item = jsonArray.optJSONObject(index) ?: JSONObject()
                TarifItem(
                    kendaraan = item.optString("kendaraan"),
                    nominal = item.optInt("nominal")
                )
            }
        }.getOrDefault(emptyList())
    }
}

