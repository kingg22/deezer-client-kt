package io.github.kingg22.deezerSdk.utils

import io.github.kingg22.deezerSdk.utils.HttpClientProvider.DEFAULT_COOKIE_STORAGE
import io.github.kingg22.deezerSdk.utils.HttpClientProvider.DEFAULT_MAX_RETRY_ATTEMPTS
import io.github.kingg22.deezerSdk.utils.HttpClientProvider.DEFAULT_MAX_RETRY_TIMEOUT
import io.github.kingg22.deezerSdk.utils.HttpClientProvider.DEFAULT_USER_AGENT
import io.github.kingg22.deezerSdk.utils.HttpClientProvider.getClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.cookies.AcceptAllCookiesStorage
import io.ktor.client.plugins.cookies.CookiesStorage
import io.ktor.client.plugins.logging.LogLevel
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/** Builder for the HttpClient. */
data class HttpClientBuilder @JvmOverloads constructor(
    /** Sets a custom user-agent for the HttpClient. */
    var userAgent: String = DEFAULT_USER_AGENT,

    /** Sets a custom cookies storage for the HttpClient. Default [AcceptAllCookiesStorage] */
    var cookiesStorage: CookiesStorage = DEFAULT_COOKIE_STORAGE,

    /** Sets a timeout for the HttpClient. Default 20 seg */
    var timeout: Long = DEFAULT_MAX_RETRY_TIMEOUT.inWholeSeconds,

    /** Sets the max retry count for requests. Default 3 */
    var maxRetryCount: Int = DEFAULT_MAX_RETRY_ATTEMPTS,

    /** Specifies the HttpClientEngine to be used explicit. Default automatically set by Ktor. */
    var httpEngine: HttpClientEngine? = null,

    /** Defines the logging level for HTTP requests and responses. Default is [LogLevel.INFO]. */
    var httpLogLevel: LogLevel = LogLevel.INFO,
) {
    private val customHttpConfig: MutableList<HttpClientConfig<*>.() -> Unit> = mutableListOf()

    /** Sets a custom user-agent for the HttpClient. */
    fun userAgent(userAgent: String) = apply {
        this.userAgent = userAgent
    }

    /** Sets a custom cookies storage for the HttpClient. Default [AcceptAllCookiesStorage] */
    fun cookiesStorage(storage: CookiesStorage) = apply {
        this.cookiesStorage = storage
    }

    /** Sets a timeout for the HttpClient. Default 20 seg */
    fun timeout(duration: Duration) = apply {
        this.timeout = duration.inWholeSeconds
    }

    /** Sets a timeout for the HttpClient. Default 20 seg */
    fun timeout(durationSeconds: Long) = apply {
        this.timeout = durationSeconds
    }

    /** Sets the max retry count for requests. Default 3 */
    fun maxRetryCount(count: Int) = apply {
        this.maxRetryCount = count
    }

    /** Sets the logging level for HTTP requests and responses. Default [LogLevel.INFO] */
    fun httpLogLevel(logLevel: LogLevel) = apply {
        this.httpLogLevel = logLevel
    }

    /** Specifies the HttpClientEngine to be used explicit. Default automatically set by Ktor. */
    fun httpEngine(engine: HttpClientEngine) = apply {
        this.httpEngine = engine
    }

    /**
     * **Warning:** This is an internal function and should only be used if you know what you're doing.
     * You don't have to specify the HTTP client engine, it will be automatically set by Ktor.
     * @see [httpEngine]
     */
    @ExperimentalDeezerSdk
    fun addCustomConfig(config: HttpClientConfig<*>.() -> Unit) = apply {
        this.customHttpConfig.add(config)
    }

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
        customHttpConfig,
    )

    companion object {
        /** Builds the HttpClient with the default options. */
        @JvmStatic
        @JvmOverloads
        @Throws(IllegalArgumentException::class)
        fun httpClient(block: HttpClientBuilder.() -> Unit = {}) = HttpClientBuilder().apply(block).build()

        /** Builds the HttpClientBuilder with the default options. */
        @JvmStatic
        @JvmOverloads
        fun httpClientBuilder(builder: HttpClientBuilder.() -> Unit = {}) = HttpClientBuilder().apply(builder)
    }
}
