package routes.web

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.registerWebRoutes() {
    routing {
        index()
        studyPlanner()
        passwordManager()
        academicHelper()
        examPreperation()
        toDoList()
        modifyAccount()
        addAccount()
        deleteAccount()
    }
}
