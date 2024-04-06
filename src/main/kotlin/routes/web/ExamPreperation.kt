package routes.web

import api.GeminiPro
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.html.*
import utils.ConfigUtil
import utils.MarkdownUtil

fun BODY.continueExamPreparation() {
    form(action = "/exam-prep", method = FormMethod.post) {
        textInput {
            name = "description"
            placeholder = "Tell the AI the action you would like to perform"
        }
        br
        button {
            type = ButtonType.submit
            id = "submissionButton"
            +"Submit"
        }
    }
}

fun Route.examPreperation() {
    val apiConfig = ConfigUtil.loadConfig().api.gemini
    val geminiPro = GeminiPro(
        url = apiConfig.url,
        key = apiConfig.key,
        prePrompt = """
            You have to return everything back to me in pure Markdown format.
            Use only normal text, bullet lists, and numbered lists.
            Bold, italic, strikethrough, links, images, code blocks are supported.
            Tables is not supported.
            You are an Exam Preparation Bot, therefore you should only be creating the questions and solutions, and 
            only explain the solutions in detail if prompted to do so. If you are asked to add more, just tell generate more
            questions. If it is prompted to walk you through the process, then explain the process in detail, it should
            contain calculations, explanations and link to theories. You should also be able to analyze why as to they've
            gotten something wrong, and the reason why. Finally, if the problem is too complex too handle, please
            refer resources (with links) to the students and explain how it could help them solve the problem. 
            Never say that you are available for help online or other unrelated things.
        """.trimIndent(),
        prePromptResponse = """
            **Exam Preparation Bot**

            **Services:**

            - Creating questions and solutions upon given theory
            - Explain the solutions in detail and walk you through the process

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

    route("/exam-prep") {
        get {
            call.respondHtml {
                head {
                    title { +"Exam Preparation Bot" }
                }
                body {
                    form(action = "/exam-prep", method = FormMethod.post) {
                        textInput {
                            name = "description"
                            placeholder =
                                "Enter theories, examples and problems for an exam and I will prepare questions for you."
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
            val description = parameters["description"] ?: ""
            val response = geminiPro.getResponse(
                message = description,
                beforePrompt = "Please either add more questions or explain the solutions to each and walk you through the process." +
                        "depending on the given prompt:",
                afterPrompt = "Please return the new result in pure Markdown format with bullet lists. Thanks!"
            )
            call.respondHtml {
                head {
                    title { +"Exam Preparation Bot" }
                }
                body {
                    unsafe { raw(MarkdownUtil.toHtml(response)) }
                    br {}
                    p {
                        +"If you would like to add more, just tell the AI what you would like to add."
                        +" "
                        +"If you are satisfied with the new questions, save them to your device or print them out."
                        +" "
                        +"Otherwise, I can also explain the solutions to each and walk you through the process."
                    }
                    continueExamPreparation()
                }
            }
        }
    }
}