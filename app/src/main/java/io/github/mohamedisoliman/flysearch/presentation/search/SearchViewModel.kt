package io.github.mohamedisoliman.flysearch.presentation.search

import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.PublishRelay
import io.github.mohamedisoliman.flysearch.domain.GetSuggestionsUseCase
import io.github.mohamedisoliman.flysearch.domain.SearchFlightsUseCase
import io.github.mohamedisoliman.flysearch.presentation.BODY_DATE_FORMAT
import io.github.mohamedisoliman.flysearch.presentation.isAFormattedDate
import io.github.mohamedisoliman.flysearch.presentation.mvi.MviViewModel
import io.github.mohamedisoliman.flysearch.presentation.search.SearchIntents.LoadDestinationSuggestions
import io.github.mohamedisoliman.flysearch.presentation.search.SearchIntents.LoadOriginSuggestions
import io.github.mohamedisoliman.flysearch.presentation.search.SearchIntents.SearchFlights
import io.github.mohamedisoliman.flysearch.presentation.search.SearchIntents.ValidateInputs
import io.github.mohamedisoliman.flysearch.presentation.search.SearchMviResults.DestinationSuggestionsResult
import io.github.mohamedisoliman.flysearch.presentation.search.SearchMviResults.OriginSuggestionsResult
import io.github.mohamedisoliman.flysearch.presentation.search.SearchMviResults.SearchIsEnabled
import io.github.mohamedisoliman.flysearch.presentation.search.SearchViewState.ErrorHappened
import io.github.mohamedisoliman.flysearch.presentation.search.SearchViewState.IDLE
import io.github.mohamedisoliman.flysearch.presentation.search.SearchViewState.INFLIGHT
import io.github.mohamedisoliman.flysearch.presentation.search.SearchViewState.Suggestions
import io.github.mohamedisoliman.flysearch.searchBodyMock
import io.reactivex.Observable
import io.reactivex.functions.Function
import timber.log.Timber
import java.util.concurrent.TimeUnit.SECONDS

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
            validateInputs(shared),
            searchFlights(shared)
        )
      }
    }
        .distinctUntilChanged()
        .scan<SearchViewState>(IDLE)
        { previousState: SearchViewState, result: SearchMviResults ->
          resultToViewStates(result, previousState)
        }
        .onErrorResumeNext(Function {
          Timber.e(it)
          Observable.just(ErrorHappened("Something wrong happened"))
        })
  }

  private fun validateInputs(shared: Observable<SearchIntents>): Observable<SearchIsEnabled> {
    return shared.ofType(ValidateInputs::class.java)
        .map {
          it.date.isAFormattedDate(BODY_DATE_FORMAT) &&
              !it.origin.code.isNullOrEmpty() &&
              !it.destination.code.isNullOrEmpty()
        }
        .map { SearchIsEnabled(it) }
  }

  private fun searchFlights(shared: Observable<SearchIntents>) =
    shared.ofType(SearchFlights::class.java)
        .flatMap { searchFlightsUseCase.build(searchBodyMock()) }

  private fun loadDestinationSuggestion(shared: Observable<SearchIntents>): Observable<SearchMviResults> {
    return shared.ofType(LoadDestinationSuggestions::class.java)
        .filter { it.text.isNotEmpty() && it.text.length <= 20 && it.text.length > 3 }
        .throttleLast(3, SECONDS)
        .switchMap { suggestionsUseCase.build(it.text) }
  }

  private fun loadOriginSuggestions(shared: Observable<SearchIntents>): Observable<SearchMviResults> {
    return shared.ofType(LoadOriginSuggestions::class.java)
        .filter { it.text.isNotEmpty() && it.text.length <= 20 && it.text.length > 3 }
        .throttleLast(3, SECONDS)
        .switchMap { suggestionsUseCase.build(it.text) }

  }

  private fun resultToViewStates(
    result: SearchMviResults,
    previousState: SearchViewState
  ): SearchViewState {
    val currentState = resultToState(result)
    return currentState
  }

  private fun resultToState(result: SearchMviResults): SearchViewState {
    return when (result) {
      is OriginSuggestionsResult -> {
        if (result.suggestions.isNotEmpty()) Suggestions(result.suggestions) else ErrorHappened(
            "No result found"
        )
      }
      is DestinationSuggestionsResult -> {
        if (result.suggestions.isNotEmpty()) Suggestions(result.suggestions) else ErrorHappened(
            "No result found"
        )
      }
      is SearchIsEnabled -> SearchViewState.SearchIsEnabled(result.enabled)
      is SearchMviResults.INFLIGHT -> INFLIGHT
      else -> IDLE
    }
  }

}