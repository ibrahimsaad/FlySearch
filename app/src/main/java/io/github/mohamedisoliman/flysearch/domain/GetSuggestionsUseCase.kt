package io.github.mohamedisoliman.flysearch.domain

import io.github.mohamedisoliman.flysearch.data.Repository
import io.github.mohamedisoliman.flysearch.data.entities.suggestion.Airport
import io.github.mohamedisoliman.flysearch.presentation.search.SearchMviResults
import io.reactivex.Observable

/**
 *
 * Created by Mohamed Ibrahim on 4/20/19.
 */
class GetSuggestionsUseCase(private val repository: Repository) : BaseUseCase<SearchMviResults, String>() {

  override fun buildUseCase(parameter: String): Observable<SearchMviResults> {
    return repository.loadSuggestions(parameter)
        .flatMapIterable { it }
        .flatMap {
          Observable.fromIterable(it.airports ?: listOf(Airport(fullName = it.fullName)))
        }
        .toList()
        .toObservable()
        .cache()
        .map { SearchMviResults.Suggestions(it) }
  }
}