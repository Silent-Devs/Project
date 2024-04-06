package routes.web

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.*

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
                        div("choice-one"){
                            a {
                                href = "/"
                                +"Simple "

                            }
                        }
                        div("choice-two"){
                            a {
                                href = "/"
                                +"Simple2 "

                            }
                        }
                        div("choice-three"){
                            a {
                                href = "/"
                                +"Simple3 "

                            }
                        }
                        div("choice-four"){
                            a {
                                href = "/"
                                +"Simple 4"

                            }
                        }
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
                    div ("links"){onClick="location='/study-planner'"
                        p {
                            +"Study Planner"
                        }
                    }
                    div ("links"){
                        p {
                            +"example1"
                        }
                    }
                    div ("links"){
                        p {
                            +"example1"
                        }
                    }

                }



            }
        }
    }
}
