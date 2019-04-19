package io.github.mohamedisoliman.flysearch.data.entities

import com.squareup.moshi.Json

data class LegXX(
    @Json(name = "arrivalDate")
    val arrivalDate: String?,
    @Json(name = "carrier")
    val carrier: Carrier?,
    @Json(name = "departureDate")
    val departureDate: String?,
    @Json(name = "destination")
    val destination: String?,
    @Json(name = "duration")
    val duration: Int?,
    @Json(name = "legId")
    val legId: String?,
    @Json(name = "origin")
    val origin: String?,
    @Json(name = "segments")
    val segments: List<String?>?,
    @Json(name = "stops")
    val stops: Int?
)