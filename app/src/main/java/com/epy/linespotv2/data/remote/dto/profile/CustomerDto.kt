package com.epy.linespotv2.data.remote.dto.profile

import com.google.gson.annotations.SerializedName

data class CustomerDto(
    @SerializedName("user_id") val userId: Long = 0L,
    @SerializedName("nik") val nik: String = "",
    @SerializedName("full_name") val fullName: String = "",
    @SerializedName("username") val username: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("phone") val phone: String = "",
    @SerializedName("avatar_url") val photoUrl: String = "",
    @SerializedName("is_verified") val isVerified: Boolean = false,
    @SerializedName("role_id") val roleId: Long = 0L,
    @SerializedName("role_code") val roleCode: String = "",
    @SerializedName("role_name") val roleName: String = "",
    @SerializedName("saldo")  val saldo : Long = 0L,
    @SerializedName("active_membership_id")  val activeMembershipId : Long = 0L,
    @SerializedName("membership_package_name")  val membershipPackageName : String = "",
    @SerializedName("membership_expired_at")  val membershipExpiredAt : String = "",
    @SerializedName("membership_package_code")  val membershipPackageCode : String = "",
    @SerializedName("membership_status")  val membershipStatus : String = "",
    @SerializedName("active_parking_session")  val activeParkingSessionId : Long = 0L,
    @SerializedName("total_parking_count")  val totalParkingCount : Long = 0L,
    @SerializedName("total_payment_amount")  val totalPaymentAmount : Long = 0L,
    @SerializedName("unread_notification_count")  val unreadNotificationCount : Long = 0L,
)

