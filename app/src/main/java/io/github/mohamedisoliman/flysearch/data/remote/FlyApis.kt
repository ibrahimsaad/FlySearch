package io.github.mohamedisoliman.flysearch.data.remote

import io.github.mohamedisoliman.flysearch.data.entities.search.SearchBody
import io.github.mohamedisoliman.flysearch.data.entities.search.SearchResponse
import io.github.mohamedisoliman.flysearch.data.entities.suggestion.CityResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

/**
 *
 * Created by Mohamed Ibrahim on 4/19/19.
 */
interface FlyApis {

  @POST("flight/search/")
  fun search(@Body searchBody: SearchBody): Observable<SearchResponse>

  @GET
  fun loadSuggestions(@Url url: String): Observable<List<CityResponse>>

}