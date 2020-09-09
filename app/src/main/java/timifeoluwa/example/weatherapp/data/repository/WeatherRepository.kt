package timifeoluwa.example.weatherapp.data.repository

import androidx.lifecycle.LiveData
import timifeoluwa.example.weatherapp.data.db.entity.CurrentWeatherEntry
import timifeoluwa.example.weatherapp.data.db.entity.WeatherLocation

interface WeatherRepository {

    suspend fun getCurrentWeather(): LiveData<out CurrentWeatherEntry>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>

}