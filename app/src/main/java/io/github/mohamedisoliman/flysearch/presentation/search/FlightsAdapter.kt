package io.github.mohamedisoliman.flysearch.presentation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.github.mohamedisoliman.flysearch.R
import io.github.mohamedisoliman.flysearch.domain.Flight
import io.github.mohamedisoliman.flysearch.presentation.search.FlightsAdapter.FLightViewHolder
import kotlinx.android.synthetic.main.item_flight.view.carrier_avatar
import kotlinx.android.synthetic.main.item_flight.view.carrier_name
import kotlinx.android.synthetic.main.item_flight.view.flight_price
import kotlinx.android.synthetic.main.item_flight.view.land_time
import kotlinx.android.synthetic.main.item_flight.view.take_off_time
import kotlinx.android.synthetic.main.item_flight.view.weight

/**
 *
 * Created by Mohamed Ibrahim on 4/21/19.
 */
class FlightsAdapter : RecyclerView.Adapter<FLightViewHolder>() {

  var data: List<Flight> = listOf()
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): FLightViewHolder {
    val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.item_flight, null, false)
    return FLightViewHolder(view)
  }

  override fun getItemCount(): Int = data.size

  override fun onBindViewHolder(
    holder: FLightViewHolder,
    position: Int
  ) {

    holder.bind(data[position])
  }

  class FLightViewHolder(private val itemFlightView: View) : RecyclerView.ViewHolder(
      itemFlightView
  ) {

    fun bind(flight: Flight) {
      itemFlightView.carrier_name.text = flight.carrierName
      itemFlightView.flight_price.text = flight.price
      itemFlightView.land_time.text = flight.landTime
      itemFlightView.take_off_time.text = flight.takeOffTime
      itemFlightView.weight.text = flight.weight
      Picasso.get()
          .load("https://cdn.fly365dev.com/airlines/${flight.carrierAvatar}.png")
          .into(itemFlightView.carrier_avatar)
    }

  }
}