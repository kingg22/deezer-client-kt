@file:OptIn(InternalDeezerClient::class, ExperimentalDeezerClient::class)

package io.github.kingg22.deezer.client.api

import io.github.kingg22.deezer.client.api.objects.ErrorContainer
import io.github.kingg22.deezer.client.api.routes.*
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
import io.github.kingg22.deezer.client.utils.API_DEEZER
import io.github.kingg22.deezer.client.utils.ExperimentalDeezerClient
import io.github.kingg22.deezer.client.utils.HttpClientBuilder
import io.github.kingg22.deezer.client.utils.InternalDeezerClient
import io.github.kingg22.deezer.client.utils.createKtorfit
import io.github.kingg22.deezer.client.utils.decodeOrNull
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlin.coroutines.coroutineContext
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic

/**
 * Client for the official [Deezer API](https://developers.deezer.com/api/).
 *
 * You can start with [DeezerApiClient.initialize] or constructor.
 *
 * For Java: **This is unusable, use the _Java Client_ instead, this is only for kotlin.**
 * @author Kingg22
 */
class DeezerApiClient
/**
 * Initialize with builder to create an [HttpClient].
 *
 * If needed, after init is set on [GlobalDeezerApiClient]
 *
 * @param builder Builder to create an [HttpClient]
 * @see GlobalDeezerApiClient.initIfNeeded
 */
@JvmOverloads
constructor(
    builder: HttpClientBuilder = HttpClientBuilder(),
) {
    /** The current [HttpClient] using */
    @InternalDeezerClient
    val httpClient = builder.copy().addCustomConfig {
        HttpResponseValidator(customValidators())
    }.build()

    @get:JvmSynthetic
    internal val ktorfit = createKtorfit(API_DEEZER, httpClient)

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Album] */
    @get:JvmSynthetic
    val albums: AlbumRoutes = ktorfit.createAlbumRoutes()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Artist] */
    @get:JvmSynthetic
    val artists: ArtistRoutes = ktorfit.createArtistRoutes()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Chart] */
    @get:JvmSynthetic
    val charts: ChartRoutes = ktorfit.createChartRoutes()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Editorial] */
    @get:JvmSynthetic
    val editorials: EditorialRoutes = ktorfit.createEditorialRoutes()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Episode] */
    @get:JvmSynthetic
    val episodes: EpisodeRoutes = ktorfit.createEpisodeRoutes()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Genre] */
    @get:JvmSynthetic
    val genres: GenreRoutes = ktorfit.createGenreRoutes()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Infos] */
    @get:JvmSynthetic
    val infos: InfosRoute = ktorfit.createInfosRoute()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Options] */
    @get:JvmSynthetic
    val options: OptionsRoute = ktorfit.createOptionsRoute()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Playlist] */
    @get:JvmSynthetic
    val playlists: PlaylistRoutes = ktorfit.createPlaylistRoutes()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Podcast] */
    @get:JvmSynthetic
    val podcasts: PodcastRoutes = ktorfit.createPodcastRoutes()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Radio] */
    @get:JvmSynthetic
    val radios: RadioRoutes = ktorfit.createRadioRoutes()

    /** All endpoints related to search */
    @get:JvmSynthetic
    val searches: SearchRoutes = ktorfit.createSearchRoutes()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Track] */
    @get:JvmSynthetic
    val tracks: TrackRoutes = ktorfit.createTrackRoutes()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.User] */
    @get:JvmSynthetic
    val users: UserRoutes = ktorfit.createUserRoutes()

    init {
        require(httpClient.isActive) { "HttpClient is not active" }
        GlobalDeezerApiClient.initIfNeeded(this)
    }

    /**
     * Client for the Deezer API.
     * @see io.github.kingg22.deezer.client.api.DeezerApiClient.Companion.initialize
     */
    companion object {
        /**
         * Initialize an instance of [DeezerApiClient].
         *
         * @param builder Builder to create [HttpClient].
         */
        @JvmStatic
        @JvmOverloads
        fun initialize(builder: HttpClientBuilder = HttpClientBuilder()) = DeezerApiClient(builder)

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
    }
}
