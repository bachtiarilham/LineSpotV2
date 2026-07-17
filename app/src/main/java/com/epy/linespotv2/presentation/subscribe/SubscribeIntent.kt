package com.epy.linespotv2.presentation.subscribe

sealed interface SubscribeIntent {
    data object LoadPage : SubscribeIntent
    data object OpenBenefit : SubscribeIntent
    data object OpenPromo : SubscribeIntent
    data class SelectTab(val index: Int) : SubscribeIntent
    data class SelectPackage(val packageName: String) : SubscribeIntent
}
