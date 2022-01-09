package com.example.weather2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var newForcast = findViewById<Button>(R.id.getForecastButton)

        newForcast.setOnClickListener {
            var moveToForecast = Intent(this, ForecastActivity::class.java)
            val searchEditText = findViewById<EditText>(R.id.searchEditText)
            moveToForecast.putExtra("searchTerm", searchEditText.text.toString())
            startActivity(moveToForecast)

        }
    }
}