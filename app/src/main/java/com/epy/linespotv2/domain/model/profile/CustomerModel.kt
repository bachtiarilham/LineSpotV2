package com.epy.linespotv2.domain.model.profile

data class CustomerModel(
    val userId: Long = 0L,
    val nik: String = "",
    val fullName: String = "",
    val username: String = "",
    val email: String = "",
    val phone: String = "",
    val photoUrl: String = "",
    val isVerified: Boolean = false,
    val roleId: Long = 0L,
    val roleCode: String = "",
    val roleName: String = "",
    val saldo: Long = 0L,
    val activeMembershipId: Long = 0L,
    val membershipPackageName: String = "",
    val membershipExpiredAt: String = "",
    val membershipPackageCode: String = "",
    val membershipStatus: String = "",
    val activeParkingSessionId: Long = 0L,
    val totalParkingCount: Long = 0L,
    val totalPaymentAmount: Long = 0L,
    val unreadNotificationCount: Long = 0L
)
