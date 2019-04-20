package io.github.mohamedisoliman.flysearch.data.entities.search

import com.squareup.moshi.Json

data class Meta(
    @Json(name = "currency")
    val currency: String?,
    @Json(name = "currencyConversionRate")
    val currencyConversionRate: Int?,
    @Json(name = "currencyConversionTime")
    val currencyConversionTime: String?,
    @Json(name = "displayCurrency")
    val displayCurrency: String?,
    @Json(name = "groupId")
    val groupId: String?,
    @Json(name = "locale")
    val locale: String?,
    @Json(name = "storeId")
    val storeId: String?,
    @Json(name = "storeUser")
    val storeUser: String?
)