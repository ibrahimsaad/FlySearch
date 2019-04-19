package io.github.mohamedisoliman.flysearch.data

import io.github.mohamedisoliman.flysearch.data.entities.SearchBody
import io.github.mohamedisoliman.flysearch.data.entities.SearchResponse
import io.reactivex.Observable
import retrofit2.http.Body

/**
 *
 * Created by Mohamed Ibrahim on 4/19/19.
 */
interface Repository {

    fun search(@Body searchBody: SearchBody): Observable<SearchResponse>
}