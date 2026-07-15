package com.epy.linespotv2.data.remote.mapper.profile

import com.epy.linespotv2.data.remote.dto.profile.JukirDto
import com.epy.linespotv2.domain.model.profile.JukirModel

fun JukirDto?.toDomain(): JukirModel = JukirModel(
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
    lokasiId = this?.lokasiId ?: 0L,
    lokasiCode = this?.lokasiCode.orEmpty(),
    lokasiName = this?.lokasiName.orEmpty(),
    address = this?.address.orEmpty(),
    minLatitude = this?.minLatitude ?: 0.0,
    maxLatitude = this?.maxLatitude ?: 0.0,
    minLongitude = this?.minLongitude ?: 0.0,
    maxLongitude = this?.maxLongitude ?: 0.0,
    centerLatitude = this?.centerLatitude ?: 0.0,
    centerLongitude = this?.centerLongitude ?: 0.0,
    radiusMeter = this?.radiusMeter ?: 0L,
    areaId = this?.areaId ?: 0L,
    areaName = this?.areaName.orEmpty(),
    zoneId = this?.zoneId ?: 0L,
    zoneName = this?.zoneName.orEmpty(),
    assignmentEffectiveFrom = this?.assignmentEffectiveFrom.orEmpty(),
    assignmentEffectiveTo = this?.assignmentEffectiveTo.orEmpty(),
    todayIncome = this?.todayIncome ?: 0L,
    totalIncome = this?.totalIncome ?: 0L,
    todayTransactionCount = this?.todayTransactionCount ?: 0L,
    unreadNotificationCount = this?.unreadNotificationCount ?: 0L
)

