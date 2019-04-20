package io.github.mohamedisoliman.flysearch.data

import io.github.mohamedisoliman.flysearch.data.entities.search.SearchBody
import io.github.mohamedisoliman.flysearch.data.entities.search.SearchResponse
import io.github.mohamedisoliman.flysearch.data.entities.suggestion.CityResponse
import io.github.mohamedisoliman.flysearch.data.remote.FlyApis
import io.reactivex.Observable

/**
 *
 * Created by Mohamed Ibrahim on 4/19/19.
 */
const val SUGGESTIONS_BASE_URL = "https://nz.fly365.com/api/suggest/airport/search"


class FlyRepository(private val flyApis: FlyApis) : Repository {

  override fun loadSuggestions(text: String): Observable<List<CityResponse>> {
    return flyApis.loadSuggestions("$SUGGESTIONS_BASE_URL?q=$text")
  }

  override fun search(searchBody: SearchBody): Observable<SearchResponse> =
    flyApis.search(searchBody)
}