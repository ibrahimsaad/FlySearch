package io.github.mohamedisoliman.flysearch.data.entities

import com.squareup.moshi.Json

data class FlightInfo(
    @Json(name = "distance")
    val distance: Int?,
    @Json(name = "flightTime")
    val flightTime: Int?
)