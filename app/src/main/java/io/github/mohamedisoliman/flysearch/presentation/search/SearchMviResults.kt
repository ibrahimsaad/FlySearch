package io.github.mohamedisoliman.flysearch.presentation.search

import io.github.mohamedisoliman.flysearch.data.entities.suggestion.Airport
import io.github.mohamedisoliman.flysearch.domain.Flight
import io.github.mohamedisoliman.flysearch.presentation.mvi.MviResult

/**
 *
 * Created by Mohamed Ibrahim on 4/20/19.
 */
sealed class SearchMviResults : MviResult {
  data class SearchFlights(val flights: List<Flight>) : SearchMviResults()
  object INFLIGHT : SearchMviResults()
  data class Error(val errorMessage: String) : SearchMviResults()
  data class Suggestions(val suggestions: List<Airport?>) : SearchMviResults()
  data class OriginSuggestions(val suggestions: List<Airport?>) : SearchMviResults()
  data class DestinationSuggestions(val suggestions: List<Airport?>) : SearchMviResults()
}