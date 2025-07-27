package io.github.kingg22.deezerSdk.api

import io.github.kingg22.deezerSdk.api.objects.ErrorContainer
import io.github.kingg22.deezerSdk.api.routes.*
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
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.coroutines.coroutineContext

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
    @get:JvmName("-httpClient")
    @PublishedApi
    @OptIn(ExperimentalDeezerSdk::class)
    internal val httpClient = builder.copy().addCustomConfig {
        HttpResponseValidator(customValidators())
    }.build()

    private val ktorfit = createKtorfit(HttpClientProvider.DeezerApiSupported.API_DEEZER.baseUrl, httpClient)

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Album] */
    @JvmField
    val albums: AlbumRoutes = ktorfit.createAlbumRoutes()

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Artist] */
    @JvmField
    val artists: ArtistRoutes = ktorfit.createArtistRoutes()

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Chart] */
    @JvmField
    val charts: ChartRoutes = ktorfit.createChartRoutes()

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Editorial] */
    @JvmField
    val editorials: EditorialRoutes = ktorfit.createEditorialRoutes()

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Episode] */
    @JvmField
    val episodes: EpisodeRoutes = ktorfit.createEpisodeRoutes()

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Genre] */
    @JvmField
    val genres: GenreRoutes = ktorfit.createGenreRoutes()

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Infos] */
    @JvmField
    val infos: InfosRoute = ktorfit.createInfosRoute()

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Options] */
    @JvmField
    val options: OptionsRoute = ktorfit.createOptionsRoute()

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Playlist] */
    @JvmField
    val playlists: PlaylistRoutes = ktorfit.createPlaylistRoutes()

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Podcast] */
    @JvmField
    val podcasts: PodcastRoutes = ktorfit.createPodcastRoutes()

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Radio] */
    @JvmField
    val radios: RadioRoutes = ktorfit.createRadioRoutes()

    /** All endpoints related to search */
    @JvmField
    val searches: SearchRoutes = ktorfit.createSearchRoutes()

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.Track] */
    @JvmField
    val tracks: TrackRoutes = ktorfit.createTrackRoutes()

    /** All endpoints related to [io.github.kingg22.deezerSdk.api.objects.User] */
    @JvmField
    val users: UserRoutes = ktorfit.createUserRoutes()

    init {
        require(httpClient.isActive) { "HttpClient is not active" }
        GlobalDeezerApiClient.initIfNeeded(this)
    }

    private inline fun <reified T : @Serializable Any> decodeOrNull(json: String): T? =
        runCatching { Json.decodeFromString<T>(json) }.getOrNull()

    private fun customValidators(): HttpCallValidatorConfig.() -> Unit = {
        handleResponseException { exception, _ ->
            when (exception) {
                is DeezerApiException -> throw exception
                is ClientRequestException -> {
                    val errorBody = runCatching {
                        exception.response.body<ErrorContainer>().error
                    }.onFailure { coroutineContext.ensureActive() }.getOrNull()

                    throw DeezerApiException(
                        errorCode = errorBody?.code,
                        errorMessage = errorBody?.message,
                        cause = exception,
                    )
                }

                is HttpRequestTimeoutException -> {
                    throw DeezerApiException(
                        errorMessage = "Deezer API took too long to respond. Try again later.",
                        cause = exception,
                    )
                }

                is ServerResponseException -> {
                    throw DeezerApiException(
                        errorMessage = "Deezer API unavailable",
                        cause = exception,
                    )
                }

                else -> {
                    coroutineContext.ensureActive()
                    throw DeezerApiException(cause = exception)
                }
            }
        }

        validateResponse { response ->
            if (!response.status.isSuccess()) return@validateResponse

            val contentType = response.contentType()
            if (contentType == ContentType.Application.Json) {
                val body = runCatching { response.bodyAsText() }
                    .onFailure { coroutineContext.ensureActive() }
                    .getOrNull()
                    ?: return@validateResponse

                val error = decodeOrNull<ErrorContainer>(body)?.error
                val asBoolean = decodeOrNull<Boolean>(body)

                when {
                    asBoolean != null -> throw DeezerApiException(
                        errorMessage = "API responded with boolean: $asBoolean",
                    )

                    error != null -> throw DeezerApiException(
                        errorCode = error.code,
                        errorMessage = error.message,
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
