package io.github.mohamedisoliman.flysearch.data.entities

import com.squareup.moshi.Json

data class Itinerary(
    @Json(name = "airPriceGroup")
    val airPriceGroup: Int?,
    @Json(name = "carrier")
    val carrier: Carrier?,
    @Json(name = "connections")
    val connections: List<Connection?>?,
    @Json(name = "discounts")
    val discounts: List<String?>?,
    @Json(name = "displayPricing")
    val displayPricing: DisplayPricing?,
    @Json(name = "itineraryId")
    val itineraryId: String?,
    @Json(name = "legs")
    val legs: List<String?>?,
    @Json(name = "pricing")
    val pricing: Pricing?,
    @Json(name = "totalStops")
    val totalStops: Int?
)