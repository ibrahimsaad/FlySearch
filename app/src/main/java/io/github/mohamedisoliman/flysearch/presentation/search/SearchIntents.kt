package io.github.mohamedisoliman.flysearch.presentation.search

import io.github.mohamedisoliman.flysearch.data.entities.search.Leg
import io.github.mohamedisoliman.flysearch.data.entities.search.SearchBody
import io.github.mohamedisoliman.flysearch.data.entities.suggestion.Airport
import io.github.mohamedisoliman.flysearch.presentation.mvi.MviIntent

/**
 *
 * Created by Mohamed Ibrahim on 4/20/19.
 */
sealed class SearchIntents : MviIntent {
  data class SearchFlights(val searchBody: SearchBody) : SearchIntents()
  data class LoadDestinationSuggestions(val text: String) : SearchIntents()
  data class LoadOriginSuggestions(val text: String) : SearchIntents()
  data class ValidateInputs(
    val origin: Airport = Airport(),
    val destination: Airport = Airport(),
    val date: String,
    val adult: String,
    val infant: String,
    val child: String,
    val cabinclass: String
  ) : SearchIntents() {

    fun toSearchBody() =
      SearchBody(
          adult.toInt(),
          cabinclass,
          child.toInt(),
          infant.toInt(),
          listOf(Leg(date, destination.code, origin.code))
      )
  }

}