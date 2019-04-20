package io.github.mohamedisoliman.flysearch.data.entities.search

import com.squareup.moshi.Json

data class DisplayPricing(
    @Json(name = "base")
    val base: Double?,
    @Json(name = "conversionRate")
    val conversionRate: Int?,
    @Json(name = "currencyCode")
    val currencyCode: String?,
    @Json(name = "perPassenger")
    val perPassenger: PerPassenger?,
    @Json(name = "tax")
    val tax: Double?,
    @Json(name = "total")
    val total: Double?
)