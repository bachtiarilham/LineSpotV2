package com.epy.linespotv2.data.remote.dto.home

import com.epy.linespotv2.data.remote.dto.profile.CustomerDto
import com.google.gson.annotations.SerializedName

data class CustomerHomeDto (
    @SerializedName("profile") val customerDto: CustomerDto? = null,
    @SerializedName("contents")           val contents: List<ContentsDto?> = emptyList(),
    @SerializedName("unread_notif_count")         val unreadNotifCount: Long = 0L
)