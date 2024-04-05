package plugins

import io.ktor.server.application.*
import routes.static.registerStaticRoutes
import routes.web.registerWebRoutes

fun Application.configureRouting() {
    registerWebRoutes()
    registerStaticRoutes()
}
