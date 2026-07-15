package com.epy.linespotv2.core.utils.inlocation

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import dagger.hilt.android.qualifiers.ApplicationContext
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.coroutines.resume
import javax.inject.Inject

class LocationTracker @Inject constructor(
    @ApplicationContext context: Context
) {
    private val appContext = context.applicationContext
    private val client: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(appContext)

    suspend fun isLocationEnabled(): Boolean = suspendCancellableCoroutine { continuation ->
        val request = LocationSettingsRequest.Builder()
            .addLocationRequest(createBalancedRequest())
            .build()

        val task = LocationServices.getSettingsClient(appContext)
            .checkLocationSettings(request)

        task.addOnSuccessListener {
            if (continuation.isActive) continuation.resume(true)
        }.addOnFailureListener {
            if (continuation.isActive) continuation.resume(false)
        }
    }

    @SuppressLint("MissingPermission")
    fun monitorBalancedLocation(): Flow<Location> = callbackFlow {
        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { trySend(it).isSuccess }
            }
        }

        client.requestLocationUpdates(
            createBalancedRequest(),
            callback,
            null
        )

        awaitClose {
            client.removeLocationUpdates(callback)
        }
    }

    @SuppressLint("MissingPermission")
    fun monitorPreciseLocation(): Flow<Location> = callbackFlow {
        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { trySend(it).isSuccess }
            }
        }

        client.requestLocationUpdates(
            createHighAccuracyRequest(),
            callback,
            null
        )

        awaitClose {
            client.removeLocationUpdates(callback)
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(
        highAccuracy: Boolean = false
    ): Location? = suspendCancellableCoroutine { continuation ->
        val priority = if (highAccuracy) {
            Priority.PRIORITY_HIGH_ACCURACY
        } else {
            Priority.PRIORITY_BALANCED_POWER_ACCURACY
        }

        client.getCurrentLocation(
            priority,
            CancellationTokenSource().token
        )
            .addOnSuccessListener { location ->
                if (continuation.isActive) continuation.resume(location)
            }
            .addOnFailureListener {
                if (continuation.isActive) continuation.resume(null)
            }
    }

    private fun createBalancedRequest(): LocationRequest {
        return LocationRequest.Builder(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            BALANCED_INTERVAL_MILLIS
        )
            .setMinUpdateIntervalMillis(BALANCED_MIN_UPDATE_INTERVAL_MILLIS)
            .setWaitForAccurateLocation(false)
            .build()
    }

    private fun createHighAccuracyRequest(): LocationRequest {
        return LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            HIGH_ACCURACY_INTERVAL_MILLIS
        )
            .setMinUpdateIntervalMillis(HIGH_ACCURACY_MIN_UPDATE_INTERVAL_MILLIS)
            .setWaitForAccurateLocation(true)
            .build()
    }

    companion object {
        private const val BALANCED_INTERVAL_MILLIS = 60_000L
        private const val BALANCED_MIN_UPDATE_INTERVAL_MILLIS = 30_000L
        private const val HIGH_ACCURACY_INTERVAL_MILLIS = 10_000L
        private const val HIGH_ACCURACY_MIN_UPDATE_INTERVAL_MILLIS = 5_000L
    }
}

