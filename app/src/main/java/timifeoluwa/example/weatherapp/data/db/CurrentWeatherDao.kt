package timifeoluwa.example.weatherapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import timifeoluwa.example.weatherapp.data.db.entity.CURRENT_WEATHER_ID
import timifeoluwa.example.weatherapp.data.db.entity.CurrentWeatherEntry

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(currentWeatherEntry: CurrentWeatherEntry)

    @Query("select * from current_weather where id =  $CURRENT_WEATHER_ID")
    fun getWeatherEntry(): LiveData<CurrentWeatherEntry>

}

