package io.github.mohamedisoliman.flysearch.presentation.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.mohamedisoliman.flysearch.R
import org.koin.android.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    private val searchViewModel by viewModel<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}
