package io.github.mohamedisoliman.flysearch.presentation.search.suggestions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import io.github.mohamedisoliman.flysearch.R
import io.github.mohamedisoliman.flysearch.data.entities.suggestion.Airport

/**
 *
 * Created by Mohamed Ibrahim on 4/20/19.
 */
class SuggestionsAdapter(
  context: Context,
  private val resource: Int
) : ArrayAdapter<Airport?>(context, resource) {

  var data: List<Airport?> = mutableListOf()
    set(value) {
      field = value
      if (value.isNotEmpty()) {
        notifyDataSetChanged()
      }
    }

  override fun getCount(): Int {
    return data.size
  }

  override fun getItem(position: Int): Airport? {
    return data[position]
  }

  override fun getView(
    position: Int,
    convertView: View?,
    parent: ViewGroup
  ): View {
    val view = LayoutInflater.from(context)
        .inflate(resource, parent, false)
    view.findViewById<TextView>(R.id.city)
        .text = data[position]?.fullName
    return view
  }

}