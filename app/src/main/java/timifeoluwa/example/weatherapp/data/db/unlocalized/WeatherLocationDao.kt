package timifeoluwa.example.weatherapp.data.db.unlocalized

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import timifeoluwa.example.weatherapp.data.db.entity.WEATHER_LOCATION_ID
import timifeoluwa.example.weatherapp.data.db.entity.WeatherLocation


@Dao
interface WeatherLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherLocation: WeatherLocation)

    @Query("select * from weather_location where id = $WEATHER_LOCATION_ID")
    fun getLocation(): LiveData<WeatherLocation>
}