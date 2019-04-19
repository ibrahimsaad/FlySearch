package io.github.mohamedisoliman.flysearch.data.entities

import com.squareup.moshi.Json

data class PerPassenger(
    @Json(name = "ADT")
    val aDT: ADT?
)