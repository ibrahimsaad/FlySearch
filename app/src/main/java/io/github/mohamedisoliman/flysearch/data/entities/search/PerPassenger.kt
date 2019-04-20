package io.github.mohamedisoliman.flysearch.data.entities.search

import com.squareup.moshi.Json

data class PerPassenger(
    @Json(name = "ADT")
    val aDT: ADT?
)