package routes.web

import dto.Event
import dto.ToDoListDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*

fun Route.addEvent() {
    route("/addevent") {
        post {
            val parameters = call.receiveParameters();
            val name = parameters["eventname"];
            val date = parameters["eventdate"];
            val subject = parameters["eventsubject"];

            val c1 = Event(name, Event.stringToDate(date), false, 1, subject);
            ToDoListDto.addEvent(c1)
            call.respondRedirect("/to-do-list")

        }
    }
}

fun Route.toDoList() {

    get("/to-do-list") {
        call.respondHtml {
            head {
                title { +"To do list" }
                link(rel = "stylesheet", href = "/css/todolist.css", type = "text/css")
            }
            body {
                h1 {
                    +"To do list"
                }
                ul {
                    ul {
                        for (event in ToDoListDto.events) {
                            li {
                                +event.name
                            }
                            li {
                                +event.subject
                            }
                            li {
                                input {
                                    type=InputType.checkBox
                                    checked=event.checked
                                }
                            }
                        }
                    }
                }

                form(action = "/addevent", method = FormMethod.post) {
                    input {
                        name = "eventname"
                        placeholder = "Event name"
                        type= InputType.text
                    }
                    input {
                        name="eventdate"
                        placeholder = "Date (yyyy-mm-dd HH:mm)"
                    }
                    input {
                        name="eventsubject"
                        placeholder = "Subject"
                        type = InputType.text
                    }
                    button() {
                        type = ButtonType.submit
                        +"Add event"
                    }
                }
            }
        }
    }


}

