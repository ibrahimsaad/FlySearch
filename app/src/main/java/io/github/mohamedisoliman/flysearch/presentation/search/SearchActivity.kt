package io.github.mohamedisoliman.flysearch.presentation.search

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.github.mohamedisoliman.flysearch.R
import io.github.mohamedisoliman.flysearch.data.entities.suggestion.Airport
import io.github.mohamedisoliman.flysearch.presentation.clicksObservable
import io.github.mohamedisoliman.flysearch.presentation.mvi.MviView
import io.github.mohamedisoliman.flysearch.presentation.search.SearchIntents.ValidateInputs
import io.github.mohamedisoliman.flysearch.presentation.search.SearchViewState.IDLE
import io.github.mohamedisoliman.flysearch.presentation.search.suggestions.SuggestionsAdapter
import io.github.mohamedisoliman.flysearch.presentation.selectedItemObservable
import io.github.mohamedisoliman.flysearch.presentation.textChangeObservable
import io.github.mohamedisoliman.flysearch.presentation.textChangesObservable
import io.reactivex.Observable
import io.reactivex.functions.Function3
import kotlinx.android.synthetic.main.activity_main.departure_date
import kotlinx.android.synthetic.main.activity_main.destination_airport
import kotlinx.android.synthetic.main.activity_main.origin_airport
import kotlinx.android.synthetic.main.activity_main.pick_departure_date
import kotlinx.android.synthetic.main.fly_app_toolbar.search_flights
import kotlinx.android.synthetic.main.fly_app_toolbar.toolbar_progress
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Calendar

class SearchActivity : AppCompatActivity(), MviView<SearchIntents, SearchViewState> {

  private val searchViewModel: SearchViewModel by viewModel()
  private lateinit var suggestionsAdapter: SuggestionsAdapter
  private val datePickerDialog: DatePickerDialog by lazy { setupDatePicker() }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setupViews()
    searchViewModel.processIntents(intents())
    searchViewModel.states()
        .subscribe { render(it) }
  }

  private fun setupViews() {
    suggestionsAdapter = SuggestionsAdapter(this, R.layout.item_suggestion)
    origin_airport.setAdapter(suggestionsAdapter)
    origin_airport.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
        origin_airport.setText(suggestionsAdapter.data[position]?.fullName)
      }

    destination_airport.setAdapter(suggestionsAdapter)
    destination_airport.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
        destination_airport.setText(suggestionsAdapter.data[position]?.fullName)
      }
    pick_departure_date.setOnClickListener { datePickerDialog.show() }
  }

  override fun intents(): Observable<SearchIntents> {
    return Observable.merge<SearchIntents>(
        origin_airport.textChangesObservable().map<SearchIntents> {
          SearchIntents.LoadOriginSuggestions(it)
        },
        destination_airport.textChangesObservable().map<SearchIntents> {
          SearchIntents.LoadOriginSuggestions(it)
        },
        search_flights.clicksObservable().map<SearchIntents> {
          TODO()
        },
        validateInput()
    )
  }

  private fun validateInput(): Observable<ValidateInputs> {
    val combineLatest: Observable<ValidateInputs> = Observable.combineLatest(
        origin_airport.selectedItemObservable().startWith(Airport()),
        destination_airport.selectedItemObservable().startWith(Airport()),
        departure_date.textChangeObservable().startWith(""),
        Function3<Airport, Airport, String, ValidateInputs> { t1, t2, t3 ->
          ValidateInputs(t1, t2, t3)
        }
    )
    return combineLatest
  }

  override fun render(state: SearchViewState) {
    showLoading(false)
    showSearchActionView(false)
    when (state) {
      is SearchViewState.Suggestions -> suggestionsAdapter.data = state.suggestions
      is SearchViewState.ErrorHappened -> showError(state.errorMessage)
      is SearchViewState.SearchIsEnabled -> showSearchActionView(state.enabled)
      is SearchViewState.INFLIGHT -> showLoading(true)
      is IDLE -> showLoading(false)
    }

  }

  private fun showSearchActionView(show: Boolean) {
    search_flights.visibility = booleanToVisibilty(show)

  }

  private fun booleanToVisibilty(show: Boolean) = if (show) View.VISIBLE else View.GONE

  private fun showLoading(show: Boolean) {
    toolbar_progress.visibility = booleanToVisibilty(show)
  }

  private fun showError(errorMessage: String) {
    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG)
        .show()
  }

  private fun setupDatePicker(): DatePickerDialog {
    val now = Calendar.getInstance()
    val dpd = DatePickerDialog(
        this@SearchActivity,
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
          val calPicked = Calendar.getInstance()
          calPicked.set(year, month, dayOfMonth)
          val picked = SimpleDateFormat("yyyy-MM-dd")
          departure_date.text = picked.format(calPicked.time)
        },
        now.get(Calendar.YEAR),
        now.get(Calendar.MONTH),
        now.get(Calendar.DAY_OF_MONTH)
    )
    dpd.datePicker.minDate = System.currentTimeMillis() - 1000
    val cal = Calendar.getInstance()
    cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE))
    dpd.datePicker.maxDate = cal.timeInMillis
    return dpd
  }


}
