package io.github.mohamedisoliman.flysearch.presentation.search

import io.github.mohamedisoliman.flysearch.data.entities.suggestion.Airport
import io.github.mohamedisoliman.flysearch.presentation.mvi.MviResult

/**
 *
 * Created by Mohamed Ibrahim on 4/20/19.
 */
sealed class SearchMviResults : MviResult {
  //  data class SearchResult()
  object Todo : SearchMviResults()

  object INFLIGHT : SearchMviResults()

  data class OriginSuggestionsResult(val suggestions: List<Airport?>) : SearchMviResults()
  data class DestinationSuggestionsResult(val suggestions: List<Airport?>) : SearchMviResults()
}