package io.github.kingg22.deezerSdk.utils

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.BodyProgress
import io.ktor.client.plugins.Charsets
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.addDefaultResponseValidation
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.AcceptAllCookiesStorage
import io.ktor.client.plugins.cookies.CookiesStorage
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.LoggingFormat
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import io.ktor.http.userAgent
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.charsets.Charsets
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlin.jvm.JvmSynthetic
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

internal data object HttpClientProvider {
    @JvmSynthetic
    const val DEFAULT_USER_AGENT = "Mozilla/5.0 (X11; Linux i686; rv:135.0) Gecko/20100101 Firefox/135.0"

    @JvmSynthetic
    const val DEFAULT_MAX_RETRY_ATTEMPTS = 3

    @JvmSynthetic
    val DEFAULT_MAX_RETRY_TIMEOUT = 20.seconds

    @JvmSynthetic
    val DEFAULT_COOKIE_STORAGE: CookiesStorage = AcceptAllCookiesStorage()

    /**
     * @throws IllegalArgumentException If the retry count is less than zero.
     * @throws IllegalArgumentException If the timeout is less than or equal to zero.
     * @throws IllegalArgumentException If the provided User-Agent string is empty.
     */
    @Suppress("kotlin:S107")
    @JvmSynthetic
    @Throws(IllegalArgumentException::class)
    fun getClient(
        userAgent: String = DEFAULT_USER_AGENT,
        maxRetryCount: Int = DEFAULT_MAX_RETRY_ATTEMPTS,
        timeout: Duration = DEFAULT_MAX_RETRY_TIMEOUT,
        engine: HttpClientEngine? = null,
        cookiesStorage: CookiesStorage = DEFAULT_COOKIE_STORAGE,
        logLevel: LogLevel = LogLevel.INFO,
        httpLogger: Logger,
        modifiers: List<HttpClientConfig<*>.() -> Unit> = emptyList(),
    ): HttpClient {
        require(userAgent.isNotBlank())
        require(timeout >= 0.seconds)
        require(maxRetryCount > 0)

        val clientConfig: HttpClientConfig<*>.() -> Unit = {
            install(HttpCookies) {
                storage = cookiesStorage
            }
            install(BodyProgress)
            install(ContentNegotiation) {
                json(getJson())
            }
            install(HttpTimeout) {
                requestTimeoutMillis = timeout.inWholeMilliseconds
            }
            install(HttpRequestRetry) {
                maxRetries = maxRetryCount
                exponentialDelay()
            }
            install(Logging) {
                logger = httpLogger
                format = LoggingFormat.OkHttp
                level = logLevel
            }
            addDefaultResponseValidation()
            expectSuccess = true

            defaultRequest {
                userAgent(userAgent)
                headers {
                    append(HttpHeaders.Pragma, "no-cache")
                    append(HttpHeaders.Origin, "https://www.deezer.com")
                    append(HttpHeaders.AcceptEncoding, "gzip, deflate, br")
                    append(HttpHeaders.AcceptLanguage, "en-US,en;q=0.9")
                    append(HttpHeaders.Accept, "*/*")
                    append(HttpHeaders.CacheControl, "no-cache")
                    append(HttpHeaders.Connection, "keep-alive")
                    append(HttpHeaders.Referrer, "https://www.deezer.com")
                    append("X-Requested-With", "XMLHttpRequest")
                    append("DNT", "1")
                    append("Sec-Fetch-Dest", "empty")
                    append("Sec-Fetch-Mode", "cors")
                    append("Sec-Fetch-Site", "same-site")
                }
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

    @OptIn(ExperimentalSerializationApi::class)
    @JvmSynthetic
    internal fun getJson() = Json {
        encodeDefaults = true
        isLenient = true
        allowSpecialFloatingPointValues = true
        allowStructuredMapKeys = true

        prettyPrint = true
        ignoreUnknownKeys = true
        explicitNulls = false
        decodeEnumsCaseInsensitive = true
        useArrayPolymorphism = true
    }

    internal enum class DeezerApiSupported(@get:JvmSynthetic val baseUrl: String) {
        @JvmSynthetic
        API_DEEZER("https://api.deezer.com/"),
    }
}
