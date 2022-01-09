package com.example.weather2

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forcast)

        var retriever = WeatherRetriever()

        val callback = object : Callback<Weather> {
            override fun onResponse(call: Call<Weather>?, response: Response<Weather>?) {
                title = response?.body()?.location?.name
                println("Response working")
                var forecasts = response?.body()?.forecast?.forecastday

                var forecastStrings = mutableListOf<String>()

                if (forecasts != null)
                    for (forecast in forecasts) {
                        val newString =
                            "${forecast.date} - Nejvyšší teplota: ${forecast.day.maxtemp_c},  Nejnižší teplota: ${forecast.day.mintemp_c}," +
                                    "Max: ${forecast.day.maxtemp_c},  Průměrná teplota: ${forecast.day.avgtemp_c} Počasí: ${forecast.day.condition.text}   "
                        forecastStrings.add(newString)
                    }

                var listView = findViewById<ListView>(R.id.forcastListView)

                var adapter = ArrayAdapter(
                    this@ForecastActivity,
                    android.R.layout.simple_list_item_1,
                    forecastStrings
                )
                listView.adapter = adapter
            }

            override fun onFailure(call: Call<Weather>?, t: Throwable?) {
                println("No failed")
            }
        }

        var searchTerm = intent.extras?.getString("searchTerm")

        retriever.getForecast(callback, searchTerm!!)
    }
}
