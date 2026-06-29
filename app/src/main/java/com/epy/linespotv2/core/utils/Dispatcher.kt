package com.epy.linespotv2.core.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlin.coroutines.CoroutineContext

interface Dispatcher {
    val io : CoroutineContext
    val main : CoroutineContext
    val default : CoroutineContext
}