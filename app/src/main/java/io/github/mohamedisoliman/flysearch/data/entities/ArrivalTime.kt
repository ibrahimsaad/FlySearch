package io.github.mohamedisoliman.flysearch.data.entities

import com.squareup.moshi.Json

data class ArrivalTime(
    @Json(name = "date")
    val date: String?,
    @Json(name = "gds")
    val gds: String?,
    @Json(name = "time")
    val time: String?,
    @Json(name = "zone")
    val zone: String?
)