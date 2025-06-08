package io.github.kingg22.deezerSdk.api

import de.jensklingenberg.ktorfit.Ktorfit
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
import io.github.kingg22.deezerSdk.utils.AfterInitialize
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
    private lateinit var ktorfit: Ktorfit

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Album] */
    @AfterInitialize
    @JvmStatic
    lateinit var albums: AlbumRoutes
        private set

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Artist] */
    @AfterInitialize
    @JvmStatic
    lateinit var artists: ArtistRoutes
        private set

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Chart] */
    @AfterInitialize
    @JvmStatic
    lateinit var charts: ChartRoutes
        private set

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Editorial] */
    @AfterInitialize
    @JvmStatic
    lateinit var editorials: EditorialRoutes
        private set

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Episode] */
    @JvmStatic
    lateinit var episodes: EpisodeRoutes
        private set

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Genre] */
    @AfterInitialize
    @JvmStatic
    lateinit var genres: GenreRoutes
        private set

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Infos] */
    @AfterInitialize
    @JvmStatic
    lateinit var infos: InfosRoute
        private set

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Options] */
    @AfterInitialize
    @JvmStatic
    lateinit var options: OptionsRoute
        private set

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Playlist] */
    @AfterInitialize
    @JvmStatic
    lateinit var playlists: PlaylistRoutes
        private set

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Podcast] */
    @AfterInitialize
    @JvmStatic
    lateinit var podcasts: PodcastRoutes
        private set

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Radio] */
    @AfterInitialize
    @JvmStatic
    lateinit var radios: RadioRoutes
        private set

    /** All endpoints related to search */
    @AfterInitialize
    @JvmStatic
    lateinit var searches: SearchRoutes
        private set

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Track] */
    @AfterInitialize
    @JvmStatic
    lateinit var tracks: TrackRoutes
        private set

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.User] */
    @AfterInitialize
    @JvmStatic
    lateinit var users: UserRoutes
        private set

    /**
     * Initialize a unique instance of [DeezerApiClient].
     * Is safe to call multiple times.
     *
     * @param builder Builder to create [HttpClient].
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
        this.ktorfit = createKtorfit(HttpClientProvider.DeezerApiSupported.API_DEEZER.baseUrl, httpClient)
        this.initializeAll()

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
    @AfterInitialize
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
    } catch (e: Throwable) {
        if (e is CancellationException) throw e
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

    @JvmStatic
    private fun initializeAll() {
        albums = ktorfit.createAlbumRoutes()
        artists = ktorfit.createArtistRoutes()
        charts = ktorfit.createChartRoutes()
        editorials = ktorfit.createEditorialRoutes()
        episodes = ktorfit.createEpisodeRoutes()
        genres = ktorfit.createGenreRoutes()
        infos = ktorfit.createInfosRoute()
        options = ktorfit.createOptionsRoute()
        playlists = ktorfit.createPlaylistRoutes()
        podcasts = ktorfit.createPodcastRoutes()
        radios = ktorfit.createRadioRoutes()
        searches = ktorfit.createSearchRoutes()
        tracks = ktorfit.createTrackRoutes()
        users = ktorfit.createUserRoutes()
    }
}
