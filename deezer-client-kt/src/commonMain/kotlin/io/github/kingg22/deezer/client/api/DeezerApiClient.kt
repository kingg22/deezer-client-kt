@file:OptIn(InternalDeezerClient::class, ExperimentalDeezerClient::class)

package io.github.kingg22.deezer.client.api

import io.github.kingg22.deezer.client.api.objects.ErrorContainer
import io.github.kingg22.deezer.client.api.routes.*
import io.github.kingg22.deezer.client.exceptions.DeezerApiException
import io.github.kingg22.deezer.client.utils.API_DEEZER
import io.github.kingg22.deezer.client.utils.ExperimentalDeezerClient
import io.github.kingg22.deezer.client.utils.HttpClientBuilder
import io.github.kingg22.deezer.client.utils.InternalDeezerClient
import io.github.kingg22.deezer.client.utils.decodeOrNull
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.header
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
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
        defaultRequest {
            url(API_DEEZER)
            header("Accept", "application/json")
        }
        HttpResponseValidator(customValidators())
    }.build()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Album] */
    @get:JvmSynthetic
    val albums = AlbumRoutes(httpClient)

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Artist] */
    @get:JvmSynthetic
    val artists = ArtistRoutes(httpClient)

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Chart] */
    @get:JvmSynthetic
    val charts = ChartRoutes(httpClient)

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Editorial] */
    @get:JvmSynthetic
    val editorials = EditorialRoutes(httpClient)

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Episode] */
    @get:JvmSynthetic
    val episodes = EpisodeRoutes(httpClient)

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Genre] */
    @get:JvmSynthetic
    val genres = GenreRoutes(httpClient)

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Infos] */
    @get:JvmSynthetic
    val infos = InfosRoute(httpClient)

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Options] */
    @get:JvmSynthetic
    val options = OptionsRoute(httpClient)

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Playlist] */
    @get:JvmSynthetic
    val playlists = PlaylistRoutes(httpClient)

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Podcast] */
    @get:JvmSynthetic
    val podcasts = PodcastRoutes(httpClient)

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Radio] */
    @get:JvmSynthetic
    val radios = RadioRoutes(httpClient)

    /** All endpoints related to search */
    @get:JvmSynthetic
    val searches = SearchRoutes(httpClient)

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Track] */
    @get:JvmSynthetic
    val tracks = TrackRoutes(httpClient)

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.User] */
    @get:JvmSynthetic
    val users = UserRoutes(httpClient)

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
                        }.onFailure { currentCoroutineContext().ensureActive() }.getOrNull()

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
                        currentCoroutineContext().ensureActive()
                        throw DeezerApiException(cause = exception)
                    }
                }
            }

            validateResponse { response ->
                if (!response.status.isSuccess()) return@validateResponse

                val contentType = response.contentType()
                if (contentType == ContentType.Application.Json) {
                    val body = runCatching { response.bodyAsText() }
                        .onFailure { currentCoroutineContext().ensureActive() }
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
