package io.github.mohamedisoliman.flysearch.data.entities.search

import com.squareup.moshi.Json

data class Airport(
    @Json(name = "cityCode")
    val cityCode: String?,
    @Json(name = "cityName")
    val cityName: String?,
    @Json(name = "code")
    val code: String?,
    @Json(name = "countryCode")
    val countryCode: String?,
    @Json(name = "countryName")
    val countryName: String?,
    @Json(name = "name")
    val name: String?
)