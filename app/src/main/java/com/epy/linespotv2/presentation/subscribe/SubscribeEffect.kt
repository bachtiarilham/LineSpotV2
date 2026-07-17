package com.epy.linespotv2.presentation.subscribe

sealed interface SubscribeEffect {
    object NavigateToBenefit : SubscribeEffect
    data class NavigateToPackage(val packageName: String) : SubscribeEffect
    object NavigateToPromo : SubscribeEffect
}
