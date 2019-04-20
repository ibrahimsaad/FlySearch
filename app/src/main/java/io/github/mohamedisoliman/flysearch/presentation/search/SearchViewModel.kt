package io.github.mohamedisoliman.flysearch.presentation.search

import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.PublishRelay
import io.github.mohamedisoliman.flysearch.domain.GetSuggestionsUseCase
import io.github.mohamedisoliman.flysearch.domain.SearchFlightsUseCase
import io.github.mohamedisoliman.flysearch.presentation.mvi.MviViewModel
import io.github.mohamedisoliman.flysearch.presentation.search.SearchViewState.IDLE
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

            shared.ofType(SearchIntents.LoadOriginSuggestions::class.java)
                .throttleLast(2, SECONDS)
                .filter { it.text.isNotEmpty() && it.text.length > 3 && it.text.length < 15 }
                .flatMap { suggestionsUseCase.build(it.text) },

            shared.ofType(SearchIntents.LoadDestinationSuggestions::class.java)
                .throttleLast(2, SECONDS)
                .filter { it.text.isNotEmpty() && it.text.length > 3 && it.text.length < 15 }
                .flatMap { suggestionsUseCase.build(it.text) }.takeUntil(
                    shared
                ), //wait until origin loads

            shared.ofType(SearchIntents.SearchFlights::class.java)
                .flatMap { searchFlightsUseCase.build(searchBodyMock()) } //TODO

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
          Observable.just(
              SearchViewState.ErrorHappened("Something wrong happened")
          )
        })
  }

  private fun resultToViewStates(
    result: SearchMviResults,
    previousState: SearchViewState
  ): SearchViewState {
    return when (result) {
      is SearchMviResults.OriginSuggestionsResult -> {
        if (result.suggestions.isNotEmpty()) SearchViewState.Suggestions(
            result.suggestions
        ) else SearchViewState.ErrorHappened("No result found")
      }
      is SearchMviResults.DestinationSuggestionsResult -> {
        if (result.suggestions.isNotEmpty()) SearchViewState.Suggestions(
            result.suggestions
        ) else SearchViewState.ErrorHappened("No result found")
      }
      is SearchMviResults.INFLIGHT -> SearchViewState.INFLIGHT
      else -> IDLE
    }
  }

}