package io.github.mohamedisoliman.flysearch.presentation.search

import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.PublishRelay
import io.github.mohamedisoliman.flysearch.domain.GetSuggestionsUseCase
import io.github.mohamedisoliman.flysearch.domain.SearchFlightsUseCase
import io.github.mohamedisoliman.flysearch.presentation.mvi.MviViewModel
import io.github.mohamedisoliman.flysearch.presentation.search.SearchIntents.LoadDestinationSuggestions
import io.github.mohamedisoliman.flysearch.presentation.search.SearchIntents.LoadOriginSuggestions
import io.github.mohamedisoliman.flysearch.presentation.search.SearchIntents.SearchFlights
import io.github.mohamedisoliman.flysearch.presentation.search.SearchMviResults.DestinationSuggestions
import io.github.mohamedisoliman.flysearch.presentation.search.SearchMviResults.Error
import io.github.mohamedisoliman.flysearch.presentation.search.SearchMviResults.OriginSuggestions
import io.github.mohamedisoliman.flysearch.presentation.search.SearchMviResults.Suggestions
import io.github.mohamedisoliman.flysearch.presentation.search.SearchViewState.ErrorHappened
import io.github.mohamedisoliman.flysearch.presentation.search.SearchViewState.FlightsResults
import io.github.mohamedisoliman.flysearch.presentation.search.SearchViewState.IDLE
import io.github.mohamedisoliman.flysearch.presentation.search.SearchViewState.INFLIGHT
import io.github.mohamedisoliman.flysearch.presentation.search.SearchViewState.Suggestions.Destination
import io.github.mohamedisoliman.flysearch.presentation.search.SearchViewState.Suggestions.Origin
import io.reactivex.Observable
import io.reactivex.functions.Function
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 *
 * Created by Mohamed Ibrahim on 4/19/19.
 */
class SearchViewModel(
  private val searchFlightsUseCase: SearchFlightsUseCase,
  private val suggestionsUseCase: GetSuggestionsUseCase
) : ViewModel(), MviViewModel<SearchIntents, SearchViewState> {

  private val intentsSubject = PublishRelay.create<SearchIntents>()

  override fun processIntents(intents: Observable<SearchIntents>) {
    intents.subscribe(intentsSubject)
  }

  override fun states(): Observable<SearchViewState> {
    return intentsSubject.compose { actions ->
      actions.publish { shared ->
        Observable.merge<SearchMviResults>(
            loadOriginSuggestions(shared),
            loadDestinationSuggestion(shared),
            searchFlights(shared)
        )
      }
    }
        .distinctUntilChanged()
        .scan<SearchViewState>(IDLE)
        { _, result: SearchMviResults -> resultToViewStates(result) }
        .onErrorResumeNext(Function {
          Timber.e(it)
          Observable.just(ErrorHappened("Something wrong happened"))
        })

  }

  private fun searchFlights(shared: Observable<SearchIntents>) =
    shared.ofType(SearchFlights::class.java)
        .flatMap {
          searchFlightsUseCase.build(it.searchBody)
              .cast(SearchMviResults::class.java)
              .startWith(SearchMviResults.INFLIGHT)
              .onErrorResumeNext(
                  Function { throwable ->
                    Observable.just(
                        SearchMviResults.Error(throwable.message.toString())
                    )
                  })

        }

  private fun loadDestinationSuggestion(shared: Observable<SearchIntents>): Observable<SearchMviResults> {
    return shared.ofType(LoadDestinationSuggestions::class.java)
        .filter { it.text.isNotEmpty() && it.text.length <= 20 && it.text.length > 3 }
        .switchMap { input ->
          suggestionsUseCase.build(input.text)
              .cast(SearchMviResults.Suggestions::class.java)
              .map { DestinationSuggestions(it.suggestions) }
              .cast(SearchMviResults::class.java)
              .startWith(SearchMviResults.INFLIGHT)

        }
  }

  private fun loadOriginSuggestions(shared: Observable<SearchIntents>): Observable<SearchMviResults> {
    return shared.ofType(LoadOriginSuggestions::class.java)
        .filter { it.text.isNotEmpty() && it.text.length <= 20 && it.text.length > 3 }
        .throttleLast(3, TimeUnit.SECONDS)
        .switchMap { input ->
          suggestionsUseCase.build(input.text)
              .cast(SearchMviResults.Suggestions::class.java)
              .map { OriginSuggestions(it.suggestions) }
              .cast(SearchMviResults::class.java)
              .startWith(SearchMviResults.INFLIGHT)

        }

  }

  private fun resultToViewStates(
    result: SearchMviResults
  ): SearchViewState {
    return resultToState(result)
  }

  private fun resultToState(result: SearchMviResults): SearchViewState {
    return when (result) {
      is OriginSuggestions -> {
        if (result.suggestions.isNotEmpty()) Origin(
            result.suggestions
        ) else ErrorHappened(
            "No result found"
        )
      }
      is DestinationSuggestions -> {
        if (result.suggestions.isNotEmpty()) Destination(
            result.suggestions
        ) else ErrorHappened(
            "No result found"
        )
      }
      is SearchMviResults.INFLIGHT -> INFLIGHT
      is SearchMviResults.SearchFlights -> FlightsResults(result.flights)
      is Suggestions -> TODO()
      is Error -> ErrorHappened(result.errorMessage)
    }
  }

}