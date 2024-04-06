package routes.web

import api.ForecastJsonData
import api.GeminiPro
import com.beust.klaxon.Klaxon
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.*
import utils.ConfigUtil
import utils.HttpUtil

fun Route.index() {
    get("/") {
        call.respondHtml {
            head {
                title { +"ALL-IN-ONE-TOOL-BOX" }
                meta {charset="UTF-8" }
                link(rel = "stylesheet", href = "/css/style.css", type = "text/css")
                script { src = "/js/script.js"}
                meta {name="viewport"; content="width=device-width, initial-scale=1.0"}

            }
            body {
                div ("column circle"){
                    div("circle-container"){
                        onClick="javascript:window.location.href='https://www.youtube.com/watch?v=dQw4w9WgXcQ'"

                    }
                }
                div("column right"){
                    div ("board"){
                        h1 {
                            +"ALL IN ONE TOOL"
                        }
                        a {onClick="load_list()"
                            +"All tools"
                        }
                    }
                }
                div("main-interface"){
                    div("column tables"){
                        div ("links"){onClick="location='/study-planner'"
                            p {
                                +"Study Planner"
                            }
                        }
                        div ("links"){onClick="location='/academic-helper'"
                            p {
                                +"Academic Helper"
                            }
                        }
                        div ("links"){onClick="location='/passwordmanager'"
                            p {
                                +"Password Manager"
                            }
                        }
                        div ("links"){onClick="location='/to-do-list'"
                            p {
                                +"To do list"
                            }
                        }
                        div ("links"){onClick="location='/exam-prep'"
                            p {
                                +"Exam Preparation"
                            }
                        }
                    }
                    div("column weather"){
                        div("content"){
                            h2 {
                                +"Today: ${avgTemp} °C"
                            }
                            img { src="${conditionIcon}"
                                alt="${condition}"
                                width="50px"
                                height="60px" }
                        }

                    }


                }



            }
        }
    }
}
val apiConfig = ConfigUtil.loadConfig().api.weather
val weatherApi = WeatherApi(apiConfig.url, apiConfig.key)
val forecastData = weatherApi.getForecast()

val location = forecastData?.location?.name
val country = forecastData?.location?.country

val avgTemp = forecastData?.forecast?.forecastday?.get(0)?.day?.avgTempC
val condition = forecastData?.forecast?.forecastday?.get(0)?.day?.condition?.text
val conditionIcon = forecastData?.forecast?.forecastday?.get(0)?.day?.condition?.icon

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
