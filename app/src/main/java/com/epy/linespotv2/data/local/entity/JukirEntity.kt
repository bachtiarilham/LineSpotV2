package com.epy.linespotv2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jukir_cache")
data class JukirEntity(
    @PrimaryKey
    val userId: Long,
    val nik: String ,
    val fullName: String,
    val username: String,
    val email: String,
    val phone: String,
    val photoUrl: String,
    val isVerified: Boolean,
    val roleId: Long,
    val roleCode: String,
    val roleName: String,
    val saldo: Long,
    val lokasiId: Long,
    val lokasiCode: String,
    val lokasiName: String,
    val address: String,
    val minLatitude: Double,
    val maxLatitude: Double,
    val minLongitude: Double,
    val maxLongitude: Double,
    val centerLatitude: Double,
    val centerLongitude: Double,
    val radiusMeter: Long,
    val areaId: Long,
    val areaName: String,
    val zoneId: Long,
    val zoneName: String,
    val assignmentEffectiveFrom: String,
    val assignmentEffectiveTo: String,
    val todayIncome: Long,
    val totalIncome: Long,
    val todayTransactionCount: Long,
    val unreadNotificationCount: Long,
    val cachedAt: Long = System.currentTimeMillis()
)