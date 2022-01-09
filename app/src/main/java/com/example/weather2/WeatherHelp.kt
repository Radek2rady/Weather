package com.example.weather2

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("forecast.json?key=6560e1285a4640ff82172941212911&days=10aqi=no&alerts=no")
    fun getForecast(@Query("q") q: String): Call<Weather>
}

class Weather(val location: WeatherLocation, val forecast: WeatherForecast)
class WeatherLocation(val name: String)
class WeatherForecast(val forecastday: List<WeatherForecastDay>)
class WeatherForecastDay(val date: String, val day: Forecast)

class Forecast(
    val avgtemp_c: Double,
    val maxtemp_c: Double,
    val mintemp_c: Double,
    val condition: TextForecast
)

class TextForecast(val text: String)

class WeatherRetriever {
    val service: WeatherAPI

    init {
        val retrofit =
            Retrofit.Builder().baseUrl("http://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        service = retrofit.create(WeatherAPI::class.java)
    }

    fun getForecast(callback: Callback<Weather>, searchTerm: String) {
        var searchTermVar = searchTerm
        if (searchTerm == "") {
            searchTermVar = "Honolulu"
        } else if (searchTerm == "Praha") {
            searchTermVar = "Praha CZ"
        }

        val q = "$searchTermVar"
        val call = service.getForecast(q)
        call.enqueue(callback)
    }
}
