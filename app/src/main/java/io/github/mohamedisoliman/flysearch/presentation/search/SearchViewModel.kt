package io.github.mohamedisoliman.flysearch.presentation.search

import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.github.mohamedisoliman.flysearch.data.entities.SearchResponse
import io.github.mohamedisoliman.flysearch.domain.SearchUseCase
import io.github.mohamedisoliman.flysearch.searchBodyMock
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

/**
 *
 * Created by Mohamed Ibrahim on 4/19/19.
 */
class SearchViewModel(private val searchUseCase: SearchUseCase) : ViewModel() {

    private val dataBehaviorRelay = BehaviorRelay.create<SearchResponse>()
    private val errorsPubisRelay = PublishRelay.create<String>()
    private val loadingPublishRelay = PublishRelay.create<Boolean>()

    private val compositeDisposable = CompositeDisposable()


    fun bind() {
        compositeDisposable.add(
            searchUseCase.build(searchBodyMock()).subscribe({
                Timber.d(it.toString())
            }, {
                Timber.e(it)
            }, {

            }
            ))
    }

}