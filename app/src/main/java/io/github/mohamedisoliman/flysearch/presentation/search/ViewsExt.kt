package io.github.mohamedisoliman.flysearch.presentation.search

import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.widget.doAfterTextChanged
import io.reactivex.Observable

/**
 *
 * Created by Mohamed Ibrahim on 4/20/19.
 */

fun AppCompatAutoCompleteTextView.textChangesObservable(): Observable<String> {

  return Observable.create { emitter ->
    val doAfterTextChanged = doAfterTextChanged {
      emitter.onNext(it.toString())
    }
    emitter.setCancellable {
      removeTextChangedListener(doAfterTextChanged)
    }
  }
}

fun AppCompatImageButton.clicksObservable(): Observable<String> {
  return Observable.create { emitter ->
    setOnClickListener { emitter.onNext("ignored") }
    emitter.setCancellable { setOnClickListener(null) }
  }
}