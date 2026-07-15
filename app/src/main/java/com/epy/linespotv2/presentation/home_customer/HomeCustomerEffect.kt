package com.epy.linespotv2.presentation.home_customer

sealed interface HomeCustomerEffect {
    data object NavigateToPayment : HomeCustomerEffect
    data object NavigateToSettings : HomeCustomerEffect
    data object NavigateToTopUp : HomeCustomerEffect
    data object NavigateToSubscription : HomeCustomerEffect
    data object SessionExpired : HomeCustomerEffect
    data class ShowToast(val message : String) : HomeCustomerEffect
//    object NavigateToDetail : HomeEffect
    data object NavigateToBooking : HomeCustomerEffect
    data object NavigateToPromo : HomeCustomerEffect
    data object NavigateToLayananLain : HomeCustomerEffect
}