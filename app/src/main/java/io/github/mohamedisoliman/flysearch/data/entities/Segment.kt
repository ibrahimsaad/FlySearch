package io.github.mohamedisoliman.flysearch.data.entities

import com.squareup.moshi.Json

data class Segment(
    @Json(name = "baggage")
    val baggage: String?,
    @Json(name = "cabinClass")
    val cabinClass: String?,
    @Json(name = "carrier")
    val carrier: CarrierX?,
    @Json(name = "destination")
    val destination: Destination?,
    @Json(name = "flightInfo")
    val flightInfo: FlightInfo?,
    @Json(name = "fuellingStops")
    val fuellingStops: List<Any?>?,
    @Json(name = "origin")
    val origin: Origin?,
    @Json(name = "seatCount")
    val seatCount: String?,
    @Json(name = "segmentId")
    val segmentId: String?,
    @Json(name = "stopOvertime")
    val stopOvertime: Int?,
    @Json(name = "stops")
    val stops: List<Any?>?
)