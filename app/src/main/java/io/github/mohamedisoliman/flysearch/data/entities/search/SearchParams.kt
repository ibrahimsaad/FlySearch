package io.github.mohamedisoliman.flysearch.data.entities.search

import com.squareup.moshi.Json

data class SearchParams(
    @Json(name = "adult")
    val adult: Int?,
    @Json(name = "cabinClass")
    val cabinClass: String?,
    @Json(name = "child")
    val child: Int?,
    @Json(name = "infant")
    val infant: Int?,
    @Json(name = "legs")
    val legs: List<LegX?>?,
    @Json(name = "searchType")
    val searchType: String?,
    @Json(name = "storeUser")
    val storeUser: String?,
    @Json(name = "tripType")
    val tripType: String?
)