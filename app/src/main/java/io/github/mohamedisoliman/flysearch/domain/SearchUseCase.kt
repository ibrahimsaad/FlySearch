package io.github.mohamedisoliman.flysearch.domain

import io.github.mohamedisoliman.flysearch.data.Repository
import io.github.mohamedisoliman.flysearch.data.entities.SearchBody
import io.github.mohamedisoliman.flysearch.data.entities.SearchResponse
import io.reactivex.Observable

/**
 *
 * Created by Mohamed Ibrahim on 4/19/19.
 */
class SearchUseCase(private val repository: Repository) :
    BaseUseCase<SearchResponse, SearchBody>() {

    override fun buildUseCase(parameter: SearchBody): Observable<SearchResponse> {
        return repository.search(parameter)
    }


}