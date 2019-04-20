package io.github.mohamedisoliman.flysearch.data.entities.suggestion

import com.squareup.moshi.Json

data class CityResponse(
  @Json(name = "airports")
  val airports: List<Airport?>?,
  @Json(name = "city")
  val city: City?,
  @Json(name = "code")
  val code: String?,
  @Json(name = "fullName")
  val fullName: String?,
  @Json(name = "isBusStation")
  val isBusStation: Boolean?,
  @Json(name = "isCity")
  val isCity: Boolean?
)