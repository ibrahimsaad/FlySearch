package io.github.mohamedisoliman.flysearch.data.entities.suggestion

import com.squareup.moshi.Json

data class Airport(
  @Json(name = "city")
  val city: String? = "",
  @Json(name = "code")
  val code: String? = "",
  @Json(name = "fullName")
  val fullName: String? = "",
  @Json(name = "isBusStation")
  val isBusStation: Boolean? = false
)