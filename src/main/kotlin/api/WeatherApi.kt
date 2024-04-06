package api

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import utils.ConfigUtil
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

    fun getRecommendation(): String {
        val forecast = getForecast()
        if (forecast == null) {
            return "Unable to get weather data"
        }

        val tempC = forecast.forecast.forecastday[0].day.avgTempC
        val windKph = forecast.forecast.forecastday[0].day.maxWindKph
        val totalPrecipMm = forecast.forecast.forecastday[0].day.totalPrecipMm
        val avgVisKm = forecast.forecast.forecastday[0].day.avgVisKm
        val avgHumidity = forecast.forecast.forecastday[0].day.avgHumidity
        val dailyWillItRain = forecast.forecast.forecastday[0].day.dailyWillItRain
        val dailyChanceOfRain = forecast.forecast.forecastday[0].day.dailyChanceOfRain
        val dailyWillItSnow = forecast.forecast.forecastday[0].day.dailyWillItSnow
        val dailyChanceOfSnow = forecast.forecast.forecastday[0].day.dailyChanceOfSnow
        val condition = forecast.forecast.forecastday[0].day.condition.text
        val uv = forecast.forecast.forecastday[0].day.uv

        val message = """
            Today's weather condition goes as follows:
            - Average Temperature: $tempC°C
            - Maximum Wind Speed: $windKph km/h
            - Total Precipitation: $totalPrecipMm mm
            - Average Visibility: $avgVisKm km
            - Average Humidity: $avgHumidity%
            - Will it rain today: ${if (dailyWillItRain == 1) "Yes" else "No"}
            - Chance of Rain: $dailyChanceOfRain%
            - Will it snow today: ${if (dailyWillItSnow == 1) "Yes" else "No"}
            - Chance of Snow: $dailyChanceOfSnow%
            - Condition: $condition
            - UV Index: $uv
            Can you give me some recommendations (on what to wear, what to bring, etc.) based on the weather condition?
        """.trimIndent()

        val geminiApiConfig = ConfigUtil.loadConfig().api.gemini
        val geminiPro = GeminiPro(
            url = geminiApiConfig.url,
            key = geminiApiConfig.key,
            prePrompt = """
                You have to return everything back to me in pure Markdown format.
                Use only normal text, bullet lists, and numbered lists.
                Bold, italic, strikethrough are supported. All other markdown syntax is not supported.
                Use long title and subtitles like "What you might need today".
                """
        )
        return geminiPro.getResponse(message)
    }
}

