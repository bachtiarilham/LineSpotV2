package com.epy.linespotv2.core.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import com.epy.linespotv2.data.local.prefs.UserPrefsModel
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
        private const val KEY_ACCESS_TOKEN  = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val EXPIRES_IN      = "expires_in"
        private const val ROLE_ID           = "role_id"
    }
    var accessToken: String
        // ?: adalah operator elvis penyedia nilai cadangan jika data bernilai null
        // defValue adalah default value kalau misalkan key_token kosong
        get() = prefs.getString(KEY_ACCESS_TOKEN, "") ?: ""
        set(value) = prefs.edit { putString(KEY_ACCESS_TOKEN, value) }

    var refreshToken: String
        get() = prefs.getString(KEY_REFRESH_TOKEN, "") ?: ""
        set(value) = prefs.edit { putString(KEY_REFRESH_TOKEN, value) }
    var expiresIn : Long
        get() = prefs.getLong(EXPIRES_IN,0L) ?: 0L
        set(value) = prefs.edit { putLong(EXPIRES_IN,value) }
    var roleId : Long
        get() = prefs.getLong(ROLE_ID,0L) ?: 0L
        set(value) = prefs.edit { putLong(ROLE_ID,value) }
    fun isLoggedIn(): Boolean = accessToken.isNotBlank()
    fun clear()= prefs.edit { clear() }
}
