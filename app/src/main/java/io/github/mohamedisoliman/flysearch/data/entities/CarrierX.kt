package io.github.mohamedisoliman.flysearch.data.entities

import com.squareup.moshi.Json

data class CarrierX(
    @Json(name = "aircraft")
    val aircraft: Aircraft?,
    @Json(name = "code")
    val code: String?,
    @Json(name = "codeShare")
    val codeShare: CodeShare?,
    @Json(name = "flightNumber")
    val flightNumber: String?,
    @Json(name = "name")
    val name: String?
)