@file:OptIn(InternalDeezerClient::class, ExperimentalDeezerClient::class)

package io.github.kingg22.deezer.client.api

import io.github.kingg22.deezer.client.api.objects.ErrorContainer
import io.github.kingg22.deezer.client.api.routes.AlbumRoutes
import io.github.kingg22.deezer.client.api.routes.ArtistRoutes
import io.github.kingg22.deezer.client.api.routes.ChartRoutes
import io.github.kingg22.deezer.client.api.routes.EditorialRoutes
import io.github.kingg22.deezer.client.api.routes.EpisodeRoutes
import io.github.kingg22.deezer.client.api.routes.GenreRoutes
import io.github.kingg22.deezer.client.api.routes.InfosRoute
import io.github.kingg22.deezer.client.api.routes.OptionsRoute
import io.github.kingg22.deezer.client.api.routes.PlaylistRoutes
import io.github.kingg22.deezer.client.api.routes.PodcastRoutes
import io.github.kingg22.deezer.client.api.routes.RadioRoutes
import io.github.kingg22.deezer.client.api.routes.SearchRoutes
import io.github.kingg22.deezer.client.api.routes.TrackRoutes
import io.github.kingg22.deezer.client.api.routes.UserRoutes
import io.github.kingg22.deezer.client.api.routes.createAlbumRoutes
import io.github.kingg22.deezer.client.api.routes.createArtistRoutes
import io.github.kingg22.deezer.client.api.routes.createChartRoutes
import io.github.kingg22.deezer.client.api.routes.createEditorialRoutes
import io.github.kingg22.deezer.client.api.routes.createEpisodeRoutes
import io.github.kingg22.deezer.client.api.routes.createGenreRoutes
import io.github.kingg22.deezer.client.api.routes.createInfosRoute
import io.github.kingg22.deezer.client.api.routes.createOptionsRoute
import io.github.kingg22.deezer.client.api.routes.createPlaylistRoutes
import io.github.kingg22.deezer.client.api.routes.createPodcastRoutes
import io.github.kingg22.deezer.client.api.routes.createRadioRoutes
import io.github.kingg22.deezer.client.api.routes.createSearchRoutes
import io.github.kingg22.deezer.client.api.routes.createTrackRoutes
import io.github.kingg22.deezer.client.api.routes.createUserRoutes
import io.github.kingg22.deezer.client.exceptions.DeezerApiException
import io.github.kingg22.deezer.client.utils.ExperimentalDeezerClient
import io.github.kingg22.deezer.client.utils.HttpClientBuilder
import io.github.kingg22.deezer.client.utils.HttpClientProvider
import io.github.kingg22.deezer.client.utils.InternalDeezerClient
import io.github.kingg22.deezer.client.utils.createKtorfit
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
import kotlin.jvm.JvmField
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
    @InternalDeezerClient
    val httpClient = builder.copy().addCustomConfig {
        HttpResponseValidator(customValidators())
    }.build()

    private val ktorfit = createKtorfit(HttpClientProvider.DeezerApiSupported.API_DEEZER.baseUrl, httpClient)

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Album] */
    @JvmField
    val albums: AlbumRoutes = ktorfit.createAlbumRoutes()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Artist] */
    @JvmField
    val artists: ArtistRoutes = ktorfit.createArtistRoutes()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Chart] */
    @JvmField
    val charts: ChartRoutes = ktorfit.createChartRoutes()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Editorial] */
    @JvmField
    val editorials: EditorialRoutes = ktorfit.createEditorialRoutes()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Episode] */
    @JvmField
    val episodes: EpisodeRoutes = ktorfit.createEpisodeRoutes()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Genre] */
    @JvmField
    val genres: GenreRoutes = ktorfit.createGenreRoutes()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Infos] */
    @JvmField
    val infos: InfosRoute = ktorfit.createInfosRoute()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Options] */
    @JvmField
    val options: OptionsRoute = ktorfit.createOptionsRoute()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Playlist] */
    @JvmField
    val playlists: PlaylistRoutes = ktorfit.createPlaylistRoutes()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Podcast] */
    @JvmField
    val podcasts: PodcastRoutes = ktorfit.createPodcastRoutes()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Radio] */
    @JvmField
    val radios: RadioRoutes = ktorfit.createRadioRoutes()

    /** All endpoints related to search */
    @JvmField
    val searches: SearchRoutes = ktorfit.createSearchRoutes()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Track] */
    @JvmField
    val tracks: TrackRoutes = ktorfit.createTrackRoutes()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.User] */
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
