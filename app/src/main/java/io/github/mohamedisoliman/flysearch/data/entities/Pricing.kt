package io.github.mohamedisoliman.flysearch.data.entities

import com.squareup.moshi.Json

data class Pricing(
    @Json(name = "base")
    val base: Double?,
    @Json(name = "currencyCode")
    val currencyCode: String?,
    @Json(name = "perPassenger")
    val perPassenger: PerPassenger?,
    @Json(name = "tax")
    val tax: Double?,
    @Json(name = "total")
    val total: Double?
)