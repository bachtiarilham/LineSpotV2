package com.epy.linespotv2.data.remote.mapper.profile

import com.epy.linespotv2.data.local.entity.CustomerEntity
import com.epy.linespotv2.domain.model.profile.CustomerModel

fun CustomerEntity.toCustomerDomain(): CustomerModel = CustomerModel(
    userId = userId,
    nik = nik,
    fullName = fullName,
    username = username,
    email = email,
    phone = phone,
    photoUrl = photoUrl,
    isVerified = isVerified,
    roleId = roleId,
    roleCode = roleCode,
    roleName = roleName,
    saldo = saldo,
    activeMembershipId = activeMembershipId,
    membershipPackageName = membershipPackageName,
    membershipExpiredAt = membershipExpiredAt,
    membershipPackageCode = membershipPackageCode,
    membershipStatus = membershipStatus,
    activeParkingSessionId = activeParkingSessionId,
    totalParkingCount = totalParkingCount,
    totalPaymentAmount = totalPaymentAmount,
    unreadNotificationCount = unreadNotificationCount
)

fun CustomerModel.toEntity(): CustomerEntity = CustomerEntity(
    userId = userId,
    nik = nik,
    fullName = fullName,
    username = username,
    email = email,
    phone = phone,
    photoUrl = photoUrl,
    isVerified = isVerified,
    roleId = roleId,
    roleCode = roleCode,
    roleName = roleName,
    saldo = saldo,
    activeMembershipId = activeMembershipId,
    membershipPackageName = membershipPackageName,
    membershipExpiredAt = membershipExpiredAt,
    membershipPackageCode = membershipPackageCode,
    membershipStatus = membershipStatus,
    activeParkingSessionId = activeParkingSessionId,
    totalParkingCount = totalParkingCount,
    totalPaymentAmount = totalPaymentAmount,
    unreadNotificationCount = unreadNotificationCount
)
