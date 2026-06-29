package com.epy.linespotv2.presentation.home_customer

sealed class HomeIntent {
    object LoadHome : HomeIntent()
//    data class clickServiceMenu (val menuId : Long) : HomeIntent()

    data class clickNotification(val notifId : Long): HomeIntent()
    object clickProfile : HomeIntent()
    object clickTopUp : HomeIntent()
    object clickSubscribe : HomeIntent()
    object clickBooking : HomeIntent()
    object clickPromo : HomeIntent()
    object clickLayananLain : HomeIntent()
    object dismissError : HomeIntent()
    object clickPayment: HomeIntent()
}