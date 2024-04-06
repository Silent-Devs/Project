package utils

import com.sksamuel.hoplite.ConfigLoader

class ConfigUtil {
    data class Config(
        val ktor: KtorConfig,
        val api: ApiConfig
    )

    data class KtorConfig(
        val host: String,
        val port: Int
    )

    data class ApiConfig(
        val gemini: GeminiConfig,
        val weather: WeatherApiConfig
    )

    data class GeminiConfig(
        val url: String,
        val key: String
    )

    data class WeatherApiConfig(
        val url: String,
        val key: String
    )

    companion object {
        fun loadConfig(): Config {
            val pwd = System.getProperty("user.dir")
            return ConfigLoader().loadConfigOrThrow("$pwd/conf/config.yaml")
        }
    }
}
