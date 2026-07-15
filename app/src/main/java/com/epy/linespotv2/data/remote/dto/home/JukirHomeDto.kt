package com.epy.linespotv2.data.remote.dto.home

import com.epy.linespotv2.data.remote.dto.profile.JukirDto
import com.google.gson.annotations.SerializedName

data class JukirHomeDto(
    @SerializedName("profile") val jukirDto: JukirDto? = null,
    @SerializedName("contents")           val contents: List<ContentsDto?>? = emptyList(),
    @SerializedName("unread_notif_count")         val unreadNotifCount: Long? = 0L
)
