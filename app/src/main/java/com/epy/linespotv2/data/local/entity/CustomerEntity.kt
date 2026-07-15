package com.epy.linespotv2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer_cache")
data class CustomerEntity(
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
    val activeMembershipId: Long,
    val membershipPackageName: String,
    val membershipExpiredAt: String,
    val membershipPackageCode: String,
    val membershipStatus: String,
    val activeParkingSessionId: Long,
    val totalParkingCount: Long,
    val totalPaymentAmount: Long,
    val unreadNotificationCount: Long,
    val cachedAt: Long = System.currentTimeMillis()
)
