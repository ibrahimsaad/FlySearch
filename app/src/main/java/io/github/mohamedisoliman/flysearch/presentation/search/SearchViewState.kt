package io.github.mohamedisoliman.flysearch.presentation.search

import io.github.mohamedisoliman.flysearch.data.entities.suggestion.Airport
import io.github.mohamedisoliman.flysearch.presentation.mvi.MviViewState

/**
 *
 * Created by Mohamed Ibrahim on 4/20/19.
 */
sealed class SearchViewState : MviViewState {
  object INFLIGHT : SearchViewState()
  object IDLE : SearchViewState()
  data class SearchIsEnabled(val enabled: Boolean) : SearchViewState()
  data class Suggestions(val suggestions: List<Airport?>) : SearchViewState()
  data class ErrorHappened(val errorMessage: String) : SearchViewState()
}