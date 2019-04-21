package io.github.mohamedisoliman.flysearch.presentation

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import io.github.mohamedisoliman.flysearch.data.entities.suggestion.Airport
import io.github.mohamedisoliman.flysearch.presentation.search.suggestions.SuggestionsAdapter
import io.reactivex.Observable
import java.text.SimpleDateFormat

/**
 *
 * Created by Mohamed Ibrahim on 4/20/19.
 */

const val BODY_DATE_FORMAT = "yyyy-MM-dd"

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

fun AppCompatAutoCompleteTextView.selectedItemObservable(): Observable<Airport> {
  return Observable.create { emitter ->
    onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
      val airport = (adapter as SuggestionsAdapter).data[position]
      airport?.let {
        this.setText(it.fullName)
        emitter.onNext(it)
      }
    }
    emitter.setCancellable { onItemClickListener = null }
  }
}

fun TextView.textChangeObservable(): Observable<String> {
  return Observable.create { emitter ->
    val doAfterTextChanged = doAfterTextChanged {
      emitter.onNext(it.toString())
    }
    emitter.setCancellable {
      removeTextChangedListener(doAfterTextChanged)
    }
  }
}

fun LinearLayout.clicksObservable(): Observable<String> {
  return Observable.create { emitter ->
    setOnClickListener { emitter.onNext("ignored") }
    emitter.setCancellable { setOnClickListener(null) }
  }
}

fun String.isAFormattedDate(format: String): Boolean {
  val dateFormat = SimpleDateFormat(format)
  dateFormat.isLenient = false
  try {
    dateFormat.parse(this.trim())
  } catch (pe: Exception) {
    return false
  }

  return true
}

fun MaterialButton.clicksObservable(): Observable<String> {
  return Observable.create { emitter ->
    setOnClickListener { emitter.onNext("ignored") }
    emitter.setCancellable { setOnClickListener(null) }
  }
}

fun TextInputEditText.textChangeObservable(): Observable<String> {
  return Observable.create { emitter ->
    val doAfterTextChanged = doAfterTextChanged {
      emitter.onNext(it.toString())
    }
    emitter.setCancellable {
      removeTextChangedListener(doAfterTextChanged)
    }
  }
}

fun AppCompatSpinner.selectedItemObservable(): Observable<String> {
  return Observable.create { emitter ->
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onNothingSelected(parent: AdapterView<*>?) {

      }

      override fun onItemSelected(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
      ) {
        val arrayAdapter = adapter as ArrayAdapter<String>
        emitter.onNext(arrayAdapter.getItem(position))
      }
    }
  }
}

fun Boolean.asVisibility() = if (this) View.VISIBLE else View.GONE