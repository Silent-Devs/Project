package routes.web

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.body
import kotlinx.html.head
import kotlinx.html.title

fun Route.Passwordmanager(){
    get("/passwordmanager") {
        call.respondHtml {
            head {
                title { +"Password Manager" }
            }
            body {

            }

        }

}

}