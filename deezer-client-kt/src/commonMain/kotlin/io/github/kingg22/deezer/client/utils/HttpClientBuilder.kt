@file:Suppress("DEPRECATION", "kotlin:S1133")

package io.github.kingg22.deezer.client.utils

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.cookies.AcceptAllCookiesStorage
import io.ktor.client.plugins.cookies.CookiesStorage
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmSynthetic
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/** Builder for the HttpClient. */
@Deprecated(
    "This class is not longer necessary, use Ktor client Deezer Plugin instead.",
    ReplaceWith(
        """HttpClient(engine) {
        install(HttpCookies) {
            storage = cookiesStorage
        }
        install(BodyProgress)
        install(ContentNegotiation) {
            json(
                Json {
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
            this.logger = logger
            format = LoggingFormat.OkHttp
            level = httpLogLevel
        }

        defaultRequest {
            userAgent(userAgent)
        }

        Charsets {
            register(Charsets.UTF_8)
            sendCharset = Charsets.UTF_8
            responseCharsetFallback = Charsets.UTF_8
        }
    }""",
        "kotlinx.serialization.json.Json",
    ),
    level = DeprecationLevel.WARNING,
)
data class HttpClientBuilder @JvmOverloads constructor(
    /** Sets a custom user-agent for the HttpClient. */
    @set:Deprecated("Use defaultRequest of Ktor", ReplaceWith("""defaultRequest { userAgent(userAgent) }"""))
    var userAgent: String = "Mozilla/5.0 (X11; Linux i686; rv:135.0) Gecko/20100101 Firefox/135.0",

    /** Sets a custom cookies storage for the HttpClient. Default [AcceptAllCookiesStorage] */
    @set:Deprecated(
        "Use Cookie plugin of Ktor directly.",
        ReplaceWith(
            """install(HttpCookies) { this.storage = cookiesStorage }""",
            "io.ktor.client.plugins.cookies.*",
        ),
    )
    var cookiesStorage: CookiesStorage = AcceptAllCookiesStorage(),

    /** Sets a timeout for the HttpClient. Default 20 seg */
    @set:Deprecated(
        "Use timeout plugin of Ktor directly.",
        ReplaceWith(
            """install(HttpTimeout) { requestTimeoutMillis = timeout }""",
            "io.ktor.client.plugins.*",
        ),
    )
    var timeout: Long = 20.seconds.inWholeSeconds,

    /** Sets the max retry count for requests. Default 3 */
    @set:Deprecated(
        "Use retry plugin of Ktor directly.",
        ReplaceWith(
            """install(HttpRequestRetry) {
            maxRetries = maxRetryCount
            exponentialDelay()
        }""",
            "io.ktor.client.plugins.*",
        ),
    )
    var maxRetryCount: Int = 3,

    /** Specifies the HttpClientEngine to be used explicit. Default automatically set by Ktor. */
    @set:Deprecated("Use directly with HttpClient DSL of Ktor", ReplaceWith("HttpClient(engine)"))
    var httpEngine: HttpClientEngine? = null,

    /** Sets a custom logger for the HttpClient. Default a no-op logger. */
    @set:Deprecated(
        "Use Logging plugin of Ktor directly.",
        ReplaceWith(
            """install(Logging) {
            this.logger = logger
            format = LoggingFormat.OkHttp
            level = LogLevel.INFO
        }""",
            "io.ktor.client.plugins.logging.*",
        ),
    )
    var logger: Logger = object : Logger {
        override fun log(message: String) {
            // do nothing
        }
    },

    /** Defines the logging level for HTTP requests and responses. Default is [LogLevel.INFO]. */
    @set:Deprecated(
        "Use Logging plugin of Ktor directly.",
        ReplaceWith(
            """install(Logging) { level = httpLogLevel }""",
            "io.ktor.client.plugins.logging.*",
        ),
    )
    var httpLogLevel: LogLevel = LogLevel.INFO,
) {
    private val customHttpConfig: MutableList<HttpClientConfig<*>.() -> Unit> = mutableListOf()

    /** Sets a custom user-agent for the HttpClient. */
    @Deprecated("Use defaultRequest of Ktor", ReplaceWith("""defaultRequest { userAgent(userAgent) }"""))
    fun userAgent(userAgent: String) = apply { this.userAgent = userAgent }

    /** Sets a custom cookies storage for the HttpClient. Default [AcceptAllCookiesStorage] */
    @Deprecated(
        "Use Cookie plugin of Ktor directly.",
        ReplaceWith(
            """install(HttpCookies) { this.storage = storage }""",
            "io.ktor.client.plugins.cookies.*",
        ),
    )
    fun cookiesStorage(storage: CookiesStorage) = apply { this.cookiesStorage = storage }

    /** Sets a timeout for the HttpClient. Default 20 seg */
    @JvmSynthetic
    @Deprecated(
        "Use timeout plugin of Ktor directly.",
        ReplaceWith(
            """install(HttpTimeout) { requestTimeoutMillis = duration.inWholeMilliseconds }""",
            "io.ktor.client.plugins.*",
        ),
    )
    fun timeout(duration: Duration) = apply { this.timeout = duration.inWholeSeconds }

    /** Sets a timeout for the HttpClient. Default 20 seg */
    @Deprecated(
        "Use timeout plugin of Ktor directly.",
        ReplaceWith(
            """install(HttpTimeout) { requestTimeoutMillis = durationSeconds }""",
            "io.ktor.client.plugins.*",
        ),
    )
    fun timeout(durationSeconds: Long) = apply { this.timeout = durationSeconds }

    /** Sets the max retry count for requests. Default 3 */
    @Deprecated(
        "Use retry plugin of Ktor directly.",
        ReplaceWith(
            """install(HttpRequestRetry) {
            maxRetries = count
            exponentialDelay()
        }""",
            "io.ktor.client.plugins.*",
        ),
    )
    fun maxRetryCount(count: Int) = apply { this.maxRetryCount = count }

    /** Sets the logging level for HTTP requests and responses. Default [LogLevel.INFO] */
    @Deprecated(
        "Use Logging plugin of Ktor directly.",
        ReplaceWith(
            """install(Logging) { level = logLevel }""",
            "io.ktor.client.plugins.logging.*",
        ),
    )
    fun httpLogLevel(logLevel: LogLevel) = apply { this.httpLogLevel = logLevel }

    /** Sets a custom logger for the HttpClient. Default NoOp. */
    @Deprecated(
        "Use Logging plugin of Ktor directly.",
        ReplaceWith(
            """install(Logging) {
            this.logger = logger
            format = LoggingFormat.OkHttp
            level = LogLevel.INFO
        }""",
            "io.ktor.client.plugins.logging.*",
        ),
    )
    fun logger(logger: Logger) = apply { this.logger = logger }

    /** Specifies the HttpClientEngine to be used explicit. Default automatically set by Ktor. */
    @Deprecated("Use directly with HttpClient DSL of Ktor", ReplaceWith("HttpClient(engine)"))
    fun httpEngine(engine: HttpClientEngine) = apply { this.httpEngine = engine }

    /**
     * **Warning:** This is an internal function and should only be used if you know what you're doing.
     * You don't have to specify the HTTP client engine, it will be automatically set by Ktor.
     * @see [httpEngine]
     */
    @ExperimentalDeezerClient
    @Deprecated("Use directly with HttpClient DSL of Ktor")
    fun addCustomConfig(config: HttpClientConfig<*>.() -> Unit) = apply { this.customHttpConfig.add(config) }

    /**
     * Builds the HttpClient with the configured options.
     * @throws IllegalArgumentException If the retry count is less than zero.
     * @throws IllegalArgumentException If the timeout is less than or equal to zero.
     * @throws IllegalArgumentException If the provided User-Agent string is empty.
     */
    @Throws(IllegalArgumentException::class)
    fun build() = getClient(
        userAgent,
        maxRetryCount,
        timeout.seconds,
        httpEngine,
        cookiesStorage,
        httpLogLevel,
        logger,
        customHttpConfig,
    )

    /** Utilities for HttpClientBuilder */
    @Deprecated("See HttpClientBuilder deprecation")
    companion object {
        /**
         * Builds the HttpClient with a DSL.
         * @param block A DSL
         * @throws IllegalArgumentException see [HttpClientBuilder.build]
         */
        @JvmSynthetic
        @Throws(IllegalArgumentException::class)
        @Deprecated("See HttpClientBuilder deprecation")
        fun httpClient(block: HttpClientBuilder.() -> Unit = {}) = HttpClientBuilder().apply(block).build()

        /**
         * Builds the HttpClientBuilder using DSL.
         * @param builder A DSL
         */
        @JvmSynthetic
        @Deprecated("See HttpClientBuilder deprecation")
        fun httpClientBuilder(builder: HttpClientBuilder.() -> Unit = {}) = HttpClientBuilder().apply(builder)
    }
}
