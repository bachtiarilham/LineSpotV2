package com.epy.linespotv2.data.local.entity

import android.hardware.Camera
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.epy.linespotv2.domain.model.HomeEventItem
import com.epy.linespotv2.domain.model.HomeNewsItem

@Entity(tableName = "home_cache")
data class HomeEntity(
    // Cukup 1 baris karena ini cache halaman utama
    // Profile
    @PrimaryKey
    val profileId: Long,
    val profileName: String,
    val photoUrl: String?,

    // SummaryInfo
    val saldo: Long,
    val expiredDate : String,

    // jukirSummaryInfo
    val pendapatan : Long,
    val lokasi : String,
    val area: String,
    val zona: String,

    // List disimpan sebagai JSON via TypeConverter
    val events: List<HomeEventItem>,
    val news: List<HomeNewsItem>,

    // Warnings
    val warningProfile: String?,
    val warningParking: String?,
    val warningFinance: String?,

    // Metadata cache
    val cachedAt: Long = System.currentTimeMillis()
)