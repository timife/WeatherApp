package timifeoluwa.example.weatherapp.data.provider

import timifeoluwa.example.weatherapp.data.db.entity.WeatherLocation

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
    suspend fun getPreferredLocationString(): String
}