package io.github.mohamedisoliman.flysearch.data.entities.search

import com.squareup.moshi.Json

data class CodeShare(
    @Json(name = "code")
    val code: String?,
    @Json(name = "flightNumber")
    val flightNumber: String?,
    @Json(name = "name")
    val name: String?
)