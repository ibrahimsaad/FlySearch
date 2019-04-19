package io.github.mohamedisoliman.flysearch.data.entities

import com.squareup.moshi.Json

data class Carrier(
    @Json(name = "code")
    val code: String?,
    @Json(name = "name")
    val name: String?
)