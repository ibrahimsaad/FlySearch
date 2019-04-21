package io.github.mohamedisoliman.flysearch.domain

import io.github.mohamedisoliman.flysearch.data.Repository
import io.github.mohamedisoliman.flysearch.data.entities.search.SearchBody
import io.github.mohamedisoliman.flysearch.presentation.search.SearchMviResults
import io.reactivex.Observable

/**
 *
 * Created by Mohamed Ibrahim on 4/19/19.
 */
class SearchFlightsUseCase(private val repository: Repository) :
    BaseUseCase<SearchMviResults, SearchBody>() {

  override fun buildUseCase(parameter: SearchBody): Observable<SearchMviResults> {
    return repository.search(parameter)
        .flatMapIterable { it.itineraries }
        .map {
          Flight(
              it.carrier?.name, it.carrier?.code, "08:30", "20:00PM",//place holders :D
              "${it.displayPricing?.total} ${it.displayPricing?.currencyCode}"
              , "48KG"
          )
        }
        .toList()
        .toObservable()
        .map { SearchMviResults.SearchFlights(it) }
  }

}