package routes.web

import api.GeminiPro
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.html.*
import utils.ConfigUtil
import utils.MarkdownUtil

fun BODY.chatbox() = div {
    id = "chatbox"
    style = "z-index: 9;"
    div("form"){
        form(action = "/academic-helper", method = FormMethod.post) {
            textInput {
                name = "message"
                placeholder = "Chat with your AI Academic Helper"
            }
            br
            button {
                type = ButtonType.submit
                id = "sendButton"

            }
        }
    }
}

fun Route.academicHelper() {
    val apiConfig = ConfigUtil.loadConfig().api.gemini
    val geminiPro = GeminiPro(
        url = apiConfig.url,
        key = apiConfig.key,
        prePrompt = """
            You have to return everything back to me in pure Markdown format.
            Use only normal text, bullet lists, and numbered lists.
            Bold, italic, strikethrough are allowed. Any other formatting or syntax are strictly prohibited.
            If a new line is needed, use `\n` as the line break, and add a `\t` right before the new line.
            You are an academic helper, so you should be able to answer FAQ/ any question related to academic advising,
            offer recommendation for certain courses and programs, personalized study advice, possible financial/aid
            around given specific scenarios, Counseling and advice for mental health, such as balancing life and reducing stress.
        """.trimIndent(),
        prePromptResponse = """
            **Academic Advising**
            
            * Frequently Asked Questions (FAQs)
            * Course and program recommendations
            * Personalized study plans
            * Financial aid and scholarship options
            
            **Counseling and Mental Health**
            **Counseling and Mental Health**
            
            * Balancing life and reducing stress
            * Time management and organization strategies
            * Motivation and focus techniques
            * Strategies for coping with academic difficulties
            * Resources for mental health support
            
            **Personalized Services**
            
            * One-on-one advising sessions
            * Email and phone consultations
            * Group workshops and presentations
            
            **Additional Resources**
            
            * Academic Success Center
            * Writing Center
            * Math Lab
            * Counseling Services
            * Financial Aid Office
        """.trimIndent()
    )

    route("/academic-helper") {
        get {
            call.respondHtml {
                head {
                    title { +"Academic Helper" }
                    link(rel = "stylesheet", href = "/css/advisor.css", type = "text/css")
                }
                body {
                    chatbox()
                }
            }
        }

        post {
            val message = call.receiveParameters()["message"] ?: ""
            geminiPro.getResponse(message)
            call.respondHtml {
                head {
                    title { +"Academic Helper" }
                    link(rel = "stylesheet", href = "/css/chat.css", type = "text/css")
                }
                body {
                    for (chat in geminiPro.messages) {
                        for ((role, parts) in chat) {
                            b {
                                +"$role: "
                            }
                            div {
                                style = "margin-left: 3em;"
                                unsafe { raw(MarkdownUtil.toHtml(parts)) }
                            }
                        }
                    }
                    chatbox()
                    div("back") {
                        p{
                            onClick="location='/'"
                            +"GO BACK"
                        }
                    }
                }
            }
        }
    }
}
