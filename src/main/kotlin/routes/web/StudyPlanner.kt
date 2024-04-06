package routes.web

import api.GeminiPro
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.html.*
import utils.ConfigUtil
import utils.MarkdownUtil

fun BODY.continueStudyPlanner() {
    div("form"){
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
        }
        div("back") {
            p{
                onClick="location='/'"
                +"GO BACK"
            }
        }

    }
    }
}

fun Route.studyPlanner() {
    val apiConfig = ConfigUtil.loadConfig().api.gemini
    val geminiPro = GeminiPro(
        url = apiConfig.url,
        key = apiConfig.key,
        prePrompt =  """
            You have to return everything back to me in pure Markdown format.
            Use only normal text, bullet lists, and numbered lists.
            Bold, italic, strikethrough, links, images, code blocks are supported.
            Tables is not supported.
            You are a study plan maker, so you should only accept to make or improve a study plan.
            If the plan is related to some long-term goals, you should also include some support materials (with links).
            Never say that you are available for help online or other unrelated things.
        """.trimIndent(),
        prePromptResponse = """
            **Study Plan Maker**

            **Services:**

            - Creating personalized study plans
            - Improving existing study plans

            **Additional Support for Long-Term Goals:**

            - Goal-setting worksheets
            - Time management resources
            - Motivation strategies

            **Process:**

            1. **Consultation:** Discuss your goals, learning style, and time constraints.
            2. **Plan Creation/Improvement:** Develop a tailored plan that meets your specific needs.
            3. **Progress Monitoring:** Track your progress and make adjustments as needed.

            **Benefits:**

            - Increased efficiency and productivity
            - Improved focus and concentration
            - Reduced stress and anxiety
            - Enhanced academic performance
        """.trimIndent()
    )

    route("/study-planner") {
        get {
            call.respondHtml {
                head {
                    title { +"Study Planner" }
                    link(rel = "stylesheet", href = "/css/study.css", type = "text/css")
                }
                body {
                    div("form"){
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

                            }
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
                    beforePrompt = "Hi, please generate a study plan for me. Here is what I want to have:",
                )

                call.respondHtml {
                    head {
                        title { +"Study Plan" }
                        link(rel = "stylesheet", href = "/css/plan.css", type = "text/css")
                    }
                    body {
                        unsafe { raw(MarkdownUtil.toHtml(response)) }
                        br {}
                        p {
                            +"If you like the study plan, save it to your device or print it out."
                            +" "
                            +"If you want to change something, tell the AI want do you want to change."
                        }
                        continueStudyPlanner()
                    }
                }
            } else {
                val response = geminiPro.getResponse(
                    message = description,
                    beforePrompt = "Hi, please change the study plan according to my request:",
                    afterPrompt = "Please return the new result in pure Markdown format with bullet lists. Thanks!"
                )
                call.respondHtml {
                    head {
                        title { +"Study Plan" }
                        link(rel = "stylesheet", href = "/css/plan.css", type = "text/css")
                    }
                    body {
                        unsafe { raw(MarkdownUtil.toHtml(response)) }
                        br {}
                        br {}
                        br {}
                        p {
                            +"If you are satisfied with the new study plan, save it to your device or print it out."
                            +" "
                            +"If you want to change something, you cal still tell the AI what do you want to change."
                        }
                        continueStudyPlanner()
                    }
                }
            }
        }
    }
}
