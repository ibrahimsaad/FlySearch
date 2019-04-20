package io.github.mohamedisoliman.flysearch.data

import io.github.mohamedisoliman.flysearch.data.entities.search.SearchBody
import io.github.mohamedisoliman.flysearch.data.entities.search.SearchResponse
import io.github.mohamedisoliman.flysearch.data.entities.suggestion.CityResponse
import io.reactivex.Observable
import retrofit2.http.Body

/**
 *
 * Created by Mohamed Ibrahim on 4/19/19.
 */
interface Repository {

  fun search(@Body searchBody: SearchBody): Observable<SearchResponse>

  fun loadSuggestions(text: String): Observable<List<CityResponse>>

}