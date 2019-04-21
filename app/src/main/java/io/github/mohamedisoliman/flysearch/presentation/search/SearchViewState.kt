package io.github.mohamedisoliman.flysearch.presentation.search

import io.github.mohamedisoliman.flysearch.data.entities.suggestion.Airport
import io.github.mohamedisoliman.flysearch.domain.Flight
import io.github.mohamedisoliman.flysearch.presentation.mvi.MviViewState

/**
 *
 * Created by Mohamed Ibrahim on 4/20/19.
 */
sealed class SearchViewState : MviViewState {
  object INFLIGHT : SearchViewState()
  data class FlightsResults(val flights: List<Flight>) : SearchViewState()
  object IDLE : SearchViewState()
  sealed class Suggestions : SearchViewState() {
    data class Origin(val data: List<Airport?>) : Suggestions()
    data class Destination(val data: List<Airport?>) : Suggestions()
  }
  data class ErrorHappened(val errorMessage: String) : SearchViewState()
}