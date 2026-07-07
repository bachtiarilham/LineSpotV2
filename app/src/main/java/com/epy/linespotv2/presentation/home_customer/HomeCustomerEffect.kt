package com.epy.linespotv2.presentation.home_customer

sealed interface HomeCustomerEffect {
    object NavigateToPayment : HomeCustomerEffect
    object NavigateToSettings : HomeCustomerEffect
    object NavigateToTopUp : HomeCustomerEffect
    object NavigateToSubscription : HomeCustomerEffect
    object SessionExpired : HomeCustomerEffect
    data class ShowToast(val message : String) : HomeCustomerEffect
//    object NavigateToDetail : HomeEffect
    object NavigateToBooking : HomeCustomerEffect
    object NavigateToPromo : HomeCustomerEffect
    object NavigateToLayananLain : HomeCustomerEffect
}