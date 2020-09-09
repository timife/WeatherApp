package timifeoluwa.example.weatherapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import timifeoluwa.example.weatherapp.StringListConverter
import timifeoluwa.example.weatherapp.data.db.entity.CurrentWeatherEntry
import timifeoluwa.example.weatherapp.data.db.entity.WeatherLocation
import timifeoluwa.example.weatherapp.data.db.unlocalized.WeatherLocationDao

@Database(
    entities = [CurrentWeatherEntry::class, WeatherLocation::class],
    version = 1
)
@TypeConverters(StringListConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun weatherLocationDao(): WeatherLocationDao

    companion object {
        @Volatile
        private var instance: WeatherDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                WeatherDatabase::class.java, "weather.db"
            )
                .build()
    }
}