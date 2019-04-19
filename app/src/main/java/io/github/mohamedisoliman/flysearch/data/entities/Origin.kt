package io.github.mohamedisoliman.flysearch.data.entities

import com.squareup.moshi.Json

data class Origin(
    @Json(name = "arrivalTime")
    val arrivalTime: ArrivalTime?,
    @Json(name = "code")
    val code: String?,
    @Json(name = "departureTime")
    val departureTime: DepartureTime?,
    @Json(name = "terminal")
    val terminal: String?
)