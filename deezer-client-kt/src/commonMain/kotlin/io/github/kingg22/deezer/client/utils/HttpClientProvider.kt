@file:JvmName("HttpClientProvider")

package io.github.kingg22.deezer.client.utils

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.charsets.*
import kotlin.jvm.JvmName
import kotlin.jvm.JvmSynthetic
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@JvmSynthetic
internal const val DEFAULT_USER_AGENT = "Mozilla/5.0 (X11; Linux i686; rv:135.0) Gecko/20100101 Firefox/135.0"

@JvmSynthetic
internal const val DEFAULT_MAX_RETRY_ATTEMPTS = 3

@JvmSynthetic
internal val DEFAULT_MAX_RETRY_TIMEOUT = 20.seconds

@JvmSynthetic
internal val DEFAULT_COOKIE_STORAGE: CookiesStorage = AcceptAllCookiesStorage()

/**
 * @throws IllegalArgumentException If the retry count is less than zero.
 * @throws IllegalArgumentException If the timeout is less than or equal to zero.
 * @throws IllegalArgumentException If the provided User-Agent string is empty.
 */
@Suppress("kotlin:S107")
@JvmSynthetic
@Throws(IllegalArgumentException::class)
internal fun getClient(
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

@JvmSynthetic
internal const val API_DEEZER = "https://api.deezer.com/"
