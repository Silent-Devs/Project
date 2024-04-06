package routes.web

import api.GeminiPro
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.html.*
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser
import utils.ConfigUtil

fun Route.studyPlanner() {
    val apiConfig = ConfigUtil.loadConfig().api.gemini
    val geminiPro = GeminiPro(apiConfig.url, apiConfig.key)

    route("/study-planner") {
        get {
            call.respondHtml {
                head {
                    title { +"Study Planner" }
                }
                body {
                    form(action = "/study-planner", method = FormMethod.post) {
                        textInput {
                            name = "description"
                            placeholder =
                                "Describe your subjects to study, tasks to complete, and any preferences (break time, etc.)"
                        }
                        select {
                            name = "first"
                            style = "display: none"
                            option { +"First" }
                        }
                        br
                        button {
                            type = ButtonType.submit
                            id = "submissionButton"
                            +"Submit"
                        }
                    }
                }
            }
        }

        post {
            val parameters = call.receiveParameters()
            val first = parameters["first"] ?: ""
            val description = parameters["description"] ?: ""
            if (first.equals("First") || first.isBlank()) {
                val response = geminiPro.getResponse(
                    message = description,
                    beforePrompt = "Hi, please generate a study plan in a day for me. Here is what I want to have:",
                    afterPrompt = "Please return in pure Markdown format with bullet lists. Thanks!"
                )
                val flavour = CommonMarkFlavourDescriptor()
                val parsedTree = MarkdownParser(flavour).buildMarkdownTreeFromString(response)
                val html = HtmlGenerator(response, parsedTree, flavour).generateHtml()
                call.respondHtml {
                    head {
                        title { +"Study Plan" }
                    }
                    body {
                        unsafe { raw(html) }
                        br {}
                        p {
                            +"If you like the study plan, save it to your device or print it out."
                            +" "
                            +"If you want to change something, tell the AI want do you want to change."
                        }
                        form(action = "/study-planner", method = FormMethod.post) {
                            textInput {
                                name = "description"
                                placeholder = "Tell the AI what do you want to change"
                            }
                            select {
                                name = "first"
                                style = "display: none"
                                option { +"Second" }
                            }
                            br
                            button {
                                type = ButtonType.submit
                                id = "submissionButton"
                                +"Submit"
                            }
                        }
                    }
                }
            } else {
                val response = geminiPro.getResponse(
                    message = description,
                    beforePrompt = "Hi, please change the study plan according to my request:",
                    afterPrompt = "Please return the new result in pure Markdown format with bullet lists. Thanks!"
                )
                val flavour = CommonMarkFlavourDescriptor()
                val parsedTree = MarkdownParser(flavour).buildMarkdownTreeFromString(response)
                val html = HtmlGenerator(response, parsedTree, flavour).generateHtml()
                call.respondHtml {
                    head {
                        title { +"Study Plan" }
                    }
                    body {
                        unsafe { raw(html) }
                        br {}
                        p {
                            +"If you are satisfied with the new study plan, save it to your device or print it out."
                            +" "
                            +"If you want to change something, you cal still tell the AI what do you want to change."
                        }
                        form(action = "/study-planner", method = FormMethod.post) {
                            textInput {
                                name = "description"
                                placeholder = "Tell the AI what do you want to change"
                            }
                            select {
                                name = "first"
                                style = "display: none"
                                option { +"Second" }
                            }
                            br
                            button {
                                type = ButtonType.submit
                                id = "submissionButton"
                                +"Submit"
                            }
                        }
                    }
                }
            }
        }
    }
}
