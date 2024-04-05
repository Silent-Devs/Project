package routes.web

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.*

fun Route.index() {
    get("/") {
        call.respondHtml {
            head {
                title { +"Ktor Server" }
            }
            body {
                p { +"Hello, Ktor!" }
            }
        }
    }
}
