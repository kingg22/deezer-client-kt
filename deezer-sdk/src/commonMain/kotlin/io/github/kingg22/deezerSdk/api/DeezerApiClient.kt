package io.github.kingg22.deezerSdk.api

import io.github.kingg22.deezerSdk.api.routes.createAlbumRoutes
import io.github.kingg22.deezerSdk.api.routes.createArtistRoutes
import io.github.kingg22.deezerSdk.api.routes.createChartRoutes
import io.github.kingg22.deezerSdk.api.routes.createEditorialRoutes
import io.github.kingg22.deezerSdk.api.routes.createEpisodeRoutes
import io.github.kingg22.deezerSdk.api.routes.createGenreRoutes
import io.github.kingg22.deezerSdk.api.routes.createInfosRoute
import io.github.kingg22.deezerSdk.api.routes.createOptionsRoute
import io.github.kingg22.deezerSdk.api.routes.createPlaylistRoutes
import io.github.kingg22.deezerSdk.api.routes.createPodcastRoutes
import io.github.kingg22.deezerSdk.api.routes.createRadioRoutes
import io.github.kingg22.deezerSdk.api.routes.createSearchRoutes
import io.github.kingg22.deezerSdk.api.routes.createTrackRoutes
import io.github.kingg22.deezerSdk.api.routes.createUserRoutes
import io.github.kingg22.deezerSdk.exceptions.DeezerApiException
import io.github.kingg22.deezerSdk.utils.ExperimentalDeezerSdk
import io.github.kingg22.deezerSdk.utils.HttpClientBuilder
import io.github.kingg22.deezerSdk.utils.HttpClientProvider
import io.github.kingg22.deezerSdk.utils.LateInitClient
import io.github.kingg22.deezerSdk.utils.createKtorfit
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpCallValidatorConfig
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.http.isSuccess
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.coroutines.cancellation.CancellationException

/**
 * Client for the official [Deezer API](https://developers.deezer.com/api/).
 *
 * Start with [DeezerApiClient.initialize]
 *
 * @author Kingg22
 */
data object DeezerApiClient : LateInitClient {
    /** The current [HttpClient] using */
    @JvmStatic
    @PublishedApi
    internal lateinit var httpClient: HttpClient
        private set

    @JvmStatic
    private lateinit var builder: HttpClientBuilder

