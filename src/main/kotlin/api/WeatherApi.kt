package api

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import utils.HttpUtil

data class ConditionData(
    @Json(name = "text")
    val text: String?,
    @Json(name = "icon")
    val icon: String?,
    @Json(name = "code")
    val code: Int?
)

data class DayData(
    @Json(name = "maxtemp_c")
    val maxTempC: Double?,
    @Json(name = "maxtemp_f")
    val maxTempF: Double?,
    @Json(name = "mintemp_c")
    val minTempC: Double?,
    @Json(name = "mintemp_f")
    val minTempF: Double?,
    @Json(name = "avgtemp_c")
    val avgTempC: Double?,
    @Json(name = "avgtemp_f")
    val avgTempF: Double?,
    @Json(name = "maxwind_mph")
    val maxWindMph: Double?,
    @Json(name = "maxwind_kph")
    val maxWindKph: Double?,
    @Json(name = "totalprecip_mm")
    val totalPrecipMm: Double?,
    @Json(name = "totalprecip_in")
    val totalPrecipIn: Double?,
    @Json(name = "avgvis_km")
    val avgVisKm: Double?,
    @Json(name = "avgvis_miles")
    val avgVisMiles: Double?,
    @Json(name = "avghumidity")
    val avgHumidity: Int?,
    @Json(name = "daily_will_it_rain")
    val dailyWillItRain: Int?,
    @Json(name = "daily_chance_of_rain")
    val dailyChanceOfRain: Int?,
    @Json(name = "daily_will_it_snow")
    val dailyWillItSnow: Int?,
    @Json(name = "daily_chance_of_snow")
    val dailyChanceOfSnow: Int?,
    @Json(name = "condition")
    val condition: ConditionData,
    @Json(name = "uv")
    val uv: Double?
)

data class ForecastDayData(
    @Json(name = "date")
    val date: String?,
    @Json(name = "date_epoch")
    val dateEpoch: Long?,
    @Json(name = "day")
    val day: DayData,
)

data class LocationData(
    @Json(name = "name")
    val name: String?,
    @Json(name = "region")
    val region: String?,
    @Json(name = "country")
    val country: String?,
    @Json(name = "lat")
    val lat: Double?,
    @Json(name = "lon")
    val lon: Double?,
    @Json(name = "tz_id")
    val tzId: String?,
    @Json(name = "localtime_epoch")
    val localtimeEpoch: Long?,
    @Json(name = "localtime")
    val localtime: String?
)

data class CurrentData(
    @Json(name = "last_updated_epoch")
    val lastUpdatedEpoch: Long?,
    @Json(name = "last_updated")
    val lastUpdated: String?,
    @Json(name = "temp_c")
    val tempC: Double?,
    @Json(name = "temp_f")
    val tempF: Double?,
    @Json(name = "is_day")
    val isDay: Int?,
    @Json(name = "condition")
    val condition: ConditionData,
    @Json(name = "wind_mph")
    val windMph: Double?,
    @Json(name = "wind_kph")
    val windKph: Double?,
    @Json(name = "wind_degree")
    val windDegree: Int?,
    @Json(name = "wind_dir")
    val windDir: String?,
    @Json(name = "pressure_mb")
    val pressureMb: Double?,
    @Json(name = "pressure_in")
    val pressureIn: Double?,
    @Json(name = "precip_mm")
    val precipMm: Double?,
    @Json(name = "precip_in")
    val precipIn: Double?,
    @Json(name = "humidity")
    val humidity: Int?,
    @Json(name = "cloud")
    val cloud: Int?,
    @Json(name = "feelslike_c")
    val feelslikeC: Double?,
    @Json(name = "feelslike_f")
    val feelslikeF: Double?,
    @Json(name = "vis_km")
    val visKm: Double?,
    @Json(name = "vis_miles")
    val visMiles: Double?,
    @Json(name = "uv")
    val uv: Double?,
    @Json(name = "gust_mph")
    val gustMph: Double?,
    @Json(name = "gust_kph")
    val gustKph: Double?
)

data class ForecastData(
    @Json(name = "forecastday")
    val forecastday: List<ForecastDayData>
)

data class ForecastJsonData(
    @Json(name = "location")
    val location: LocationData,
    @Json(name = "current")
    val current: CurrentData,
    @Json(name = "forecast")
    val forecast: ForecastData
)

class WeatherApi(private val baseUrl: String, private val key: String) {
    fun getForecast(): ForecastJsonData? {
        val url = "$baseUrl/forecast.json?key=$key&q=auto:ip"
        val response = HttpUtil.PostRequests.body(url)
        return Klaxon().parse<ForecastJsonData>(response)
    }
}
