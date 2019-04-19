package io.github.mohamedisoliman.flysearch.data.entities

import com.squareup.moshi.Json

data class SearchResponse(
    @Json(name = "airports")
    val airports: List<Airport?>?,
    @Json(name = "createdAt")
    val createdAt: String?,
    @Json(name = "hash")
    val hash: String?,
    @Json(name = "itineraries")
    val itineraries: List<Itinerary?>?,
    @Json(name = "legs")
    val legs: List<LegX?>?,
    @Json(name = "meta")
    val meta: Meta?,
    @Json(name = "searchId")
    val searchId: String?,
    @Json(name = "searchParams")
    val searchParams: SearchParams?,
    @Json(name = "segments")
    val segments: List<Segment?>?
)