package io.github.mohamedisoliman.flysearch.presentation.search

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.github.mohamedisoliman.flysearch.R
import io.github.mohamedisoliman.flysearch.presentation.mvi.MviView
import io.github.mohamedisoliman.flysearch.presentation.search.suggestions.SuggestionsAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.destination_airport
import kotlinx.android.synthetic.main.activity_main.origin_airport
import kotlinx.android.synthetic.main.activity_main.search_flights
import kotlinx.android.synthetic.main.fly_app_toolbar.toolbar_progress
import org.koin.android.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity(), MviView<SearchIntents, SearchViewState> {

  private val searchViewModel: SearchViewModel by viewModel()
  private lateinit var suggestionsAdapter: SuggestionsAdapter

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
    origin_airport.onItemClickListener =
      AdapterView.OnItemClickListener { _, _, position, _ ->
        origin_airport.setText(suggestionsAdapter.data[position]?.fullName)
      }

    destination_airport.setAdapter(suggestionsAdapter)
    destination_airport.onItemClickListener =
      AdapterView.OnItemClickListener { _, _, position, _ ->
        destination_airport.setText(suggestionsAdapter.data[position]?.fullName)
      }

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
          SearchIntents.SearchFlights(
              "", "", ""
          )/*TODO*/
        }
    )
  }

  override fun render(state: SearchViewState) {
    hidLoading()
    when (state) {
      is SearchViewState.Suggestions -> suggestionsAdapter.data = state.suggestions
      is SearchViewState.ErrorHappened -> showError(state.errorMessage)
      is SearchViewState.INFLIGHT -> showLoading()
    }

  }

  private fun hidLoading() {
    toolbar_progress.visibility = View.GONE
  }

  private fun showLoading() {
    toolbar_progress.visibility = View.VISIBLE
  }

  private fun showError(errorMessage: String) {
    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG)
        .show()
  }

}
