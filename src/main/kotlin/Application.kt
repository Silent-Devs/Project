import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.doublereceive.*
import plugins.configureRouting
import utils.ConfigUtil

fun main() {
    val ktorConfig = ConfigUtil.loadConfig().ktor
    embeddedServer(Netty, port = ktorConfig.port, host = ktorConfig.host, module = Application::module)
        .start(wait = true)

}

fun Application.module() {
    install(DoubleReceive) {
        cacheRawRequest = false
    }
    configureRouting()
}
