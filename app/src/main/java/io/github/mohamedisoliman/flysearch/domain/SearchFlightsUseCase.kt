package io.github.mohamedisoliman.flysearch.domain

import io.github.mohamedisoliman.flysearch.data.Repository
import io.github.mohamedisoliman.flysearch.data.entities.search.SearchBody
import io.github.mohamedisoliman.flysearch.presentation.search.SearchMviResults
import io.reactivex.Observable

/**
 *
 * Created by Mohamed Ibrahim on 4/19/19.
 */
class SearchFlightsUseCase(private val repository: Repository) :
    BaseUseCase<SearchMviResults, SearchBody>() {

  override fun buildUseCase(parameter: SearchBody): Observable<SearchMviResults> {
    return repository.search(parameter)
        .map<SearchMviResults> { SearchMviResults.Todo }
  }

}