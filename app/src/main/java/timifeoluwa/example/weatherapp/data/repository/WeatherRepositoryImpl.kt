package timifeoluwa.example.weatherapp.data.repository

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timifeoluwa.example.weatherapp.data.db.CurrentWeatherDao
import timifeoluwa.example.weatherapp.data.db.entity.CurrentWeatherEntry
import timifeoluwa.example.weatherapp.data.db.entity.CurrentWeatherResponse
import timifeoluwa.example.weatherapp.data.db.entity.WeatherLocation
import timifeoluwa.example.weatherapp.data.db.unlocalized.WeatherLocationDao
import timifeoluwa.example.weatherapp.data.network.WeatherNetworkDataSource
import timifeoluwa.example.weatherapp.data.provider.LocationProvider
import java.time.ZonedDateTime
import java.util.*

class WeatherRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
) : WeatherRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)

        }
    }

    override suspend fun getCurrentWeather(): LiveData<out CurrentWeatherEntry> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext currentWeatherDao.getWeatherEntry()

        }
    }

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO) {
            return@withContext weatherLocationDao.getLocation()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            fetchedWeather.currentWeatherEntry?.let { currentWeatherDao.upsert(it) }
            fetchedWeather.weatherLocation?.let { weatherLocationDao.upsert(it) }
        }
    }

    @SuppressLint("NewApi")
    private suspend fun initWeatherData() {
        val lastWeatherLocation = weatherLocationDao.getLocation().value

        if (lastWeatherLocation == null || locationProvider.hasLocationChanged(lastWeatherLocation)) {
            fetchCurrentWeather()
            return
        }

        if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()

    }

    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocationString(),
            Locale.getDefault().language
        )
    }

    @SuppressLint("NewApi")
    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)

    }
}