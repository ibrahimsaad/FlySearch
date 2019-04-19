package io.github.mohamedisoliman.flysearch

import androidx.annotation.VisibleForTesting
import com.squareup.moshi.Moshi
import io.github.mohamedisoliman.flysearch.data.entities.SearchBody


/**
 *
 * Created by Mohamed Ibrahim on 4/19/19.
 */
@VisibleForTesting
fun searchBodyMock(): SearchBody {


    val json = """
        {
        "legs": [
             {
            "origin": "SYD",
            "destination": "DXB",
            "departureDate": "2019-04-21"
            }
        ],
        "cabinClass": "Economy",
        "infant": 0,
        "child": 0,
        "adult": 1
}
    """.trimIndent()
    val moshi = Moshi.Builder().build()
    val jsonAdapter = moshi.adapter<SearchBody>(SearchBody::class.java)

    return jsonAdapter.fromJson(json)
}