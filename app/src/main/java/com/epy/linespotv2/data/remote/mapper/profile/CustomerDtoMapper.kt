package com.epy.linespotv2.data.remote.mapper.profile

import com.epy.linespotv2.data.remote.dto.profile.CustomerDto
import com.epy.linespotv2.domain.model.profile.CustomerModel

fun CustomerDto?.toDomain(): CustomerModel = CustomerModel(
    userId = this?.userId ?: 0L,
    nik = this?.nik.orEmpty(),
    fullName = this?.fullName.orEmpty(),
    username = this?.username.orEmpty(),
    email = this?.email.orEmpty(),
    phone = this?.phone.orEmpty(),
    photoUrl = this?.photoUrl.orEmpty(),
    isVerified = this?.isVerified ?: false,
    roleId = this?.roleId ?: 0L,
    roleCode = this?.roleCode.orEmpty(),
    roleName = this?.roleName.orEmpty(),
    saldo = this?.saldo ?: 0L,
    activeMembershipId = this?.activeMembershipId ?: 0L,
    membershipPackageName = this?.membershipPackageName.orEmpty(),
    membershipExpiredAt = this?.membershipExpiredAt.orEmpty(),
    membershipPackageCode = this?.membershipPackageCode.orEmpty(),
    membershipStatus = this?.membershipStatus.orEmpty(),
    activeParkingSessionId = this?.activeParkingSessionId ?: 0L,
    totalParkingCount = this?.totalParkingCount ?: 0L,
    totalPaymentAmount = this?.totalPaymentAmount ?: 0L,
    unreadNotificationCount = this?.unreadNotificationCount ?: 0L
)