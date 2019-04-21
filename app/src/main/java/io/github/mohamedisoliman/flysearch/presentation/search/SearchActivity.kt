package io.github.mohamedisoliman.flysearch.presentation.search

import android.R.layout
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay2.BehaviorRelay
import io.github.mohamedisoliman.flysearch.R
import io.github.mohamedisoliman.flysearch.data.entities.suggestion.Airport
import io.github.mohamedisoliman.flysearch.presentation.asVisibility
import io.github.mohamedisoliman.flysearch.presentation.clicksObservable
import io.github.mohamedisoliman.flysearch.presentation.mvi.MviView
import io.github.mohamedisoliman.flysearch.presentation.search.SearchIntents.ValidateInputs
import io.github.mohamedisoliman.flysearch.presentation.search.SearchViewState.ErrorHappened
import io.github.mohamedisoliman.flysearch.presentation.search.SearchViewState.FlightsResults
import io.github.mohamedisoliman.flysearch.presentation.search.SearchViewState.IDLE
import io.github.mohamedisoliman.flysearch.presentation.search.SearchViewState.INFLIGHT
import io.github.mohamedisoliman.flysearch.presentation.search.SearchViewState.Suggestions
import io.github.mohamedisoliman.flysearch.presentation.search.SearchViewState.Suggestions.Destination
import io.github.mohamedisoliman.flysearch.presentation.search.SearchViewState.Suggestions.Origin
import io.github.mohamedisoliman.flysearch.presentation.search.suggestions.SuggestionsAdapter
import io.github.mohamedisoliman.flysearch.presentation.selectedItemObservable
import io.github.mohamedisoliman.flysearch.presentation.textChangeObservable
import io.github.mohamedisoliman.flysearch.presentation.textChangesObservable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function7
import kotlinx.android.synthetic.main.activity_main.adult
import kotlinx.android.synthetic.main.activity_main.cabin_class
import kotlinx.android.synthetic.main.activity_main.child
import kotlinx.android.synthetic.main.activity_main.departure_date
import kotlinx.android.synthetic.main.activity_main.destination_airport
import kotlinx.android.synthetic.main.activity_main.flights_recycler
import kotlinx.android.synthetic.main.activity_main.infant
import kotlinx.android.synthetic.main.activity_main.origin_airport
import kotlinx.android.synthetic.main.activity_main.pick_departure_date
import kotlinx.android.synthetic.main.activity_main.search_button
import kotlinx.android.synthetic.main.fly_app_toolbar.toolbar_progress
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.ext.checkedStringValue
import java.text.SimpleDateFormat
import java.util.Calendar

class SearchActivity : AppCompatActivity(), MviView<SearchIntents, SearchViewState> {

  private val searchViewModel: SearchViewModel by viewModel()
  private lateinit var originSuggestionsAdapter: SuggestionsAdapter
  private lateinit var destinationSuggestionsAdapter: SuggestionsAdapter
  private lateinit var flightsAdapter: FlightsAdapter
  private val datePickerDialog: DatePickerDialog by lazy { setupDatePicker() }
  private val publishRelay = BehaviorRelay.create<ValidateInputs>()


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setupViews()
    searchViewModel.processIntents(intents())
    searchViewModel.states()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { render(it) }
    validateInput().subscribe(publishRelay)

  }

  override fun intents(): Observable<SearchIntents> {
    return Observable.merge<SearchIntents>(
        validateInput(),
        origin_airport.textChangesObservable().map<SearchIntents> {
          SearchIntents.LoadOriginSuggestions(it)
        },
        destination_airport.textChangesObservable().map<SearchIntents> {
          SearchIntents.LoadDestinationSuggestions(it)
        },
        search_button.clicksObservable()
            .filter { publishRelay.hasValue() }
            .flatMap { Observable.just(publishRelay.value) }
            .map { SearchIntents.SearchFlights(it.toSearchBody()) }
    )
  }

  private fun validateInput(): Observable<ValidateInputs> {
    return Observable.zip(
        origin_airport.selectedItemObservable(),
        destination_airport.selectedItemObservable(),
        departure_date.textChangeObservable(),
        adult.textChangeObservable().startWith("1"),
        infant.textChangeObservable().startWith("0"),
        child.textChangeObservable().startWith("0"),
        cabin_class.selectedItemObservable().startWith(cabin_class.selectedItem as String),
        Function7<Airport, Airport, String, String, String, String, String, ValidateInputs>
        { t1, t2, date, adult, infant, child, cabinclass ->
          ValidateInputs(t1, t2, date, adult, infant, child, cabinclass)
        }
    )
  }

  override fun render(state: SearchViewState) {
    when (state) {
      is Suggestions -> {
        showLoading(false)
        when (state) {
          is Origin -> originSuggestionsAdapter.data = state.data
          is Destination -> destinationSuggestionsAdapter.data = state.data
        }
      }
      is ErrorHappened -> {
        showLoading(false)
        showError(state.errorMessage)
      }
      is INFLIGHT -> showLoading(true)
      is IDLE -> {
        showLoading(false)
      }
      is FlightsResults -> {
        showLoading(false)
        flightsAdapter.data = state.flights
      }
    }.checkedStringValue()

  }


  private fun showLoading(show: Boolean) {
    toolbar_progress.visibility = show.asVisibility()
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

  private fun setupViews() {
    originSuggestionsAdapter = SuggestionsAdapter(this, R.layout.item_suggestion)
    origin_airport.setAdapter(originSuggestionsAdapter)
    origin_airport.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
      origin_airport.setText(originSuggestionsAdapter.data[position]?.fullName)
    }

    destinationSuggestionsAdapter = SuggestionsAdapter(this, R.layout.item_suggestion)
    destination_airport.setAdapter(destinationSuggestionsAdapter)
    destination_airport.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
      destination_airport.setText(destinationSuggestionsAdapter.data[position]?.fullName)
    }
    pick_departure_date.setOnClickListener { datePickerDialog.show() }

    val adapter = ArrayAdapter(
        this, layout.simple_spinner_item, listOf("Economy", "Premium", "Business", "First")
    )
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    cabin_class.adapter = adapter

    flightsAdapter = FlightsAdapter()
    flights_recycler.adapter = flightsAdapter
    flights_recycler.layoutManager = LinearLayoutManager(this)
    flights_recycler.isNestedScrollingEnabled = true
    flights_recycler.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
  }


}
