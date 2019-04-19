package io.github.mohamedisoliman.flysearch.data.remote

import io.github.mohamedisoliman.flysearch.data.entities.SearchBody
import io.github.mohamedisoliman.flysearch.data.entities.SearchResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 *
 * Created by Mohamed Ibrahim on 4/19/19.
 */
interface FlyApis {

    @POST("flight/search/")
    fun search(@Body searchBody: SearchBody): Observable<SearchResponse>
}