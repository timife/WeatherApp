package timifeoluwa.example.weatherapp.data.network

import androidx.lifecycle.LiveData
import timifeoluwa.example.weatherapp.data.db.entity.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        languageCode: String
    )
}