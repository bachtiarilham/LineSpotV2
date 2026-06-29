package com.epy.linespotv2.presentation.home_customer

sealed interface HomeEffect {
    object NavigateToPayment : HomeEffect
    object NavigateToSettings : HomeEffect
    object NavigateToTopUp : HomeEffect
    object NavigateToSubscription : HomeEffect
    object SessionExpired : HomeEffect
    data class ShowToast(val message : String) : HomeEffect
//    object NavigateToDetail : HomeEffect
    object NavigateToBooking : HomeEffect
    object NavigateToPromo : HomeEffect
    object NavigateToLayananLain : HomeEffect
}