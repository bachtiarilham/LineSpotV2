package com.epy.linespotv2.core.network

sealed class ApiCondition<out T> {
    object AppLoading : ApiCondition<Nothing>()
    data class AppSuccess<out T>(val data : T) : ApiCondition <T>()
    data class AppFailure (val exception: Throwable) : ApiCondition<Nothing>()
//    data class AppFailure <out T> (val exception: Throwable) : ApiCondition<Nothing>()
}
