// TODO add Poko to this class
@file:Suppress("DEPRECATION", "DEPRECATION_ERROR")

package io.github.kingg22.deezer.client.api

import io.github.kingg22.deezer.client.api.routes.*
import io.github.kingg22.deezer.client.utils.HttpClientBuilder
import io.github.kingg22.deezer.client.utils.InternalDeezerClient
import io.github.kingg22.deezer.client.utils.getDefaultDeezerHeaders
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.isActive

/**
 * Client for the official [Deezer API](https://developers.deezer.com/api/).
 *
 * For Java: **This is unusable, use the _Java Client_ instead, this is only for kotlin.**
 * @author Kingg22
 * @constructor Initialize the client with an active [HttpClient] and [DeezerClientPlugin] installed
 */
class DeezerApiClient(
    /** The current underlying [HttpClient] using */
    @property:InternalDeezerClient val httpClient: HttpClient,
) {
    /**
     * Initialize with builder to create an [HttpClient].
     *
     * Be careful if you expect the request to happen; look at all the [DeezerApiClient.initialize] options.
     *
     * @param builder Builder to create an [HttpClient]
     */
    @Deprecated("Build a HttpClient instead, see HttpClientBuilder deprecation.", level = DeprecationLevel.ERROR)
    @JvmOverloads
    constructor(
        builder: HttpClientBuilder = HttpClientBuilder(),
    ) : this(
        builder.copy().addCustomConfig {
            install(DeezerClientPlugin)
            defaultRequest {
                url(API_DEEZER_URL)
                accept(ContentType.Application.Json)
            }
        }.build(),
    )

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
    val users: UserRoutes = UserRoutes(httpClient)

    init {
        require(httpClient.isActive) { "HttpClient is not active" }
        httpClient.plugin(DeezerClientPlugin) // Invoke this function to ensure the plugin is installed
        GlobalDeezerApiClient.initIfNeeded(this)
    }

    /** Client for the Deezer API. */
    companion object {
        /** URL Host for Deezer API: `https://api.deezer.com` */
        const val API_DEEZER_URL = "https://api.deezer.com"

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
        @Deprecated("Build a HttpClient instead, see HttpClientBuilder deprecation.", level = DeprecationLevel.ERROR)
        @JvmStatic
        @JvmOverloads
        fun initialize(
            builder: HttpClientBuilder = HttpClientBuilder(),
            includeDefaultHeaders: Boolean = true,
            expectSuccess: Boolean = true,
        ) = DeezerApiClient(
            builder.apply {
                addCustomConfig {
                    if (includeDefaultHeaders) defaultRequest { this.headers.appendAll(getDefaultDeezerHeaders()) }
                    if (expectSuccess) this.expectSuccess = true
                }
            },
        )
    }
}
