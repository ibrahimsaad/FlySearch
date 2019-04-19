package io.github.mohamedisoliman.flysearch.data.entities

import com.squareup.moshi.Json

data class Leg(
    @Json(name = "departureDate")
    val departureDate: String?,
    @Json(name = "destination")
    val destination: String?,
    @Json(name = "origin")
    val origin: String?
)