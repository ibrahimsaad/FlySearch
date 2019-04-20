package io.github.mohamedisoliman.flysearch.presentation.search

import io.github.mohamedisoliman.flysearch.presentation.mvi.MviIntent

/**
 *
 * Created by Mohamed Ibrahim on 4/20/19.
 */
sealed class SearchIntents : MviIntent {
  data class LoadDestinationSuggestions(val text: String) : SearchIntents()
  data class LoadOriginSuggestions(val text: String) : SearchIntents()
  data class SearchFlights(
    val origin: String,
    val destination: String,
    val date: String
  ) : SearchIntents()

}