package io.github.mohamedisoliman.flysearch.data.entities.suggestion

import com.squareup.moshi.Json

data class City(
  @Json(name = "code")
  val code: String?
)