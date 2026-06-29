package com.epy.linespotv2.presentation.subscribe

sealed interface SubscribeEffect {
    object NavigateToBenefit : SubscribeEffect
    object NavigateToPackage : SubscribeEffect
    object NavigateToPromo : SubscribeEffect
}
