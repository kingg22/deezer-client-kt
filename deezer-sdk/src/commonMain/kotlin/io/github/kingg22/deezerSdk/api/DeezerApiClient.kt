package io.github.kingg22.deezerSdk.api

import io.github.kingg22.deezerSdk.api.routes.AlbumRoutes
import io.github.kingg22.deezerSdk.api.routes.ArtistRoutes
import io.github.kingg22.deezerSdk.api.routes.ChartRoutes
import io.github.kingg22.deezerSdk.api.routes.EditorialRoutes
import io.github.kingg22.deezerSdk.api.routes.EpisodeRoutes
import io.github.kingg22.deezerSdk.api.routes.GenreRoutes
import io.github.kingg22.deezerSdk.api.routes.InfosRoute
import io.github.kingg22.deezerSdk.api.routes.OptionsRoute
import io.github.kingg22.deezerSdk.api.routes.PlaylistRoutes
import io.github.kingg22.deezerSdk.api.routes.PodcastRoutes
import io.github.kingg22.deezerSdk.api.routes.RadioRoutes
import io.github.kingg22.deezerSdk.api.routes.SearchRoutes
import io.github.kingg22.deezerSdk.api.routes.TrackRoutes
import io.github.kingg22.deezerSdk.api.routes.UserRoutes
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
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.coroutineContext
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * Client for the official [Deezer API](https://developers.deezer.com/api/).
 *
 * You can start with [DeezerApiClient.initialize] or constructor.
 *
 * @author Kingg22
 */
data class DeezerApiClient @JvmOverloads constructor(
    /** Builder to create [HttpClient].*/
    private val builder: HttpClientBuilder = HttpClientBuilder(),
) {
    /** The current [HttpClient] using */
    @PublishedApi
    @OptIn(ExperimentalDeezerSdk::class)
    internal val httpClient = builder.copy().addCustomConfig {
        HttpResponseValidator(customValidators())
    }.build()

    private val ktorfit = createKtorfit(HttpClientProvider.DeezerApiSupported.API_DEEZER.baseUrl, httpClient)

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Album] */
    val albums: AlbumRoutes = ktorfit.createAlbumRoutes()

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Artist] */
    val artists: ArtistRoutes = ktorfit.createArtistRoutes()

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Chart] */
    val charts: ChartRoutes = ktorfit.createChartRoutes()

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Editorial] */
    val editorials: EditorialRoutes = ktorfit.createEditorialRoutes()

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Episode] */
    val episodes: EpisodeRoutes = ktorfit.createEpisodeRoutes()

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Genre] */
    val genres: GenreRoutes = ktorfit.createGenreRoutes()

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Infos] */
    val infos: InfosRoute = ktorfit.createInfosRoute()

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Options] */
    val options: OptionsRoute = ktorfit.createOptionsRoute()

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Playlist] */
    val playlists: PlaylistRoutes = ktorfit.createPlaylistRoutes()

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Podcast] */
    val podcasts: PodcastRoutes = ktorfit.createPodcastRoutes()

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Radio] */
    val radios: RadioRoutes = ktorfit.createRadioRoutes()

    /** All endpoints related to search */
    val searches: SearchRoutes = ktorfit.createSearchRoutes()

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Track] */
    val tracks: TrackRoutes = ktorfit.createTrackRoutes()

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.User] */
    val users: UserRoutes = ktorfit.createUserRoutes()

    init {
        require(httpClient.isActive) { "HttpClient is not active" }
        GlobalDeezerApiClient.initIfNeeded(this)
    }

    /**
     * Executes an HTTP request asynchronously and deserializes the response body into the specified type [T].
     *
     * @param request A lambda that configures the HTTP request. This is passed to the [HttpRequestBuilder].
     * @return The deserialized response body of type [T].
     * @throws DeezerApiException If the request fails
     */
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
    @PublishedApi
    @Throws(DeezerApiException::class, CancellationException::class)
    internal suspend inline fun <T> exceptionHandler(crossinline block: suspend () -> T): T = try {
        block()
    } catch (e: ClientRequestException) {
        // try to obtain the error code of deezer if the http status is 4xx
        val errorBody = runCatching {
            e.response.body<io.github.kingg22.deezerSdk.api.objects.Error>().error
        }.onFailure { coroutineContext.ensureActive() }.getOrNull()
        throw DeezerApiException(errorCode = errorBody?.code, cause = e)
    } catch (e: HttpRequestTimeoutException) {
        throw DeezerApiException(
            errorMessage = "Deezer API took too long to respond. Try again later.",
            cause = e,
        )
    } catch (e: ServerResponseException) {
        throw DeezerApiException(errorMessage = "Deezer API unavailable", cause = e)
    } catch (e: Throwable) {
        coroutineContext.ensureActive()
        throw DeezerApiException(cause = e)
    }

    private inline fun <reified T : @Serializable Any> decodeOrNull(json: String): T? =
        runCatching { Json.decodeFromString<T>(json) }.getOrNull()

    private fun customValidators(): HttpCallValidatorConfig.() -> Unit = {
        handleResponseException { exception, _ ->
            if (exception is DeezerApiException || exception is CancellationException) {
                throw exception
            }
            throw DeezerApiException(errorMessage = "Ktor", cause = exception)
        }
        validateResponse {
            val contentType = it.contentType()
            if (it.status.isSuccess() && contentType != null && contentType == ContentType.Application.Json) {
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

    companion object {
        /**
         * Initialize an instance of [DeezerApiClient].
         *
         * @param builder Builder to create [HttpClient].
         */
        @JvmStatic
        @JvmOverloads
        fun initialize(builder: HttpClientBuilder = HttpClientBuilder()) = DeezerApiClient(builder)
    }
}
