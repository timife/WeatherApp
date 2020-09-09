package timifeoluwa.example.weatherapp.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import timifeoluwa.example.weatherapp.data.repository.WeatherRepository

class CurrentWeatherViewModelFactory(
    private val weatherRepository: WeatherRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(weatherRepository) as T
    }
}