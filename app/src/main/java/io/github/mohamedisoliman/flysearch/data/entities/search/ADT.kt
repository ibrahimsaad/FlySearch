package io.github.mohamedisoliman.flysearch.data.entities.search

import com.squareup.moshi.Json

data class ADT(
    @Json(name = "base")
    val base: Double?,
    @Json(name = "code")
    val code: String?,
    @Json(name = "count")
    val count: Int?,
    @Json(name = "tax")
    val tax: Double?,
    @Json(name = "total")
    val total: Double?
)