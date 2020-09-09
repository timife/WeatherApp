package timifeoluwa.example.weatherapp.data.db.entity


import android.annotation.SuppressLint
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

const val WEATHER_LOCATION_ID = 0

@Entity(tableName = "weather_location")
data class WeatherLocation(
    val country: String?,
    val lat: Double?,
    val localtime: String?,
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Long?,
    val lon: Double?,
    val name: String?,
    val region: String?,
    @SerializedName("timezone_id")
    val timezoneId: String?,
    @SerializedName("utc_offset")
    val utcOffset: String?
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = WEATHER_LOCATION_ID

    val zonedDateTime: ZonedDateTime  //getting a unified time zone without comparison.
        @SuppressLint("NewApi")
        get() {
            val instant = Instant.ofEpochSecond(this!!.localtimeEpoch!!)
            val zoneId = ZoneId.of(timezoneId)
            return ZonedDateTime.ofInstant(instant, zoneId)
        }
}