package io.github.mohamedisoliman.flysearch.data

import io.github.mohamedisoliman.flysearch.data.entities.SearchBody
import io.github.mohamedisoliman.flysearch.data.entities.SearchResponse
import io.github.mohamedisoliman.flysearch.data.remote.FlyApis
import io.reactivex.Observable

/**
 *
 * Created by Mohamed Ibrahim on 4/19/19.
 */
class FlyRepository(private val flyApis: FlyApis) : Repository {

    override fun search(searchBody: SearchBody): Observable<SearchResponse> = flyApis.search(searchBody)
}