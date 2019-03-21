package com.pedro.mapsandroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btMapIntent.setOnClickListener {
            startActivity(Intent(this, MapByIntentActivity::class.java))
        }

        btMapApp.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))

        }
    }
}
