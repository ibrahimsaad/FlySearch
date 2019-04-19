package io.github.mohamedisoliman.flysearch.domain

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class BaseUseCase<T, P>(
    private val subscribeOnScheduler: Scheduler = Schedulers.io(),
    private val observeOnScheduler: Scheduler = AndroidSchedulers.mainThread()
) {

    abstract fun buildUseCase(parameter: P): Observable<T>

    fun build(parameter: P): Observable<T> =
        buildUseCase(parameter)
            .subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)
}