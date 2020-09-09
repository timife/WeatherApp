package timifeoluwa.example.weatherapp.data.db.entity


import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry?,
    val weatherLocation: WeatherLocation?,
    val request: Request?
)