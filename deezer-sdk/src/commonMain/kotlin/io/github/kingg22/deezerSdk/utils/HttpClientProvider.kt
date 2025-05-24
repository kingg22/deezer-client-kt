package io.github.kingg22.deezerSdk.utils

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.Charsets
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.AcceptAllCookiesStorage
import io.ktor.client.plugins.cookies.CookiesStorage
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.userAgent
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.charsets.Charsets
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

internal object HttpClientProvider {
    const val DEFAULT_USER_AGENT: String =
        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36"
    const val DEFAULT_MAX_RETRY_ATTEMPTS = 3

    @JvmStatic
    val DEFAULT_MAX_RETRY_TIMEOUT = 20.seconds

    @JvmStatic
    val DEFAULT_COOKIE_STORAGE: CookiesStorage = AcceptAllCookiesStorage()

    /**
     * @throws IllegalArgumentException If the retry count is less than zero.
     * @throws IllegalArgumentException If the timeout is less than or equal to zero.
     * @throws IllegalArgumentException If the provided User-Agent string is empty.
     */
    @OptIn(ExperimentalSerializationApi::class)
    @Throws(IllegalArgumentException::class)
    fun getClient(
        userAgent: String = DEFAULT_USER_AGENT,
        maxRetryCount: Int = DEFAULT_MAX_RETRY_ATTEMPTS,
        timeout: Duration = DEFAULT_MAX_RETRY_TIMEOUT,
        engine: HttpClientEngine? = null,
        cookiesStorage: CookiesStorage = DEFAULT_COOKIE_STORAGE,
        logLevel: LogLevel = LogLevel.INFO,
        modifiers: List<HttpClientConfig<*>.() -> Unit> = listOf(),
    ): HttpClient {
        require(userAgent.isNotBlank())
        require(timeout >= 0.seconds)
        require(maxRetryCount > 0)
        val clientConfig: HttpClientConfig<*>.() -> Unit = {
            install(HttpCookies) {
                storage = cookiesStorage
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                        explicitNulls = false
                        decodeEnumsCaseInsensitive = true
                    },
                )
            }
            install(HttpTimeout) {
                requestTimeoutMillis = timeout.inWholeMilliseconds
            }
            install(HttpRequestRetry) {
                maxRetries = maxRetryCount
                exponentialDelay()
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        co.touchlab.kermit.Logger.d("HttpClient") { message }
                    }
                }
                level = logLevel
            }

            defaultRequest {
                userAgent(userAgent)
            }

            Charsets {
                register(Charsets.UTF_8)
                sendCharset = Charsets.UTF_8
                responseCharsetFallback = Charsets.UTF_8
            }

            modifiers.forEach { it(this) }
        }
        return if (engine != null) HttpClient(engine, clientConfig) else HttpClient(clientConfig)
    }

    enum class DeezerApiSupported(val baseUrl: String) {
        API_DEEZER("https://api.deezer.com/"),

        @UnofficialDeezerApi
        GW_DEEZER("https://www.deezer.com/ajax/gw-light.php/"),

        /** **Don't contain `/` at the end** */
        @UnofficialDeezerApi
        MEDIA_DEEZER("https://media.deezer.com/v1/get_url"),
        ;

        companion object {
            /** Include `https://` and finish with `/` */
            fun fromBaseUrl(baseUrl: String) = entries.firstOrNull { it.baseUrl == baseUrl }
        }
    }
}
