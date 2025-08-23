@file:OptIn(InternalDeezerClient::class, ExperimentalDeezerClient::class)

package io.github.kingg22.deezer.client.api

import io.github.kingg22.deezer.client.api.routes.AlbumJavaRoutes.Companion.asJava
import io.github.kingg22.deezer.client.api.routes.ArtistJavaRoutes.Companion.asJava
import io.github.kingg22.deezer.client.api.routes.ChartJavaRoutes.Companion.asJava
import io.github.kingg22.deezer.client.api.routes.EditorialJavaRoutes.Companion.asJava
import io.github.kingg22.deezer.client.api.routes.EpisodeJavaRoutes.Companion.asJava
import io.github.kingg22.deezer.client.api.routes.GenreJavaRoutes.Companion.asJava
import io.github.kingg22.deezer.client.api.routes.InfosJavaRoute.Companion.asJava
import io.github.kingg22.deezer.client.api.routes.OptionsJavaRoute.Companion.asJava
import io.github.kingg22.deezer.client.api.routes.PlaylistJavaRoutes.Companion.asJava
import io.github.kingg22.deezer.client.api.routes.PodcastJavaRoutes.Companion.asJava
import io.github.kingg22.deezer.client.api.routes.RadioJavaRoutes.Companion.asJava
import io.github.kingg22.deezer.client.api.routes.SearchJavaRoutes.Companion.asJava
import io.github.kingg22.deezer.client.api.routes.TrackJavaRoutes.Companion.asJava
import io.github.kingg22.deezer.client.api.routes.UserJavaRoutes.Companion.asJava
import io.github.kingg22.deezer.client.utils.ExperimentalDeezerClient
import io.github.kingg22.deezer.client.utils.HttpClientBuilder
import io.github.kingg22.deezer.client.utils.InternalDeezerClient
import io.ktor.client.*
import kotlinx.coroutines.isActive

/**
 * Java Client for the official [Deezer API](https://developers.deezer.com/api/).
 *
 * You can start with [DeezerApiJavaClient.initialize] or constructors.
 *
 * For Kotlin: **You don't need this, use [DeezerApiClient] instead, this is only for Java**
 * @author Kingg22
 */
@PublishedApi
/*
This is internal, for kotlin consumers can't access this, but Java consumers can access avoid internal stuff.
Is PublishedApi to maintain binary compatibility.
 */
internal class DeezerApiJavaClient
/**
 * Initialize with a [Deezer API Kotlin Client][DeezerApiClient]
 *
 * @param delegate the [DeezerApiClient] to wrap all operations with it
 * @throws IllegalArgumentException If the [HttpClient] of the delegate is not active
 */
@ExperimentalDeezerClient
internal constructor(
    /** The current kotlin client is used to delegate all operations of this java client. */
    @property:InternalDeezerClient
    val delegate: DeezerApiClient,
) {
    /**
     * Initialize with builder to create an [HttpClient].
     *
     * @param builder Builder to create an [HttpClient]
     * @see DeezerApiClient
     */
    @JvmOverloads
    internal constructor(builder: HttpClientBuilder = HttpClientBuilder()) : this(DeezerApiClient(builder))

    init {
        require(delegate.httpClient.isActive) { "The http client of delegate isn't active" }
    }

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Album] */
    @JvmField
    val albums = delegate.albums.asJava()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Artist] */
    @JvmField
    val artists = delegate.artists.asJava()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Chart] */
    @JvmField
    val charts = delegate.charts.asJava()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Editorial] */
    @JvmField
    val editorials = delegate.editorials.asJava()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Episode] */
    @JvmField
    val episodes = delegate.episodes.asJava()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Genre] */
    @JvmField
    val genres = delegate.genres.asJava()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Infos] */
    @JvmField
    val infos = delegate.infos.asJava()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Options] */
    @JvmField
    val options = delegate.options.asJava()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Playlist] */
    @JvmField
    val playlists = delegate.playlists.asJava()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Podcast] */
    @JvmField
    val podcasts = delegate.podcasts.asJava()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Radio] */
    @JvmField
    val radios = delegate.radios.asJava()

    /** All endpoints related to search */
    @JvmField
    val searches = delegate.searches.asJava()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.Track] */
    @JvmField
    val tracks = delegate.tracks.asJava()

    /** All endpoints related to [io.github.kingg22.deezer.client.api.objects.User] */
    @JvmField
    val users = delegate.users.asJava()

    /**
     * Java Client for the Deezer API.
     * @see io.github.kingg22.deezer.client.api.DeezerApiJavaClient.Companion.initialize
     */
    companion object {
        /**
         * Initialize an instance of [DeezerApiClient].
         *
         * @param builder Builder to create [HttpClient].
         * @see DeezerApiClient.initialize
         */
        @PublishedApi
        @JvmStatic
        internal fun initialize(builder: HttpClientBuilder = HttpClientBuilder()) = DeezerApiJavaClient(builder)
    }
}
