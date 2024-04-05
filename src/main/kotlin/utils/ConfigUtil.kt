package utils

import com.sksamuel.hoplite.ConfigLoader

class ConfigUtil {
    data class Config(
        val api: ApiConfig
    )

    data class ApiConfig(
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
