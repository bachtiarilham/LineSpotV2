package com.epy.linespotv2.core.utils.inlocation

sealed interface LocationBoundaryStatus {
    data object Inside : LocationBoundaryStatus
    data object Outside : LocationBoundaryStatus
    data object NeedPreciseRecheck : LocationBoundaryStatus
    data object LocationDisabled : LocationBoundaryStatus
    data object InvalidCurrentLocation : LocationBoundaryStatus
    data object InvalidBoundary : LocationBoundaryStatus
}

