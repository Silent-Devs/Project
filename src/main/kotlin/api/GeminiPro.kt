package api

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import io.ktor.client.request.*
import io.ktor.http.*
import utils.HttpUtil
import java.util.ArrayList

/**
 * Request to GeminiPro API
 * @param url API URL
 * @param key API Key
 */
class GeminiPro (url: String, key: String) {
    /**
     * The URL which requests are sent to
     */
    private val api = "$url?key=$key"

    /**
     * The contents of the request, saving every message sent by user and response from model
     * Used for multi-turn conversation support
     */
    private val contents: ArrayList<Map<String, Any>> = ArrayList()

    /**
     * Get response from GeminiPro API
     * @param message The message sent by user
     * @return The response from GeminiPro API
     */
    fun getResponse(message: String): String {
        // Add user message to contents
        contents.add(
            mapOf(
                "role" to "user",
                "parts" to arrayOf(
                    mapOf(
                        "text" to message
                    )
                )
            )
        )

        // Parse JSON API request body
        val request = Klaxon().toJsonString(
            mapOf("contents" to contents)
        )

        // Send request to API
        val response = HttpUtil.PostRequests.bodyJsonObject(api) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        // Get all responses from API
        val results = ArrayList<String>()
        response.array<JsonObject>("candidates")?.forEach { candidate ->
            candidate.obj("content")?.array<JsonObject>("parts")?.forEach { part ->
                results.add(part.string("text") ?: "")
            }
        }

        // Get the first response sent back by the model
        val modelResponse = results[0]

        // Add model response to contents
        contents.add(
            mapOf(
                "role" to "model",
                "parts" to arrayOf(
                    mapOf(
                        "text" to modelResponse
                    )
                )
            )
        )

        // Return the response to user
        return modelResponse
    }
}