    @JvmStatic
    private val ktorfit by lazy {
        createKtorfit(HttpClientProvider.DeezerApiSupported.API_DEEZER.baseUrl, httpClient)
    }

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Album] */
    @JvmStatic
    val albums by lazy {
        ktorfit.createAlbumRoutes()
    }

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Artist] */
    @JvmStatic
    val artists by lazy {
        ktorfit.createArtistRoutes()
    }

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Chart] */
    @JvmStatic
    val charts by lazy {
        ktorfit.createChartRoutes()
    }

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Editorial] */
    @JvmStatic
    val editorials by lazy {
        ktorfit.createEditorialRoutes()
    }

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Episode] */
    @JvmStatic
    val episodes by lazy {
        ktorfit.createEpisodeRoutes()
    }

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Genre] */
    @JvmStatic
    val genres by lazy {
        ktorfit.createGenreRoutes()
    }

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Infos] */
    @JvmStatic
    val infos by lazy {
        ktorfit.createInfosRoute()
    }

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Options] */
    @JvmStatic
    val options by lazy {
        ktorfit.createOptionsRoute()
    }

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Playlist] */
    @JvmStatic
    val playlists by lazy {
        ktorfit.createPlaylistRoutes()
    }

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Podcast] */
    @JvmStatic
    val podcasts by lazy {
        ktorfit.createPodcastRoutes()
    }

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Radio] */
    @JvmStatic
    val radios by lazy {
        ktorfit.createRadioRoutes()
    }

    /** All endpoints related to search */
    @JvmStatic
    val searches by lazy {
        ktorfit.createSearchRoutes()
    }

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Track] */
    @JvmStatic
    val tracks by lazy {
        ktorfit.createTrackRoutes()
    }

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.User] */
    @JvmStatic
    val users by lazy {
        ktorfit.createUserRoutes()
    }

    /**
     * Initialize a unique instance of [DeezerApiClient].
     * Is safe to call multiple times.
     *
     * @param builder Builder to create [io.ktor.client.HttpClient].
     */
    @JvmStatic
    @JvmOverloads
    @OptIn(ExperimentalDeezerSdk::class)
    fun initialize(builder: HttpClientBuilder = HttpClientBuilder()): DeezerApiClient {
        if (::builder.isInitialized && builder == this.builder) return this
        if (::httpClient.isInitialized) httpClient.close()

        this.httpClient = builder.copy().addCustomConfig {
            HttpResponseValidator(customValidators())
        }.build()
        this.builder = builder

        return this
    }

    override fun isInitialized() = ::httpClient.isInitialized

    /**
     * Executes an HTTP request asynchronously and deserializes the response body into the specified type [T].
     *
     * @param request A lambda that configures the HTTP request. This is passed to the [HttpRequestBuilder].
     * @return The deserialized response body of type [T].
     * @throws DeezerApiException If the request fails
     */
    @JvmStatic
    @PublishedApi
    @Throws(DeezerApiException::class, CancellationException::class)
    internal suspend inline fun <reified T : @Serializable Any> rawExecuteAsync(
        crossinline request: HttpRequestBuilder.() -> Unit,
    ) = exceptionHandler { httpClient.request(request).body<T>() }

    /**
     * Handler an exception to Ktor client, wrap to custom [io.github.kingg22.deezerSdk.exceptions]
     *
     * @param block request of ktor client that can exception
     * @return T the expected type of the request
     * @throws DeezerApiException If the request fails
     */
    @JvmStatic
    @PublishedApi
    @Throws(DeezerApiException::class, CancellationException::class)
    internal suspend inline fun <T> exceptionHandler(crossinline block: suspend () -> T): T = try {
        block()
    } catch (e: ClientRequestException) {
        // try to obtain the error code of deezer if the http status is 4xx
        val errorBody = runCatching {
            e.response.body<io.github.kingg22.deezerSdk.api.objects.Error>().error
        }.getOrNull()
        throw DeezerApiException(errorCode = errorBody?.code, cause = e)
    } catch (e: HttpRequestTimeoutException) {
        throw DeezerApiException(
            errorMessage = "Deezer API took too long to respond. Try again later.",
            cause = e,
        )
    } catch (e: ServerResponseException) {
        throw DeezerApiException(errorMessage = "Deezer API unavailable", cause = e)
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        throw DeezerApiException(cause = e)
    }

    @JvmStatic
    private inline fun <reified T : @Serializable Any> decodeOrNull(json: String): T? =
        runCatching { Json.decodeFromString<T>(json) }.getOrNull()

    @JvmStatic
    private fun customValidators(): HttpCallValidatorConfig.() -> Unit = {
        handleResponseException { exception, _ ->
            if (exception is DeezerApiException || exception is CancellationException) {
                throw exception
            }
            throw DeezerApiException(cause = exception)
        }
        validateResponse {
            if (it.status.isSuccess()) {
                val saved = it.call
                // Validate errors as they are received with http code 2xx
                // This can cause [DoubleReceiveException], keep an eye on
                val content = decodeOrNull<io.github.kingg22.deezerSdk.api.objects.Error>(saved.body())?.error
                val booleano = content?.let { decodeOrNull<Boolean>(saved.body()) }
                when {
                    booleano != null -> throw DeezerApiException(
                        errorMessage = "API response a boolean: $booleano",
                    )

                    content != null -> throw DeezerApiException(
                        errorCode = content.code,
                        errorMessage = content.message,
                    )
                }
            }
        }
    }
}
