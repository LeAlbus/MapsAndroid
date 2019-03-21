package com.pedro.mapsandroid

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_map_by_intent.*

class MapByIntentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_by_intent)

        btShowLocal.setOnClickListener {

            val coordinates = "-23.5565804, -46.662113"
            val zoom = 15

            val geo = "geo:$coordinates?z=$zoom"

            this.showOnMap(geo)
        }

        btShowRestaurants.setOnClickListener {

            val query = "restaurants"
            val geo = "0,0?q=$query"

            this.showOnMap(geo)
        }

        btShowRoute.setOnClickListener {

            val adrs = "Rua Alba, 2140, São Paulo, Brasil"
            var location = Uri.encode(adrs)
            var mapMode = "w"
            val geo = "google.navigation:q=$location&mode=$mapMode"

            this.showOnMap(geo)
        }
    }

    fun showOnMap(geo: String) {

        val geoURI = Uri.parse(geo)
        val intent = Intent(Intent.ACTION_VIEW, geoURI)
        intent.setPackage("com.google.android.apps.maps")
        startActivity(intent)

    }
}
