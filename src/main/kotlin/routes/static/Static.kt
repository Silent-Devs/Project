package routes.static

import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Route.static() {
    staticResources("/", "static")
}
