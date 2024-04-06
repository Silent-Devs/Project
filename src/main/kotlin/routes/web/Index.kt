package routes.web
import api.WeatherApi
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*
import utils.ConfigUtil
import utils.MarkdownUtil

fun Route.index() {
    val apiConfig = ConfigUtil.loadConfig().api.weather
    val weatherApi = WeatherApi(apiConfig.url, apiConfig.key)
    get("/") {
        val weather = weatherApi.getForecast()

        val city = weather?.location?.name
        val region = weather?.location?.region
        val country = weather?.location?.country

        val avgTemp = weather?.forecast?.forecastday?.get(0)?.day?.avgTempC
        val condition = weather?.forecast?.forecastday?.get(0)?.day?.condition?.text
        val conditionIcon = weather?.forecast?.forecastday?.get(0)?.day?.condition?.icon

        call.respondHtml {
            head {
                title { +"StudyBuddy" }
                meta { charset = "UTF-8" }
                link(rel = "stylesheet", href = "/css/style.css", type = "text/css")
                script { src = "/js/script.js" }
                meta { name = "viewport"; content = "width=device-width, initial-scale=1.0" }

            }
            body {
                div("column circle") {
                    div("circle-container") {
                        onClick = "javascript:window.location.href='https://www.youtube.com/watch?v=dQw4w9WgXcQ'"

                    }
                }

                div("column right") {
                    div("board") {
                        h1 {
                            +"StudyBuddy"
                        }
                        h3 {
                            +"All In One Study Companion"
                        }
                        a {
                            onClick = "load_list()"
                            +"All tools"
                        }
                    }
                }

                div("main-interface") {
                    div("column tables") {
                        div("links") {
                            onClick = "location='/study-planner'"
                            p {
                                +"Study Planner"
                            }
                        }
                        div("links") {
                            onClick = "location='/academic-helper'"
                            p {
                                +"Academic Helper"
                            }
                        }
                        div("links") {
                            onClick = "location='/password-manager'"
                            p {
                                +"Password Manager"
                            }
                        }
                        div("links") {
                            onClick = "location='/to-do-list'"
                            p {
                                +"To do list"
                            }
                        }
                        div("links") {
                            onClick = "location='/exam-prep'"
                            p {
                                +"Exam Preparation"
                            }
                        }
                    }
                    div("column weather") {
                        div("content") {
                            h2 {
                                +"$city, $region, $country"
                            }
                            h2 {
                                +"Today: $avgTemp °C"
                            }
                            img {
                                src = "$conditionIcon"
                                alt = "$condition"
                                width = "50px"
                                height = "60px"
                            }
                        }
                    }
                }
            }
        }
    }
}

