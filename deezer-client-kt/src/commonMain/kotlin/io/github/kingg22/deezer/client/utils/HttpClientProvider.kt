@file:JvmName("HttpClientProvider")

package io.github.kingg22.deezer.client.utils

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.utils.buildHeaders
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.charsets.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlin.jvm.JvmName
import kotlin.jvm.JvmSynthetic
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * @throws IllegalArgumentException If the retry count is less than zero.
 * @throws IllegalArgumentException If the timeout is less than or equal to zero.
 * @throws IllegalArgumentException If the provided User-Agent string is empty.
 */
@Suppress("kotlin:S107")
@JvmSynthetic
@Throws(IllegalArgumentException::class)
@Deprecated("")
internal fun getClient(
    userAgent: String,
    maxRetryCount: Int,
    timeout: Duration,
    engine: HttpClientEngine?,
    cookiesStorage: CookiesStorage,
    logLevel: LogLevel,
    httpLogger: Logger,
    modifiers: List<HttpClientConfig<*>.() -> Unit>,
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
            json(getDefaultJson())
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

/** Configure a [Json] with default configuration to be compatible with the Deezer API */
@OptIn(ExperimentalSerializationApi::class)
@InternalDeezerClient
fun getDefaultJson() = Json {
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

/**
 * _Advanced_ Configure a [Headers] with default configuration to be compatible with the Deezer API,
 * this is not needed in normal cases.
 * @see io.github.kingg22.deezer.client.api.DeezerPluginConfig.includeDefaultHeaders
 */
@InternalDeezerClient
fun getDefaultDeezerHeaders() = buildHeaders {
    append(HttpHeaders.Origin, "https://www.deezer.com")
    append(HttpHeaders.Referrer, "https://www.deezer.com")
    append("DNT", "1")
    append("Sec-Fetch-Dest", "empty")
    append("Sec-Fetch-Mode", "cors")
    append("Sec-Fetch-Site", "same-site")
}
