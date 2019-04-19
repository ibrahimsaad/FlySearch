package io.github.mohamedisoliman.flysearch.data.entities

import com.squareup.moshi.Json

data class Connection(
    @Json(name = "changeOfAirport")
    val changeOfAirport: Boolean?,
    @Json(name = "changeOfPlane")
    val changeOfPlane: Boolean?,
    @Json(name = "changeOfTerminal")
    val changeOfTerminal: Boolean?,
    @Json(name = "segmentIndex")
    val segmentIndex: Int?,
    @Json(name = "stopOver")
    val stopOver: Boolean?
)