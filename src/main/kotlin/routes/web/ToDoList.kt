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


fun Route.toDoList() {
    get("/to-do-list") {
        call.respondHtml {
            head {
                title { +"To do list" }
            }
            body {
                div {
                    id = "calendar"
                }
                div {
                    button {
                        id = "add-event-button"
                        +"Add Event"
                        onClick = "location='/to-do-list/add-event'"
                    }
                }
                div {
                    button {
                        id = "change-subject-button"
                        +"Change Subject"
                        onClick = "location='/to-do-list/change-subject'"
                    }
                }
            }
        }
    }

    post("/to-do-list/add-event") {
        val toDoListDTO = call.receive<ToDoListDto>()
        val toDoList = getToDoList()
        val eventDTO = toDoListDTO.events.find { it.name == "newEvent" }
        if (eventDTO != null) {
            val event = Event(
                eventDTO.name,
                eventDTO.eventDate,
                eventDTO.checked,
                eventDTO.priority,
                eventDTO.subject
            )
            toDoList.addEvent(event)
            setToDoList(toDoList)
            call.respond(HttpStatusCode.Created)
        } else {
            call.respond(HttpStatusCode.BadRequest)
        }
    }

    post("/to-do-list/add-subject") {
        val name = call.parameters["name"] ?: ""
        val subject = call.parameters["subject"] ?: ""
        val toDoList = getToDoList()
        toDoList.addSubject(name, subject)
        setToDoList(toDoList)
        call.respond(HttpStatusCode.Created)
    }

    post("/to-do-list/remove-subject") {
        val name = call.parameters["name"] ?: ""
        val toDoList = getToDoList()
        toDoList.removeSubject(name)
        setToDoList(toDoList)
        call.respond(HttpStatusCode.Created)
    }
}

fun getToDoList(): ToDoListDto {
    return ToDoListDto()
}

fun setToDoList(toDoListDto: ToDoListDto) {
    val toDoList = getToDoList()
    toDoList.date = toDoListDto.date
    toDoList.events = toDoListDto.events
}
