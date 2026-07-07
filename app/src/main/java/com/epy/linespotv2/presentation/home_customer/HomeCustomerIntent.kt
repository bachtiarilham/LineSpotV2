package com.epy.linespotv2.presentation.home_customer

sealed class HomeCustomerIntent {
    object LoadHomeCustomer : HomeCustomerIntent()
//    data class clickServiceMenu (val menuId : Long) : HomeIntent()

    data class clickNotification(val notifId : Long): HomeCustomerIntent()
    object clickProfile : HomeCustomerIntent()
    object clickTopUp : HomeCustomerIntent()
    object clickSubscribe : HomeCustomerIntent()
    object clickBooking : HomeCustomerIntent()
    object clickPromo : HomeCustomerIntent()
    object clickLayananLain : HomeCustomerIntent()
    object dismissError : HomeCustomerIntent()
    object clickPayment: HomeCustomerIntent()
}