package io.github.kingg22.deezer.client.api

import io.github.kingg22.deezer.client.api.objects.ErrorContainer
import io.github.kingg22.deezer.client.api.routes.*
import io.github.kingg22.deezer.client.exceptions.DeezerApiException
import io.github.kingg22.deezer.client.utils.HttpClientBuilder
import io.github.kingg22.deezer.client.utils.InternalDeezerClient
import io.github.kingg22.deezer.client.utils.decodeOrNull
import io.github.kingg22.deezer.client.utils.getDefaultDeezerHeaders
import io.github.kingg22.ktorgen.http.Header
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
 * Be careful if you expect the request to happen; look at all the [DeezerApiClient.initialize] options.
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
    /** The current underlying [HttpClient] using */
    @InternalDeezerClient
    val httpClient = builder.copy().addCustomConfig {
        defaultRequest {
            url("https://api.deezer.com/")
            header(HttpHeaders.Accept, Header.ContentTypes.Application.Json)
        }
        HttpResponseValidator(customValidators())
    }.build()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Album] */
    @get:JvmSynthetic
    val albums: AlbumRoutes = AlbumRoutes(httpClient)

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Artist] */
    @get:JvmSynthetic
    val artists: ArtistRoutes = ArtistRoutes(httpClient)

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Chart] */
    @get:JvmSynthetic
    val charts: ChartRoutes = ChartRoutes(httpClient)

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Editorial] */
    @get:JvmSynthetic
    val editorials: EditorialRoutes = EditorialRoutes(httpClient)

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Episode] */
    @get:JvmSynthetic
    val episodes: EpisodeRoutes = EpisodeRoutes(httpClient)

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Genre] */
    @get:JvmSynthetic
    val genres: GenreRoutes = GenreRoutes(httpClient)

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Infos] */
    @get:JvmSynthetic
    val infos: InfosRoute = InfosRoute(httpClient)

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Options] */
    @get:JvmSynthetic
    val options: OptionsRoute = OptionsRoute(httpClient)

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Playlist] */
    @get:JvmSynthetic
    val playlists: PlaylistRoutes = PlaylistRoutes(httpClient)

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Podcast] */
    @get:JvmSynthetic
    val podcasts: PodcastRoutes = PodcastRoutes(httpClient)

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Radio] */
    @get:JvmSynthetic
    val radios: RadioRoutes = RadioRoutes(httpClient)

    /** All endpoints related to search */
    @get:JvmSynthetic
    val searches: SearchRoutes = SearchRoutes(httpClient)

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Track] */
    @get:JvmSynthetic
    val tracks: TrackRoutes = TrackRoutes(httpClient)

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
         * Default [HttpClientBuilder]
         * @param includeDefaultHeaders Whether to include the default headers to be compatible with the official API.
         * See source code of [getDefaultDeezerHeaders].
         * Default true
         * @param expectSuccess Whether to expect a successful response.
         * See [addDefaultResponseValidation].
         * Default true.
         */
        @JvmStatic
        @JvmOverloads
        fun initialize(
            builder: HttpClientBuilder = HttpClientBuilder(),
            includeDefaultHeaders: Boolean = true,
            expectSuccess: Boolean = true,
        ) = DeezerApiClient(
            builder.copy().apply {
                addCustomConfig {
                    if (includeDefaultHeaders) headers { appendAll(getDefaultDeezerHeaders()) }
                    if (expectSuccess) this.expectSuccess = true
                }
            },
        )

        @Suppress("kotlin:S3776") // simple validation in one method
        private fun customValidators(): HttpCallValidatorConfig.() -> Unit = {
            handleResponseException { exception, _ ->
                when (exception) {
                    is DeezerApiException -> throw exception
                    is ClientRequestException -> {
                        val errorBody = try {
                            exception.response.body<ErrorContainer>().error
                        } catch (_: Exception) {
                            currentCoroutineContext().ensureActive()
                            null
                        }

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
                if (contentType?.match(ContentType.Application.Json) == true) {
                    val body = try {
                        response.bodyAsText()
                    } catch (_: Exception) {
                        currentCoroutineContext().ensureActive()
                        // Don't rethrow because is probable the error is caused by parsing, delegate to ktor
                        return@validateResponse
                    }

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
