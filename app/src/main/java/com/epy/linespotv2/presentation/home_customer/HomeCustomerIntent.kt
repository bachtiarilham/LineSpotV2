package com.epy.linespotv2.presentation.home_customer

sealed class HomeCustomerIntent {
    data object LoadHomeCustomer : HomeCustomerIntent()
    data class clickNotification(val notifId : Long): HomeCustomerIntent()
    data object clickProfile : HomeCustomerIntent()
    data object clickTopUp : HomeCustomerIntent()
    data object clickSubscribe : HomeCustomerIntent()
    data object clickBooking : HomeCustomerIntent()
    data object clickPromo : HomeCustomerIntent()
    data object clickLayananLain : HomeCustomerIntent()
    data object dismissError : HomeCustomerIntent()
    data object clickPayment: HomeCustomerIntent()
    data class showFeatureUnavailable(val featureName: String) : HomeCustomerIntent()
    data object dismissFeatureUnavailable : HomeCustomerIntent()
}