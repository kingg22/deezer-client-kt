package io.github.kingg22.deezerSdk.api

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.FlowConverterFactory
import de.jensklingenberg.ktorfit.ktorfit
import io.github.kingg22.deezerSdk.api.objects.Error
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
import io.github.kingg22.deezerSdk.utils.HttpClientBuilder
import io.github.kingg22.deezerSdk.utils.HttpClientProvider
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.bodyAsText
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
object DeezerApiClient {
    /** The current [HttpClient] using */
    lateinit var httpClient: HttpClient
    private val ktorfit: Ktorfit by lazy {
        ktorfit {
            baseUrl(HttpClientProvider.DeezerApiSupported.API_DEEZER.baseUrl)
            httpClient(httpClient)
            converterFactories(FlowConverterFactory())
        }
    }

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Album] */
    val albums: AlbumRoutes by lazy {
        ktorfit.createAlbumRoutes()
    }

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Artist] */
    val artists: ArtistRoutes by lazy {
        ktorfit.createArtistRoutes()
    }

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Chart] */
    val charts: ChartRoutes by lazy {
        ktorfit.createChartRoutes()
    }

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Editorial] */
    val editorials: EditorialRoutes by lazy {
        ktorfit.createEditorialRoutes()
    }

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Episode] */
    val episodes: EpisodeRoutes by lazy {
        ktorfit.createEpisodeRoutes()
    }

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Genre] */
    val genres: GenreRoutes by lazy {
        ktorfit.createGenreRoutes()
    }

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Infos] */
    val infos: InfosRoute by lazy {
        ktorfit.createInfosRoute()
    }

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Options] */
    val options: OptionsRoute by lazy {
        ktorfit.createOptionsRoute()
    }

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Playlist] */
    val playlists: PlaylistRoutes by lazy {
        ktorfit.createPlaylistRoutes()
    }

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Podcast] */
    val podcasts: PodcastRoutes by lazy {
        ktorfit.createPodcastRoutes()
    }

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Radio] */
    val radios: RadioRoutes by lazy {
        ktorfit.createRadioRoutes()
    }

    /** All endpoints related to search */
    val searches: SearchRoutes by lazy {
        ktorfit.createSearchRoutes()
    }

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Track] */
    val tracks: TrackRoutes by lazy {
        ktorfit.createTrackRoutes()
    }

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.User] */
    val users: UserRoutes by lazy {
        ktorfit.createUserRoutes()
    }

    @Throws(IllegalStateException::class)
    fun initialize(block: HttpClientBuilder): DeezerApiClient {
        check(!::httpClient.isInitialized) { "Deezer Api Client is already initialized" }
        block.addCustomConfig {
            HttpResponseValidator {
                validateResponse {
                    if (it.status.isSuccess()) {
                        // Validate errors as they are received with http code 2xx
                        // This can cause [DoubleReceiveException], keep an eye on
                        val content = decodeOrNull<Error>(it.bodyAsText())?.error
                        val booleano = content ?: decodeOrNull<Boolean>(it.bodyAsText())
                        when {
                            content != null -> throw DeezerApiException(
                                errorCode = content.code,
                                errorMessage = content.message,
                            )

                            booleano != null -> throw DeezerApiException(
                                errorMessage = "API response a boolean: $booleano",
                            )
                        }
                    }
                }
            }
        }
        httpClient = block.build()
        return this
    }

    fun isInitialized(): Boolean = ::httpClient.isInitialized

    /**
     * Executes an HTTP request asynchronously and deserializes the response body into the specified type [T].
     *
     * @param request A lambda that configures the HTTP request. This is passed to the [HttpRequestBuilder].
     * @return The deserialized response body of type [T].
     * @throws DeezerApiException If the request fails
     */
    @Throws(DeezerApiException::class, CancellationException::class)
    suspend inline fun <reified T : @Serializable Any> rawExecuteAsync(request: HttpRequestBuilder.() -> Unit) =
        exceptionHandler {
            httpClient.request(request).body<T>()
        }

    /**
     * Handler an exception to Ktor client, wrap to custom [io.github.kingg22.deezerSdk.exceptions]
     *
     * @param block request of ktor client that can exception
     * @return T the expected type of the request
     * @throws DeezerApiException If the request fails
     */
    @Throws(DeezerApiException::class, CancellationException::class)
    suspend inline fun <T> exceptionHandler(block: suspend () -> T): T = try {
        block()
    } catch (e: ClientRequestException) {
        // try to obtain the error code of deezer if the http status is 4xx
        val errorBody = try {
            e.response.body<Error>().error
        } catch (_: Exception) {
            null
        }
        throw DeezerApiException(errorCode = errorBody?.code, cause = e)
    } catch (e: HttpRequestTimeoutException) {
        throw DeezerApiException(
            errorMessage = "Deezer API took too long to respond. Try again later.",
            cause = e,
        )
    } catch (e: ServerResponseException) {
        throw DeezerApiException(errorMessage = "Deezer API unavailable", cause = e)
    }

    private inline fun <reified T> decodeOrNull(json: String): T? = try {
        Json.decodeFromString<T>(json)
    } catch (_: Exception) {
        null
    }
}
