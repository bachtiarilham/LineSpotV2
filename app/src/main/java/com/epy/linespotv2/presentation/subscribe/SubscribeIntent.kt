package com.epy.linespotv2.presentation.subscribe

sealed class SubscribeIntent {
    object loadPage : SubscribeIntent()
    object onOpenBenefit : SubscribeIntent()
    data class onClickTab(val index: Int) : SubscribeIntent()
    object onClickPackage : SubscribeIntent()
}
