package com.epy.linespotv2.core.utils.inlocation

import android.location.Location
import com.epy.linespotv2.domain.model.profile.JukirModel
import kotlin.math.max
import kotlin.math.min

object InLocation {
    fun isInsideBounds(
        currentLatitude: Double,
        currentLongitude: Double,
        minLatitude: Double,
        maxLatitude: Double,
        minLongitude: Double,
        maxLongitude: Double
    ): Boolean {
        if (!isValidCoordinate(currentLatitude, currentLongitude)) return false
        if (!hasValidBounds(minLatitude, maxLatitude, minLongitude, maxLongitude)) return false

        val normalizedMinLatitude = min(minLatitude, maxLatitude)
        val normalizedMaxLatitude = max(minLatitude, maxLatitude)
        val normalizedMinLongitude = min(minLongitude, maxLongitude)
        val normalizedMaxLongitude = max(minLongitude, maxLongitude)

        return currentLatitude in normalizedMinLatitude..normalizedMaxLatitude &&
            currentLongitude in normalizedMinLongitude..normalizedMaxLongitude
    }

    fun isNearCenter(
        currentLatitude: Double,
        currentLongitude: Double,
        centerLatitude: Double,
        centerLongitude: Double,
        radiusMeters: Float
    ): Boolean {
        if (!isValidCoordinate(currentLatitude, currentLongitude)) return false
        if (!isValidCoordinate(centerLatitude, centerLongitude)) return false
        if (radiusMeters <= 0f) return false

        val result = FloatArray(1)
        Location.distanceBetween(
            currentLatitude,
            currentLongitude,
            centerLatitude,
            centerLongitude,
            result
        )
        return result.firstOrNull()?.let { it <= radiusMeters } == true
    }

    fun evaluate(
        currentLatitude: Double,
        currentLongitude: Double,
        minLatitude: Double,
        maxLatitude: Double,
        minLongitude: Double,
        maxLongitude: Double,
        centerLatitude: Double,
        centerLongitude: Double,
        radiusMeters: Float = DEFAULT_RADIUS_METERS,
        recheckMarginMeters: Float = DEFAULT_RECHECK_MARGIN_METERS
    ): LocationBoundaryStatus {
        if (!isValidCoordinate(currentLatitude, currentLongitude)) {
            return LocationBoundaryStatus.InvalidCurrentLocation
        }

        val hasBounds = hasValidBounds(minLatitude, maxLatitude, minLongitude, maxLongitude)
        val hasCenter = isValidCoordinate(centerLatitude, centerLongitude)

        if (!hasBounds && !hasCenter) {
            return LocationBoundaryStatus.InvalidBoundary
        }

        if (hasBounds && isInsideBounds(
                currentLatitude = currentLatitude,
                currentLongitude = currentLongitude,
                minLatitude = minLatitude,
                maxLatitude = maxLatitude,
                minLongitude = minLongitude,
                maxLongitude = maxLongitude
            )
        ) {
            return LocationBoundaryStatus.Inside
        }

        if (hasCenter && isNearCenter(
                currentLatitude = currentLatitude,
                currentLongitude = currentLongitude,
                centerLatitude = centerLatitude,
                centerLongitude = centerLongitude,
                radiusMeters = radiusMeters
            )
        ) {
            return LocationBoundaryStatus.Inside
        }

        if (hasCenter && isNearCenter(
                currentLatitude = currentLatitude,
                currentLongitude = currentLongitude,
                centerLatitude = centerLatitude,
                centerLongitude = centerLongitude,
                radiusMeters = radiusMeters + recheckMarginMeters
            )
        ) {
            return LocationBoundaryStatus.NeedPreciseRecheck
        }

        return LocationBoundaryStatus.Outside
    }

    fun evaluate(
        userModel: JukirModel,
        currentLatitude: Double,
        currentLongitude: Double,
        radiusMeters: Float = DEFAULT_RADIUS_METERS,
        recheckMarginMeters: Float = DEFAULT_RECHECK_MARGIN_METERS
    ): LocationBoundaryStatus {
        return evaluate(
            currentLatitude = currentLatitude,
            currentLongitude = currentLongitude,
            minLatitude = userModel.minLatitude,
            maxLatitude = userModel.maxLatitude,
            minLongitude = userModel.minLongitude,
            maxLongitude = userModel.maxLongitude,
            centerLatitude = userModel.centerLatitude,
            centerLongitude = userModel.centerLongitude,
            radiusMeters = radiusMeters,
            recheckMarginMeters = recheckMarginMeters
        )
    }

    private fun hasValidBounds(
        minLatitude: Double,
        maxLatitude: Double,
        minLongitude: Double,
        maxLongitude: Double
    ): Boolean {
        if (!isValidCoordinate(minLatitude, minLongitude)) return false
        if (!isValidCoordinate(maxLatitude, maxLongitude)) return false

        return !(minLatitude == 0.0 &&
            maxLatitude == 0.0 &&
            minLongitude == 0.0 &&
            maxLongitude == 0.0)
    }

    private fun isValidCoordinate(latitude: Double, longitude: Double): Boolean {
        return latitude in -90.0..90.0 && longitude in -180.0..180.0
    }

    private const val DEFAULT_RADIUS_METERS = 100f
    private const val DEFAULT_RECHECK_MARGIN_METERS = 50f
}

