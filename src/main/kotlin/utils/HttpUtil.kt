package utils

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import okhttp3.Protocol

/**
 * HTTP utilities, used for quickly sending GET and POST requests
 * @property httpClient The HTTP client used for sending requests
 * @property GetRequests The object containing all GET request methods
 * @property PostRequests The object containing all POST request methods
 */
object HttpUtil {
    /**
     * The Ktor HTTP client used for sending requests
     */
    private val httpClient = HttpClient(OkHttp) {
        engine {
            config {
                followRedirects(true)
                protocols(listOf(Protocol.HTTP_1_1, Protocol.HTTP_2))
            }
        }
    }

    /**
     * The object containing all GET request methods
     */
    object GetRequests {
        /**
         * Send a GET request to the specified URL
         * @param url The URL to send the request to
         * @param requestBuilder The request builder used to modify the request
         * @return The response from the server
         */
        fun request(url: String, requestBuilder: HttpRequestBuilder.() -> Unit = {}): HttpResponse {
            val httpResponse: HttpResponse
            runBlocking {
                httpResponse = httpClient.get(url, requestBuilder)
            }
            return httpResponse
        }

        /**
         * Send a GET request to the specified URL and return the response body as a string
         * @param url The URL to send the request to
         * @param requestBuilder The request builder used to modify the request
         * @return The response body as a string
         */
        fun body(url: String, requestBuilder: HttpRequestBuilder.() -> Unit = {}): String {
            val httpResponseBody: String
            runBlocking {
                httpResponseBody = request(url, requestBuilder).bodyAsText()
            }
            return httpResponseBody
        }

        private fun bodyJson(url: String, requestBuilder: HttpRequestBuilder.() -> Unit = {}): Any {
            val parser: Parser = Parser.default()
            return parser.parse(StringBuilder(body(url, requestBuilder)))
        }

        /**
         * Send a GET request to the specified URL and return the response body as a JSON object
         * @param url The URL to send the request to
         * @param requestBuilder The request builder used to modify the request
         * @return The response body as a JSON object
         */
        fun bodyJsonObject(url: String, requestBuilder: HttpRequestBuilder.() -> Unit = {}): JsonObject {
            return bodyJson(url, requestBuilder) as JsonObject
        }

        /**
         * Send a GET request to the specified URL and return the response body as a JSON array
         * @param url The URL to send the request to
         * @param requestBuilder The request builder used to modify the request
         * @return The response body as a JSON array
         */
        @Suppress("UNCHECKED_CAST")
        fun bodyJsonArray(url: String, requestBuilder: HttpRequestBuilder.() -> Unit = {}): JsonArray<JsonObject> {
            return bodyJson(url, requestBuilder) as JsonArray<JsonObject>
        }
    }

    /**
     * The object containing all POST request methods
     */
    object PostRequests {
        /**
         * Send a POST request to the specified URL
         * @param url The URL to send the request to
         * @param requestBuilder The request builder used to modify the request
         * @return The response from the server
         */
        fun request(url: String, requestBuilder: HttpRequestBuilder.() -> Unit = {}): HttpResponse {
            val httpResponse: HttpResponse
            runBlocking {
                httpResponse = httpClient.post(url, requestBuilder)
            }
            return httpResponse
        }

        /**
         * Send a POST request to the specified URL and return the response body as a string
         * @param url The URL to send the request to
         * @param requestBuilder The request builder used to modify the request
         * @return The response body as a string
         */
        fun body(url: String, requestBuilder: HttpRequestBuilder.() -> Unit = {}): String {
            val httpResponseBody: String
            runBlocking {
                httpResponseBody = request(url, requestBuilder).bodyAsText()
            }
            return httpResponseBody
        }

        private fun bodyJson(url: String, requestBuilder: HttpRequestBuilder.() -> Unit = {}): Any {
            val parser: Parser = Parser.default()
            return parser.parse(StringBuilder(body(url, requestBuilder)))
        }

        /**
         * Send a POST request to the specified URL and return the response body as a JSON object
         * @param url The URL to send the request to
         * @param requestBuilder The request builder used to modify the request
         * @return The response body as a JSON object
         */
        fun bodyJsonObject(url: String, requestBuilder: HttpRequestBuilder.() -> Unit = {}): JsonObject {
            return bodyJson(url, requestBuilder) as JsonObject
        }

        /**
         * Send a POST request to the specified URL and return the response body as a JSON array
         * @param url The URL to send the request to
         * @param requestBuilder The request builder used to modify the request
         * @return The response body as a JSON array
         */
        @Suppress("UNCHECKED_CAST")
        fun bodyJsonArray(url: String, requestBuilder: HttpRequestBuilder.() -> Unit = {}): JsonArray<JsonObject> {
            return bodyJson(url, requestBuilder) as JsonArray<JsonObject>
        }
    }
}
