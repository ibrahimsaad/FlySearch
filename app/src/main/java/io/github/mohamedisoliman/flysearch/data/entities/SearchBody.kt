package io.github.mohamedisoliman.flysearch.data.entities

import com.squareup.moshi.Json

data class SearchBody(
    @Json(name = "adult")
    val adult: Int?,
    @Json(name = "cabinClass")
    val cabinClass: String?,
    @Json(name = "child")
    val child: Int?,
    @Json(name = "infant")
    val infant: Int?,
    @Json(name = "legs")
    val legs: List<Leg?>?
)