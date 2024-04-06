package routes.web

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*


import logic.ClipboardManager
import logic.PasswordManager

fun Route.modifyAccount() {
    route("/modifyaccount") {
        post {
            val parameters = call.receiveParameters()
            val password = parameters["passwordname"];
            val username = parameters["username"]
            val oldPassword = parameters["oldpassword"]
            val website = parameters["websitename"]
            println("\n\n\n\n\n")
            println(username)
            println(oldPassword)
            println(password)
            println(website)
            println("\n\n\n\n\n")
            ClipboardManager.copyPassword(password)
            PasswordManager.modifyAccount(website, username, oldPassword, password)
            call.respondRedirect("/passwordmanager")
        }
    }
}

fun Route.addAccount() {
    route("/addaccount") {
        post {
            val parameters = call.receiveParameters()
            val password = parameters["passwordname"];
            val username = parameters["username"]
            val website = parameters["websitename"]
            PasswordManager.addAccount(website, username, password)
            call.respondRedirect("/passwordmanager")
        }
    }
}

fun Route.deleteAccount() {
    route("/deleteaccount") {
        post {
            val parameters = call.receiveParameters()
            val username = parameters["username"]
            val website = parameters["websitename"]
            println("\n\n\n\n")
            println(username)
            println(website)
            println("\n\n\n")
            PasswordManager.deleteAccount(website, username)
            call.respondRedirect("/passwordmanager")
        }
    }
}

fun Click() {

}

fun Route.passwordManager() {
    get("/password-manager") {
        PasswordManager.updateAccounts("src/main/java/logic/database.json")
        println(PasswordManager.accountList.size)
        call.respondHtml {
            head {
                title { +"Password Manager" }
                link(rel = "stylesheet", href = "/css/password.css", type = "text/css")
            }
            body {
                h1 { +"Password Manager" }
                h2 { +"Your list of passwords" }
                table {
                    tr {
                        th { +"Password" }
                        th { +"Username" }
                        th { +"Website" }
                    }
                    for (n in PasswordManager.accountList) {
                        tr {
                            td { + n.websiteName }
                            td { + n.username }
                            td {
                                + n.password }
                            td {
                                /*form(action = "copypassword", method = FormMethod.post) {
                                    button {
                                        type = ButtonType.submit
                                        script {

                                        }
                                        +"Show password"
                                    }
                                }*/
                            }
                        }
                    }
                }

                form(action = "/addaccount", method = FormMethod.post) {
                    input {
                        name = "passwordname"
                        placeholder = "New password"
                        type= InputType.text
                    }
                    input {
                        name="websitename"
                        placeholder = "WEBSITE"
                    }
                    input {
                        name="username"
                        placeholder = "Username"
                    }
                    button() {
                        type = ButtonType.submit
                        +"Add account"
                    }
                }
                form(action = "/modifyaccount", method = FormMethod.post) {
                    input {
                        name = "passwordname"
                        placeholder = "New password"
                        type= InputType.text
                    }
                    input {
                        name="oldpassword"
                        placeholder = "Old password"
                        type=InputType.password
                    }
                    input {
                        name="websitename"
                        placeholder = "WEBSITE"
                    }
                    input {
                        name="username"
                        placeholder = "Username"
                    }
                    button {
                        type = ButtonType.submit
                        +"Change password"
                    }
                }

                form(action = "/deleteaccount", method = FormMethod.post) {
                    input {
                        name = "username"
                        placeholder = "Username"
                        type= InputType.text
                    }
                    input {
                        name="websitename"
                        placeholder = "Website"
                        type=InputType.text
                    }
                    button {
                        type = ButtonType.submit
                        +"Remove account"
                    }
                }
            }

        }
    }
}
