package timifeoluwa.example.weatherapp.ui.weather.current

import androidx.lifecycle.ViewModel
import timifeoluwa.example.weatherapp.data.repository.WeatherRepository
import timifeoluwa.example.weatherapp.internal.UnitSystem
import timifeoluwa.example.weatherapp.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {


    val weather by lazyDeferred {

        weatherRepository.getCurrentWeather()

    }
    val weatherLocation by lazyDeferred {
        weatherRepository.getWeatherLocation()
    }


}