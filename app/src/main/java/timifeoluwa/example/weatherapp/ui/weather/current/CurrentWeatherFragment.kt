package timifeoluwa.example.weatherapp.ui.weather.current

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_weather_current.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import timifeoluwa.example.weatherapp.R
import timifeoluwa.example.weatherapp.ui.base.ScopedFragment

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()

    private lateinit var currentWeatherViewModel: CurrentWeatherViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather_current, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        currentWeatherViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {
        val currentWeather = currentWeatherViewModel.weather.await()

        val weatherLocation = currentWeatherViewModel.weatherLocation.await()

        weatherLocation.observe(this@CurrentWeatherFragment, Observer { location ->
            if (location == null) return@Observer
            updateLocation(location.name!!)

        })

        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            if (it == null) return@Observer

            group_loading.visibility = View.GONE
            updateDateToToday()
            updateTemperatures(it.temperature!!, it.feelslike!!)
            updatePrecipitation(it.precip!!)
            updateWind(it.windDir!!, it.windSpeed!!)
            updateVisibility(it.visibility!!)
            updateDescriptions(it.weatherDescriptions?.get(0))

            Glide.with(this@CurrentWeatherFragment)
                .load("${it.weatherIcons?.get(0)}")
                .into(imageView_condition_icon)

        })
    }

    private fun updateLocation(location: String) {
        textView_location.text = location
    }

    private fun updateDateToToday() {
        textView_date.text = "Today"
    }

    @SuppressLint("SetTextI18n")
    private fun updateTemperatures(temperature: Double, feelsLike: Double) {
        textView_temperature.text = "$temperature\u2103"
        textView_feels_like.text = "Feels like $feelsLike\u2103"
    }

    private fun updateDescriptions(description: String?) {
        textView_description.text = "$description"
    }

    @SuppressLint("SetTextI18n")
    private fun updatePrecipitation(precip: Double) {
        textView_precipitation.text = "Precipitation: $precip mm"
    }

    @SuppressLint("SetTextI18n")
    private fun updateWind(windDir: String, windSpeed: Double) {
        textView_wind.text = "WindDirection: $windDir , $windSpeed kph"
    }

    @SuppressLint("SetTextI18n")
    private fun updateVisibility(visibility: Double) {
        textView_visibility.text = "Visibility: $visibility km"
    }
}